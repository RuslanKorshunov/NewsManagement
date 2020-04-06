package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

@Repository("authorDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
public class AuthorDao extends AbstractDao<Author> implements AuthorDaoInterface {
    @Override
    @Modifying
    public Author create(Author author) throws DaoException {
        Author result = read(author);
        if (result == null) {
            result = super.create(author);
        }
        return result;
    }

    @Override
    public Author read(long id) throws DaoException {
        return super.read(id);
    }

    @Override
    @Modifying
    public Author update(Author author) throws DaoException {
        Author old = read(author.getId());
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaUpdate<Author> update = builder.createCriteriaUpdate(Author.class);
            Root<Author> root = update.from(Author.class);
            update.set(Constants.NAME, author.getName());
            update.set(Constants.SURNAME, author.getSurname());
            update.where(builder.equal(root.get(Constants.ID), author.getId()));
            getEntityManager().createQuery(update).executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return old;
    }

    @Override
    @Modifying
    public Author delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Class<Author> getClassObject() {
        return Author.class;
    }

    private Author read(Author author) throws DaoException {
        Author result;
        try {
            CriteriaBuilder builder =
                    getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Author> query = builder.createQuery(getClassObject());
            Root<Author> root = query.from(getClassObject());
            query.select(root);
            query.where(
                    builder.equal(root.get(Constants.NAME), author.getName()),
                    builder.equal(root.get(Constants.SURNAME), author.getSurname()));
            result = getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            result = null;
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }
}
