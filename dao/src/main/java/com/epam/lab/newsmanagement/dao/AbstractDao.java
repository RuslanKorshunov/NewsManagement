package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDao<T extends AbstractEntity> implements DaoInterface<T> {
    private static final String ID;

    static {
        ID = "id";
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public T create(T t) throws DaoException {
        if (entityManager == null) {
            throw new DaoException("EntityManager is null.");
        }
        try {
            entityManager.persist(t);
        } catch (IllegalArgumentException | TransactionRequiredException e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public T read(long id) throws DaoException {
        if (entityManager == null) {
            throw new DaoException("EntityManager is null");
        }
        T t;
        try {
            CriteriaBuilder builder =
                    entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClassObject());
            Root<T> root = query.from(getClassObject());
            query.select(root);
            query.where(builder.equal(root.get(ID), id));
            t = entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public T update(T t) throws DaoException {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public T delete(long id) throws DaoException {
        return null;
    }

    abstract Class<T> getClassObject();
}
