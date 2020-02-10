package com.epam.lab.newsmanagement.controller;

import org.springframework.http.ResponseEntity;

public interface Controller<T> {

    ResponseEntity<T> create(T t);

    ResponseEntity<T> read(long id);

    ResponseEntity<T> update(long id, T t);
}
