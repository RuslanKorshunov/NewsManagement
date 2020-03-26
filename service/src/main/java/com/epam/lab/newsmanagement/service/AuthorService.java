package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AuthorMapper;
import com.epam.lab.newsmanagement.validator.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("authorService")
public class AuthorService extends AbstractService<Author, AuthorDto> {
    @Autowired
    public AuthorService(AuthorDao dao, AuthorValidator validator, AuthorMapper mapper) {
        super(dao, validator, mapper);
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
}
