package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.exception.ServiceException;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDaoInterface.SortCriteria;

public interface NewsServiceInterface extends ServiceInterface<News, NewsDto> {
    @Override
    NewsDto create(NewsDto newsDto) throws ServiceException;

    @Override
    NewsDto read(long id) throws ServiceException;

    List<NewsDto> read(SearchCriteriaDto sc) throws ServiceException;

    List<NewsDto> read(SortCriteria sc) throws ServiceException;

    @Override
    NewsDto update(NewsDto newsDto) throws ServiceException;

    @Override
    NewsDto delete(long id) throws ServiceException;
}
