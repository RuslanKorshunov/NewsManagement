package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.exception.DaoException;

public interface Dao<T> {
    T add(T t) throws DaoException;
}
