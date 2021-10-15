package by.latashinsky.java.topics.repositories.factories;

import by.latashinsky.java.topics.repositories.MyRepository;

public interface RepositoryFactory {
    <T>MyRepository<T> getRepository(Class<T> clazz);
}
