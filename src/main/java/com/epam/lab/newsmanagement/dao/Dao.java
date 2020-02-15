package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.exception.DaoException;

import java.util.List;

public interface Dao<T> {
    T create(T t) throws DaoException;

    List<T> create(List<T> t) throws DaoException;

    T read(long id) throws DaoException;

    T update(T t) throws DaoException;

    T delete(long id) throws DaoException;
}
