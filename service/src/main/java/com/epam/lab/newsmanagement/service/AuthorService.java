package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.AuthorValidator;
import com.epam.lab.newsmanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("authorService")
public class AuthorService extends AbstractService<Author> {
    @Autowired
    private AuthorDao dao;
    @Autowired
    private AuthorValidator validator;

    @Override
    public Author create(Author author) throws ServiceException {
        return super.create(author);
    }

    @Override
    public List<Author> create(List<Author> t) throws ServiceException {
        return super.create(t);
    }

    @Override
    public Author read(long id) throws ServiceException {
        return super.read(id);
    }

    @Override
    public Author update(Author author) throws ServiceException {
        return super.update(author);
    }

    @Override
    public Author delete(long id) throws ServiceException {
        return super.delete(id);
    }

    @Override
    Dao<Author> getDao() {
        return dao;
    }

    @Override
    Validator getValidator() {
        return validator;
    }

    @Override
    Author getClone(Author author) throws CloneNotSupportedException {
        return author.clone();
    }
}
