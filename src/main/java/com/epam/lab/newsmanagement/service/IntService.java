package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.exception.ServiceException;

public interface IntService<T> {
    T add(T t) throws ServiceException;
}
