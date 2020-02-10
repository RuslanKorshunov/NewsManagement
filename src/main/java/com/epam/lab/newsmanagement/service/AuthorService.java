package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService implements IntService<Author> {
    @Autowired
    private AuthorDao dao;

    @Override
    public Author create(Author author) throws ServiceException {
        String name = author.getName();
        String surname = author.getSurname();
        if (name == null || name.equals("")) {
            throw new ServiceException("Author's name has invalid value. It's \"" + name + "\".");
        }
        if (surname == null || surname.equals("")) {
            throw new ServiceException("Author's surname has invalid value. It's \"" + name + "\".");
        }
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
        String name = author.getName();
        String surname = author.getSurname();
        if (name == null || name.equals("")) {
            throw new ServiceException("Author's name has invalid value. It's \"" + name + "\".");
        }
        if (surname == null || surname.equals("")) {
            throw new ServiceException("Author's surname has invalid value. It's \"" + name + "\".");
        }
        try {
            dao.update(author);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return author;
    }
}
