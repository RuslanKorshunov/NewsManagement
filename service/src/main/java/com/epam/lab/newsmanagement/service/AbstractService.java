package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.DaoInterface;
import com.epam.lab.newsmanagement.dto.AbstractDto;
import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AbstractMapper;
import com.epam.lab.newsmanagement.validator.Validator;

public abstract class AbstractService<N extends AbstractEntity, T extends AbstractDto> implements ServiceInterface<N, T> {
    @Override
    public T create(T t) throws ServiceException {
        N n = getMapper().toEntity(t);
        Validator<N> validator = getValidator();
        try {
            validator.validate(n);
            n = getDao().create(n);
            t = getMapper().toDto(n);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public T read(long id) throws ServiceException {
        T t;
        try {
            N n = getDao().read(id);
            t = getMapper().toDto(n);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public T update(T t) throws ServiceException {
        N n = getMapper().toEntity(t);
        Validator<N> validator = getValidator();
        try {
            validator.validate(n);
            getDao().update(n);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    @Override
    public T delete(long id) throws ServiceException {
        T t;
        try {
            N n = getDao().delete(id);
            t = getMapper().toDto(n);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return t;
    }

    abstract Validator<N> getValidator();

    abstract DaoInterface<N> getDao();

    abstract AbstractMapper<N, T> getMapper();
}
