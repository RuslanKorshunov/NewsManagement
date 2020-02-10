package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IntService<Author> {
    @Autowired
    private AuthorDao dao;

    @Override
    public Author create(Author author) throws ServiceException {
        validate(author);
        try {
            dao.create(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return author;
    }

    @Override
    public Author read(long id) throws ServiceException {
        Author author;
        try {
            author = dao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return author;
    }

    @Override
    public Author update(Author author) throws ServiceException {
        validate(author);
        try {
            dao.update(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return author;
    }

    private void validate(Author author) throws ServiceException {
        if (author == null) {
            throw new ServiceException("parameter \"author\" can't be null.");
        }
        String name = author.getName();
        String surname = author.getSurname();
        if (name == null || !NameValidator.validate(name)) {
            throw new ServiceException("Author's name has invalid value \"" + name + "\".");
        }
        if (surname == null || !NameValidator.validate(surname)) {
            throw new ServiceException("Author's surname has invalid value \"" + surname + "\".");
        }
    }
}
