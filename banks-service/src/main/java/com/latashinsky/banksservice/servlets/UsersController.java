package com.latashinsky.banksservice.servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.latashinsky.banksservice.factory.Factory;
import com.latashinsky.banksservice.services.MessageService;
import com.latashinsky.banksservice.entities.User;
import com.latashinsky.banksservice.entities.UserTypes;
import com.latashinsky.banksservice.exceptions.BadRequest;
import com.latashinsky.banksservice.exceptions.ResourceNotFound;
import com.latashinsky.banksservice.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.UUID;

@Tag(name = "Users controller", description = "Base CRUD operation on users")
@RestController
@RequestMapping("/users")
public class UsersController {
    private final MessageService messageService;
    UserRepository<User<?>> userMyRepository = Factory.getInstance().getRepository(new TypeReference<>() {
    });

    public UsersController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(
            summary = "Вывод списка пользователей",
            description = "Позволяет выводить список пользователей с фильтрами в параметрах"
    )
    @GetMapping("")
    public Collection<User<?>> getUsers(@RequestParam(value = "name", required = false)
                                        @Parameter(name = "name", description = "Фильтр на совпадение имени вне зависимости от регистра")
                                                String name) {
        Collection<User<?>> users = userMyRepository.findAll();
        if (name != null) {
            users.removeIf(r -> !name.equalsIgnoreCase(r.getName()));
        }
        return users;
    }

    @Operation(
            summary = "Получение пользователя",
            description = "Вывод пользователя по id"
    )
    @GetMapping("/{id}")
    public User<?> getUser(@PathVariable("id")
                           @Parameter(name = "id", description = "id пользователя") UUID id) {
        User<?> user = userMyRepository.findById(id);
        if (user != null) {
            return user;
        }
        throw new ResourceNotFound();
    }

    @Operation(
            summary = "Создание нового пользователя",
            description = "Создание пользователя по параметрам"
    )
    @PostMapping("")
    public boolean addNewUser(@RequestParam(value = "name") String name,
                              @RequestParam(value = "type") UserTypes type,
                              @RequestParam(value = "email") @Email String email,
                              @RequestParam(value = "chatId", required = false) String chatId,
                              @RequestParam(value = "isEnabledNotifications", defaultValue = "false") boolean isEnabledNotifications) {
        if (name == null || type == null) {
            throw new BadRequest();
        }
        User<?> user = Factory.getInstance().getEntity(new TypeReference<>() {
        });
        user.setName(name);
        user.setUserType(type);
        user.setEmail(email);
        user.setChatId(chatId);
        user.setEnabledNotifications(isEnabledNotifications);
        userMyRepository.save(user);
        messageService.sendMessage(user, user.toString(), "users.create");
        return true;
    }

    @Operation(
            summary = "Измененение информации о пользователе",
            description = "Изменение информации о пользоаваеле по входным параметрам"
    )
    @PutMapping("/{id}")
    public boolean updateUser(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "type", required = false) UserTypes type,
                              @RequestParam(value = "email", required = false) @Email String email,
                              @RequestParam(value = "chatId", required = false) String chatId,
                              @PathVariable("id") UUID id) {
        User<?> user = userMyRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFound();
        }
        if (name != null) {
            user.setName(name);
        }
        if (type != null) {
            user.setUserType(type);
        }
        if (email != null) {
            user.setEmail(email);
        }
        if (chatId != null) {
            user.setChatId(chatId);
        }
        String message = user.toString();
        userMyRepository.save(user);
        message += "\nto\n" + user;
        messageService.sendMessage(user, message, "users.put");
        return true;
    }

    @Operation(
            summary = "Удаление пользователя",
            description = "Удаление пользователя по заданному id"
    )
    @DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable("id")
                              @Parameter(name = "id", description = "Id пользователя") UUID id) {
        User<?> user = userMyRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFound();
        }
        userMyRepository.delete(user);
        messageService.sendMessage(user, user.toString(), "users.delete");
        return true;
    }
}
