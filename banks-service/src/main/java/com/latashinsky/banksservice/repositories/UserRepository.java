package com.latashinsky.banksservice.repositories;

import com.latashinsky.banksservice.entities.User;

public interface UserRepository<T extends User<?>> extends MyRepository<T> {
}
