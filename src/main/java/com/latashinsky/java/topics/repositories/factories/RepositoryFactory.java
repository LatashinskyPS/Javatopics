package com.latashinsky.java.topics.repositories.factories;

import com.latashinsky.java.topics.repositories.MyRepository;

public interface RepositoryFactory {
    <T> MyRepository<T> getRepository(Class<T> clazz);
}
