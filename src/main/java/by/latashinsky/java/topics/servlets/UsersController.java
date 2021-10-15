package by.latashinsky.java.topics.servlets;

import by.latashinsky.java.topics.entities.User;
import by.latashinsky.java.topics.entities.UserTypes;
import by.latashinsky.java.topics.exceptions.BadRequest;
import by.latashinsky.java.topics.exceptions.ResourceNotFound;
import by.latashinsky.java.topics.factory.Factory;
import by.latashinsky.java.topics.repositories.MyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UsersController {
    MyRepository<User> userMyRepository = Factory.getInstance().getRepository(User.class);

    @GetMapping("")
    public String getUsers(@RequestParam(value = "name", required = false) String name) throws JsonProcessingException {
        Collection<User> users = userMyRepository.findAll();
        if (name != null) {
            users.removeIf(r -> !name.equalsIgnoreCase(r.getName()));
        }
        return new ObjectMapper().writeValueAsString(users);
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id) throws JsonProcessingException {
        User user = userMyRepository.findById(id);
        if (user != null) {
            return new ObjectMapper().writeValueAsString(user);
        }
        throw new ResourceNotFound();
    }

    @PostMapping("")
    public String addNewUser(@RequestParam(value = "name") String name,
                             @RequestParam(value = "type") String type) {
        if (name == null || type == null) {
            throw new BadRequest();
        }
        User user = Factory.getInstance().getEntity(User.class);
        user.setName(name);
        UserTypes userType = UserTypes.getUserType(type);
        if (userType != null) {
            user.setUserType(userType);
            userMyRepository.save(user);
            return "success";
        }
        throw new BadRequest();
    }

    @PutMapping("/{id}")
    public String updateUser(@RequestParam(value = "name", required = false) String name,
                             @RequestParam(value = "type", required = false) String type,
                             @PathVariable("id") int id) {
        User user = userMyRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFound();
        }
        if (name != null) {
            user.setName(name);
        }
        if (type != null) {
            UserTypes userType = UserTypes.getUserType(type);
            if (userType == null) {
                throw new BadRequest();
            }
            user.setUserType(userType);
        }
        userMyRepository.save(user);
        return "success";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        User user = userMyRepository.findById(id);
        if (user == null) {
            throw new ResourceNotFound();
        }
        userMyRepository.delete(user);
        return "success";
    }
}
