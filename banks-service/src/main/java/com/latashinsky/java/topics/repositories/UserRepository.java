package com.latashinsky.java.topics.repositories;

import com.latashinsky.java.topics.entities.User;

public interface UserRepository<T extends User<?>> extends MyRepository<T> {
}
