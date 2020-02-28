package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.dao.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;

import java.util.List;

public interface Dao<T extends AbstractEntity> {
    T create(T t) throws DaoException;

    List<T> create(List<T> t) throws DaoException;

    T read(long id) throws DaoException;

    List<T> read(SearchCriteria sc) throws DaoException;

    T update(T t) throws DaoException;

    T delete(long id) throws DaoException;
}
