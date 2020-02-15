package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.ServiceException;

import java.util.List;

public abstract class AbstractService<T> implements IntService<T> {
    @Override
    public T create(T t) throws ServiceException {
        validate(t);
        try {
            t = getDao().create(t);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public List<T> create(List<T> t) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
    }

    @Override
    public T read(long id) throws ServiceException {
        T t;
        try {
            t = getDao().read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public T update(T t) throws ServiceException {
        validate(t);
        try {
            getDao().update(t);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public T delete(long id) throws ServiceException {
        T t;
        try {
            t = getDao().delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public abstract void validate(T t) throws ServiceException;

    abstract Dao<T> getDao();
}
