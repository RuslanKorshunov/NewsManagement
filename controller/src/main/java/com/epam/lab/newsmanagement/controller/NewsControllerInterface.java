package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDaoInterface.SortCriteria;

public interface NewsControllerInterface extends ControllerInterface<NewsDto> {
    @Override
    ResponseEntity<NewsDto> create(NewsDto newsDto);

    @Override
    ResponseEntity<NewsDto> read(long id);

    ResponseEntity<List<NewsDto>> read(SearchCriteriaDto scd);

    ResponseEntity<List<NewsDto>> read(SortCriteria sc);

    @Override
    ResponseEntity<NewsDto> update(long id, NewsDto newsDto);

    @Override
    ResponseEntity<NewsDto> delete(long id);
}
