package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;

public interface AuthorServiceInterface extends ServiceInterface<Author, AuthorDto> {
    @Override
    AuthorDto create(AuthorDto authorDto) throws ServiceException;

    @Override
    AuthorDto read(long id) throws ServiceException;

    @Override
    AuthorDto update(AuthorDto authorDto) throws ServiceException;

    @Override
    AuthorDto delete(long id) throws ServiceException;
}
