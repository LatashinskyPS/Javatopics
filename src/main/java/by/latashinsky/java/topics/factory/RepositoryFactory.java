package by.latashinsky.java.topics.factory;

import by.latashinsky.java.topics.repositories.MyRepository;

public interface RepositoryFactory {
    <T>MyRepository<?> getRepository(Class<T> clazz);
}
