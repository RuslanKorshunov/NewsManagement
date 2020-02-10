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

    //TODO исправить это ввведение автоинкремента; проблема при пустой таблице
    default long getMaxId(JdbcTemplate jdbcTemplate, String SELECT_MAX_INDEX_QUERY) throws DaoException {
        long id;
        try {
            Optional<Long> optionalLong = Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_MAX_INDEX_QUERY, Long.class));
            if (optionalLong.isPresent()) {
                id = optionalLong.get();
            } else {
                throw new DaoException("Dao can't get max id.");
            }
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return id;
    }
}
