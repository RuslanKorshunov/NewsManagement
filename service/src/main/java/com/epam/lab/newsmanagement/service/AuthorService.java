package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AbstractMapper;
import com.epam.lab.newsmanagement.mapper.AuthorMapper;
import com.epam.lab.newsmanagement.validator.AuthorValidator;
import com.epam.lab.newsmanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("authorService")
public class AuthorService extends AbstractService<Author, AuthorDto> {
    private AuthorDao dao;
    private AuthorValidator validator;
    private AuthorMapper mapper;

    @Autowired
    public AuthorService(AuthorDao dao, AuthorValidator validator, AuthorMapper mapper) {
        this.dao = dao;
        this.validator = validator;
        this.mapper = mapper;
    }

    @Override
    public AuthorDto create(AuthorDto authorDto) throws ServiceException {
        return super.create(authorDto);
    }

    @Override
    public List<AuthorDto> create(List<AuthorDto> t) throws ServiceException {
        return super.create(t);
    }

    @Override
    public AuthorDto read(long id) throws ServiceException {
        return super.read(id);
    }

    @Override
    public AuthorDto update(AuthorDto authorDto) throws ServiceException {
        return super.update(authorDto);
    }

    @Override
    public AuthorDto delete(long id) throws ServiceException {
        return super.delete(id);
    }

    @Override
    Dao<Author> getDao() {
        return dao;
    }

    @Override
    Validator<Author> getValidator() {
        return validator;
    }

    @Override
    AbstractMapper<Author, AuthorDto> getMapper() {
        return mapper;
    }
}
