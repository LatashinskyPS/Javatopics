package by.latashinsky.factory;

import by.latashinsky.repositories.MyRepository;

public interface RepositoryFactory {
    <T>MyRepository<?> getRepository(Class<T> clazz);
}
