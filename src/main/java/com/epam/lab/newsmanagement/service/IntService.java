package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.exception.ServiceException;

import java.util.List;

public interface IntService<T> {
    T create(T t) throws ServiceException;

    List<T> create(List<T> t) throws ServiceException;

    T read(long id) throws ServiceException;

    T update(T t) throws ServiceException;

    T delete(long id) throws ServiceException;

    void validate(T t) throws ServiceException;
}
