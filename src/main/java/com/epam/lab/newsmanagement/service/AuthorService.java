package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("authorService")
public class AuthorService extends AbstractService<Author> {
    @Autowired
    private AuthorDao dao;

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
    public void validate(Author author) throws ServiceException {
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

    @Override
    Dao<Author> getDao() {
        return dao;
    }
}
