package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController implements Controller<Author> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(AuthorController.class);
    }

    @Autowired
    private AuthorService authorService;

    @Override
    @RequestMapping(value = "/author/",
            produces = "application/json",
            consumes = "application/json",
            method = RequestMethod.POST)
    public ResponseEntity<Author> create(@RequestBody Author author) {
        HttpStatus status = HttpStatus.CREATED;
        try {
            authorService.add(author);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(author, status);
    }
}
