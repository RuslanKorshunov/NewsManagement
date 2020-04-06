package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.AbstractEntity;
import com.epam.lab.newsmanagement.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class AbstractDao<T extends AbstractEntity> implements DaoInterface<T> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public T create(T t) throws DaoException {
        try {
            entityManager.persist(t);
            entityManager.flush();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public T read(long id) throws DaoException {
        T t;
        try {
            CriteriaBuilder builder =
                    entityManager.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(getClassObject());
            Root<T> root = query.from(getClassObject());
            query.select(root);
            query.where(builder.equal(root.get(Constants.ID), id));
            t = entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return t;
    }

    @Override
    public abstract T update(T t) throws DaoException;

    @Override
    public T delete(long id) throws DaoException {
        T t = read(id);
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaDelete<T> delete = builder.createCriteriaDelete(getClassObject());
            Root<T> root = delete.from(getClassObject());
            delete.where(builder.equal(root.get(Constants.ID), id));
            entityManager.createQuery(delete).executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return t;
    }

    EntityManager getEntityManager() {
        return entityManager;
    }

    abstract Class<T> getClassObject();
}
