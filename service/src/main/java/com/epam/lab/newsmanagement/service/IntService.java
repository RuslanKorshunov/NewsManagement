package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.ServiceException;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

public interface IntService<T> {
    T create(T t) throws ServiceException;

    List<T> create(List<T> t) throws ServiceException;

    T read(long id) throws ServiceException;

    List<T> read(SearchCriteria sc) throws ServiceException;

    List<T> read(SortCriteria sc) throws ServiceException;

    T update(T t) throws ServiceException;

    T delete(long id) throws ServiceException;
}
