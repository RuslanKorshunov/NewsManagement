package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.function.Supplier;

public abstract class AbstractDao<T extends AbstractEntity> implements DaoInterface<T> {
    private static final String ID;

    static {
        ID = "id";
    }

    @Override
    public T create(T t) throws DaoException {
/*        Supplier<T> supplier = getSupplier(t);
        T t2 = supplier.get();
        if (t2 == null) {
            try {
                t2 = getClone(t);
                KeyHolder keyHolder = new GeneratedKeyHolder();
                getJdbcTemplate().update(getCreatorForCreateQuery(t), keyHolder);
                long id = keyHolder.getKey().longValue();
                setId(t2, id);
            } catch (DataAccessException | CloneNotSupportedException e) {
                throw new DaoException(e);
            }
        }
        return t2;*/
        if (getEntityManager() == null) {
            throw new DaoException("EntityManager is null.");
        }
        try {
            getEntityManager().persist(t);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public T read(long id) throws DaoException {
        if (getEntityManager() == null) {
            throw new DaoException("EntityManager is null");
        }
        T t;
        try {
            CriteriaBuilder builder =
                    getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClassObject());
            Root<T> root = query.from(getClassObject());
            query.select(root);
            query.where(builder.equal(root.get(ID), id));
            t = getEntityManager().createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public T update(T t) throws DaoException {
        try {
            getJdbcTemplate().update(getUpdateQuery(), getParametersForUpdate(t));
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public T delete(long id) throws DaoException {
        T t = read(id);
        try {
            getJdbcTemplate().update(getDeleteQuery(), id);
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

    abstract JdbcTemplate getJdbcTemplate();

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

    abstract EntityManager getEntityManager();

    abstract Class<T> getClassObject();
}
