package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController implements Controller<Tag> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(TagController.class);
    }

    @Autowired
    private TagService service;

    @Override
    @PostMapping(value = "/tag/",
            produces = "application/json",
            consumes = "application/json")
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        HttpStatus status = HttpStatus.CREATED;
        try {
            tag = service.create(tag);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(tag, status);
    }

    @Override
    public ResponseEntity<Tag> read(long id) {
        return null;
    }

    @Override
    public ResponseEntity<Tag> update(long id, Tag tag) {
        return null;
    }

    @Override
    public ResponseEntity<Tag> delete(long id) {
        return null;
    }
}
