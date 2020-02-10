package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.exception.DaoException;

public interface Dao<T> {
    T create(T t) throws DaoException;

    T read(long id) throws DaoException;

    T update(T t) throws DaoException;
}
