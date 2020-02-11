package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.TagDao;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService implements IntService<Tag> {
    @Autowired
    private TagDao dao;

    @Override
    public Tag create(Tag tag) throws ServiceException {
        validate(tag);
        String name = tag.getName();
        name = name.toLowerCase();
        tag.setName(name);
        try {
            tag = dao.create(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public Tag read(long id) throws ServiceException {
        Tag tag;
        try {
            tag = dao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public Tag update(Tag tag) throws ServiceException {
        validate(tag);
        try {
            dao.update(tag);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public Tag delete(long id) throws ServiceException {
        Tag tag;
        try {
            tag = dao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    //TODO убрать дублирование
    private void validate(Tag tag) throws ServiceException {
        if (tag == null) {
            throw new ServiceException("parameter \"tag\" can't be null.");
        }
        String name = tag.getName();
        if (name == null || !NameValidator.validate(name)) {
            throw new ServiceException("Tag's name has invalid value \"" + name + "\".");
        }
    }
}
