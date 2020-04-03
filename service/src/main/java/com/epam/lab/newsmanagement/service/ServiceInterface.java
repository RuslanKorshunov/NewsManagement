package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dto.AbstractDto;
import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.ServiceException;

public interface ServiceInterface<N extends AbstractEntity, T extends AbstractDto> {
    T create(T t) throws ServiceException;

    //List<T> create(List<T> t) throws ServiceException;

    T read(long id) throws ServiceException;

    //List<T> read(SearchCriteriaDto sc) throws ServiceException;

    //List<T> read(SortCriteria sc) throws ServiceException;

    T update(T t) throws ServiceException;

    T delete(long id) throws ServiceException;
}
