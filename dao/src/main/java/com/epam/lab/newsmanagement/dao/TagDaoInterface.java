package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;

import java.util.List;

public interface TagDaoInterface extends DaoInterface<Tag> {
    List<Tag> create(List<Tag> tags) throws DaoException;
}
