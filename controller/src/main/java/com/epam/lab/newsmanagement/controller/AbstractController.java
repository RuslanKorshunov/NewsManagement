package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AbstractDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.IntService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

public abstract class AbstractController<N extends AbstractEntity, T extends AbstractDto> implements Controller<T> {
    private static Logger logger = LogManager.getLogger();

    private IntService<N, T> service;

    public AbstractController(IntService service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<T> create(T t) {
        HttpStatus status = HttpStatus.CREATED;
        try {
            t = getService().create(t);
        } catch (ServiceException e) {
            getLogger().error(e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(t, status);
    }

    @Override
    public ResponseEntity<T> read(long id) {
        HttpStatus status = HttpStatus.OK;
        T t;
        try {
            t = getService().read(id);
        } catch (ServiceException e) {
            getLogger().error(e);
            status = HttpStatus.NOT_FOUND;
            t = createEntity(id);
        }
        return new ResponseEntity<>(t, status);
    }

    @Override
    public ResponseEntity<List<T>> read(SearchCriteriaDto scd) {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<List<T>> read(SortCriteria sc) {
        return new ResponseEntity<>(null, HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public ResponseEntity<T> update(long id, T t) {
        setId(t, id);
        HttpStatus status = HttpStatus.OK;
        boolean isFound = false;
        try {
            if (getService().read(id) != null) {
                isFound = true;
            }
            getService().update(t);
        } catch (ServiceException e) {
            getLogger().error(e);
            status = !isFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(t, status);
    }

    @Override
    public ResponseEntity<T> delete(long id) {
        HttpStatus status = HttpStatus.OK;
        T t;
        try {
            t = getService().delete(id);
        } catch (ServiceException e) {
            getLogger().error(e);
            status = HttpStatus.NOT_FOUND;
            t = createEntity(id);
        }
        return new ResponseEntity<>(t, status);
    }

    final Logger getLogger() {
        return logger;
    }

    final IntService<N, T> getService() {
        return service;
    }

    abstract T createEntity(long id);

    abstract void setId(T t, long id);
}
