package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.exception.ServiceException;

public interface IntService<T> {
    T create(T t) throws ServiceException;

    T read(long id) throws ServiceException;

    T update(T t) throws ServiceException;

    T delete(long id) throws ServiceException;
}