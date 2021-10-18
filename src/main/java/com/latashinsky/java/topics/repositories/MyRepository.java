package com.latashinsky.java.topics.repositories;

import java.util.Collection;

public interface MyRepository<T> {
    T findById(int id);

    Collection<T> findAll();

    void save(T t);

    void delete(T t);
}
