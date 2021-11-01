package com.latashinsky.banksservice.repositories.factories;

import com.fasterxml.jackson.core.type.TypeReference;

public interface RepositoryFactory {
    <T> T getRepository(TypeReference<T> typeReference);
}
