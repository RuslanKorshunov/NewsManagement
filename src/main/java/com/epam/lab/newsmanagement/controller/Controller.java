package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.SearchCriteria;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

public interface Controller<T> {
    String PRODUCES = "application/json";
    String CONSUMES = PRODUCES;

    ResponseEntity<T> create(T t);

    ResponseEntity<T> read(long id);

    ResponseEntity<List<T>> read(SearchCriteria sc);

    ResponseEntity<List<T>> read(SortCriteria sc);

    ResponseEntity<T> update(long id, T t);

    ResponseEntity<T> delete(long id);
}
