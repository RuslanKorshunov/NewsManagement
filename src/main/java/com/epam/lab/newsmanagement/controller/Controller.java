package com.epam.lab.newsmanagement.controller;

import org.springframework.http.ResponseEntity;

public interface Controller<T> {
    String PRODUCES = "application/json";
    String CONSUMES = PRODUCES;

    ResponseEntity<T> create(T t);

    ResponseEntity<T> read(long id);

    ResponseEntity<T> update(long id, T t);

    ResponseEntity<T> delete(long id);
}
