package by.latashinsky.java.topics.entities.factories;

public interface EntitiesFactory {
    <T>T getEntity(Class<T> clazz);
}
