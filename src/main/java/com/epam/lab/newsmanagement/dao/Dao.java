package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

public interface Dao<T> {
    T create(T t) throws DaoException;

    T read(long id) throws DaoException;

    T update(T t) throws DaoException;

    T delete(long id) throws DaoException;
}
