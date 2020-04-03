package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import org.springframework.http.ResponseEntity;

public interface AuthorControllerInterface extends ControllerInterface<AuthorDto> {
    @Override
    ResponseEntity<AuthorDto> create(AuthorDto authorDto);

    @Override
    ResponseEntity<AuthorDto> read(long id);

    @Override
    ResponseEntity<AuthorDto> update(long id, AuthorDto authorDto);

    @Override
    ResponseEntity<AuthorDto> delete(long id);
}
