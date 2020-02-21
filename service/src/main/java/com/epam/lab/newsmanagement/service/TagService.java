package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.dao.TagDao;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.TagValidator;
import com.epam.lab.newsmanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("tagService")
public class TagService extends AbstractService<Tag> {
    @Autowired
    private TagDao dao;
    @Autowired
    private TagValidator validator;

    @Override
    public Tag create(Tag tag) throws ServiceException {
        try {
            validator.validate(tag);
            tag = tag.clone();
            String name = tag.getName();
            name = name.toLowerCase();
            tag.setName(name);
            tag = dao.create(tag);
        } catch (DaoException | CloneNotSupportedException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public List<Tag> create(List<Tag> tags) throws ServiceException {
        if (tags == null || tags.isEmpty()) {
            throw new ServiceException("parameter \"tags\" can't be null or empty.");
        }
        try {
            for (Tag tag : tags) {
                validator.validate(tag);
                toLoverCase(tag);
            }
            tags = dao.create(tags);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tags;
    }

    @Override
    public Tag read(long id) throws ServiceException {
        return super.read(id);
    }

    @Override
    public Tag update(Tag tag) throws ServiceException {
        try {
            validator.validate(tag);
            toLoverCase(tag);
            getDao().update(tag);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tag;
    }

    @Override
    public Tag delete(long id) throws ServiceException {
        return super.delete(id);
    }

    @Override
    Dao<Tag> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    private void toLoverCase(Tag tag) {
        String name = tag.getName();
        name = name.toLowerCase();
        tag.setName(name);
    }
}
