package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.validator.NumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("newsDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
public class NewsDao extends AbstractDao<News> implements NewsDaoInterface {
    @Autowired
    private NumberValidator validator;

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
        return null;
    }

    @Override
    public List<News> read(SortCriteria sc) throws DaoException {
        return null;
    }

    @Override
    @Modifying
    public News update(News news) throws DaoException {
        return super.update(news);
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
