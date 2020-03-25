package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.DaoException;

import java.util.List;

public interface NewsDaoInterface extends DaoInterface<News> {
    enum SortCriteria {
        DATE,
        AUTHOR
    }

    List<News> read(SortCriteria sc) throws DaoException;

    List<News> read(SearchCriteria sc) throws DaoException;
}
