package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AbstractDto;
import org.springframework.http.ResponseEntity;

public interface ControllerInterface<T extends AbstractDto> {
    String PRODUCES = "application/json";
    String CONSUMES = PRODUCES;

    ResponseEntity<T> create(T t);

    ResponseEntity<T> read(long id);

/*    ResponseEntity<List<T>> read(SearchCriteriaDto scd);

    ResponseEntity<List<T>> read(SortCriteria sc);*/

    ResponseEntity<T> update(long id, T t);

    ResponseEntity<T> delete(long id);
}
