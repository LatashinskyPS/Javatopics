package com.latashinsky.java.topics.entities.factories;

import com.fasterxml.jackson.core.type.TypeReference;

public interface EntitiesFactory {
    <T> T getEntity(TypeReference<T> typeReference);
}
