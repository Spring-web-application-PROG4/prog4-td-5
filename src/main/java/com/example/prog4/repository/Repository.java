package com.example.prog4.repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface Repository<T> {
    Optional<T> findById(String id);

    T save(T toSave);

    boolean existsById(String id);
}
