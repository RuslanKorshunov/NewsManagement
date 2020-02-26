package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.dao.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.Validator;

import java.util.List;

public abstract class AbstractService<T> implements IntService<T> {
    @Override
    public T create(T t) throws ServiceException {
        Validator<T> validator = getValidator();
        try {
            validator.validate(t);
            t = getDao().create(t);
        } catch (DaoException | IncorrectDataException e) {
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
    public List<T> read(SearchCriteria sc) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
    }

    @Override
    public List<T> read(NewsDao.SortCriteria sc) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
    }

    @Override
    public T update(T t) throws ServiceException {
        Validator<T> validator = getValidator();
        try {
            validator.validate(t);
            getDao().update(t);
        } catch (DaoException | IncorrectDataException e) {
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

    abstract Validator<T> getValidator();

    abstract Dao<T> getDao();
}
