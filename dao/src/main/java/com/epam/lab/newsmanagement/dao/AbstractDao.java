package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractDao<T extends AbstractEntity> implements Dao<T> {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public T create(T t) throws DaoException {
        Supplier<T> supplier = getSupplier(t);
        T t2 = supplier.get();
        if (t2 == null) {
            try {
                t2 = getClone(t);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(getCreatorForCreateQuery(t), keyHolder);
                long id = keyHolder.getKey().longValue();
                setId(t2, id);
            } catch (DataAccessException | CloneNotSupportedException e) {
                throw new DaoException(e);
            }
        }
        return t2;
    }

    @Override
    public List<T> create(List<T> t) throws DaoException {
        throw new DaoException("Operation isn't supported by dao.");
    }

    @Override
    public T read(long id) throws DaoException {
        T t;
        try {
            t = readEntity(getSelectByIdQuery(), id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public List<T> read(SearchCriteria sc) throws DaoException {
        throw new DaoException("Operation isn't supported by dao.");
    }

    @Override
    public T update(T t) throws DaoException {
        try {
            jdbcTemplate.update(getUpdateQuery(), getParametersForUpdate(t));
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public T delete(long id) throws DaoException {
        T t = read(id);
        try {
            jdbcTemplate.update(getDeleteQuery(), id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return t;
    }

    Supplier<T> getSupplier(T t) {
        return () -> {
            T t2;
            try {
                t2 = readEntity(getQueryForSupplier(), getParametersForSupplier(t));
            } catch (DataAccessException exception) {
                t2 = null;
            }
            return t2;
        };
    }

    final JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    abstract T readEntity(String query, Object... objects) throws DataAccessException;

    abstract String getQueryForSupplier();

    abstract String getSelectByIdQuery();

    abstract String getUpdateQuery();

    abstract String getDeleteQuery();

    abstract Object[] getParametersForSupplier(T t);

    abstract Object[] getParametersForUpdate(T t);

    abstract PreparedStatementCreator getCreatorForCreateQuery(T t) throws DataAccessException;

    abstract T getClone(T t) throws CloneNotSupportedException;

    abstract void setId(T t, long id);
}
