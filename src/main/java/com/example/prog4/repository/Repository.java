package com.example.prog4.repository;

@org.springframework.stereotype.Repository
public interface Repository<T> {
    T findById(String id);

    T save(T toSave);

}
