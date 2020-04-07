package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.List;

@Repository("newsDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
public class NewsDao extends AbstractDao<News> implements NewsDaoInterface {
    @Override
    @Modifying
    public News create(News news) throws DaoException {
        return super.create(news);
    }

    @Override
    public News read(long id) throws DaoException {
        return super.read(id);
    }

    @Override
    public List<News> read(SearchCriteria sc) throws DaoException {
        List<News> result;
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<News> query = builder.createQuery(getClassObject());
            Root<News> newsRoot = query.from(getClassObject());
            query.select(newsRoot);
            Author author = sc.getAuthor();
            if (author != null) {
                Predicate predicateAuthor = author != null ? builder.and(
                        builder.equal(newsRoot.get(Constants.AUTHOR).
                                get(Constants.NAME), sc.getAuthor().getName()),
                        builder.equal(newsRoot.get(Constants.AUTHOR).
                                get(Constants.SURNAME), sc.getAuthor().getSurname())) : null;
                query.where(predicateAuthor);
            }
            result = getEntityManager().createQuery(query).getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<News> read(SortCriteria sc) throws DaoException {
        List<News> result;
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<News> query = builder.createQuery(getClassObject());
            Root<News> newsRoot = query.from(getClassObject());
            query.select(newsRoot);
            switch (sc) {
                case AUTHOR:
                    query.orderBy(builder.asc(newsRoot.get(Constants.AUTHOR).get(Constants.SURNAME)),
                            builder.asc(newsRoot.get(Constants.AUTHOR).get(Constants.NAME)));
                    break;
                case DATE:
                    query.orderBy(builder.desc(newsRoot.get(Constants.MODIFICATION_DATE)));
                    break;
            }
            result = getEntityManager().createQuery(query).getResultList();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    @Modifying
    public News update(News news) throws DaoException {
        News old = read(news.getId()).clone();
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaUpdate<News> update = builder.createCriteriaUpdate(getClassObject());
            Root<News> root = update.from(getClassObject());
            update.set(Constants.TITLE, news.getTitle());
            update.set(Constants.SHORT_TEXT, news.getShortText());
            update.set(Constants.FULL_TEXT, news.getFullText());
            update.set(Constants.AUTHOR, news.getAuthor());
            update.set(Constants.MODIFICATION_DATE, LocalDate.now());
            update.where(builder.equal(root.get(Constants.ID), news.getId()));
            getEntityManager().createQuery(update).executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return old;
    }

    @Override
    @Modifying
    public News delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Class<News> getClassObject() {
        return News.class;
    }
}
