package com.latashinsky.java.topics.repositories;

import java.util.Collection;
import java.util.UUID;

public interface MyRepository<T> {
    T findById(UUID id);

    Collection<T> findAll();

    void save(T t);

    void delete(T t);
}
