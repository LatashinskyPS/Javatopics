package by.latashinsky.repositories;

import by.latashinsky.entities.Bank;

import java.util.List;

public interface MyRepository<T> {
    T findById(int id);

    List<T> findAll();

    void save(T t);

    void delete(T t);
}
