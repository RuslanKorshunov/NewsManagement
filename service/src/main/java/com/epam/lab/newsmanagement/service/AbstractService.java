package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.dto.AbstractDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AbstractMapper;
import com.epam.lab.newsmanagement.validator.Validator;

import java.util.List;

public abstract class AbstractService<N extends AbstractEntity, T extends AbstractDto> implements IntService<N, T> {
    private Dao<N> dao;
    private Validator<N> validator;
    private AbstractMapper<N, T> mapper;

    public AbstractService(Dao dao, Validator validator, AbstractMapper mapper) {
        this.dao = dao;
        this.validator = validator;
        this.mapper = mapper;
    }

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
    public List<T> create(List<T> t) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
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
    public List<T> read(SearchCriteriaDto sc) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
    }

    @Override
    public List<T> read(NewsDao.SortCriteria sc) throws ServiceException {
        throw new ServiceException("Operation isn't supported by service.");
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

    final Validator<N> getValidator() {
        return validator;
    }

    final Dao<N> getDao() {
        return dao;
    }

    final AbstractMapper<N, T> getMapper() {
        return mapper;
    }
}
