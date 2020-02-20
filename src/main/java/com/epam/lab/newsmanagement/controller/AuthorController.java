package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.service.AuthorService;
import com.epam.lab.newsmanagement.service.IntService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/author/")
public class AuthorController extends AbstractController<Author> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(AuthorController.class);
    }

    @Autowired
    private AuthorService service;

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return super.create(author);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Author> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Author> update(@PathVariable long id, @RequestBody Author author) {
        return super.update(id, author);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Author> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    IntService<Author> getService() {
        return service;
    }

    @Override
    Author createEntity(long id) {
        Author author = new Author();
        author.setId(id);
        return author;
    }

    @Override
    void setId(Author author, long id) {
        author.setId(id);
    }

    @Override
    Logger getLogger() {
        return logger;
    }
}
