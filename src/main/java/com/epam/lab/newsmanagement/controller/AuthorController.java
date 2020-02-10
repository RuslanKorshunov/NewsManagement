package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.AuthorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorController implements Controller<Author> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(AuthorController.class);
    }

    @Autowired
    private AuthorService authorService;

    @Override
    @PostMapping(value = "/author/",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<Author> create(@RequestBody Author author) {
        HttpStatus status = HttpStatus.CREATED;
        try {
            authorService.create(author);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(author, status);
    }

    @Override
    @GetMapping(value = "/author/{id}/",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<Author> read(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.OK;
        Author author = new Author();
        author.setId(id);
        try {
            author = authorService.read(id);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.NOT_FOUND;//TODO проверить статус
        }
        return new ResponseEntity<>(author, status);
    }

    @Override
    @PutMapping(value = "/author/{id}/",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<Author> update(@PathVariable long id, @RequestBody Author author) {
        HttpStatus status = HttpStatus.OK;
        Author currentAuthor = null;
        try {
            currentAuthor = authorService.read(id);
            String name = author.getName();
            currentAuthor.setName(name);
            String surname = author.getSurname();
            currentAuthor.setSurname(surname);
            authorService.update(currentAuthor);
        } catch (ServiceException e) {
            logger.error(e);
            status = currentAuthor == null ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(currentAuthor, status);
    }
}
