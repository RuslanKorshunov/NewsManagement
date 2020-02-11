package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.TagService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tag/")
public class TagController implements Controller<Tag> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(TagController.class);
    }

    @Autowired
    private TagService service;

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
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
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> read(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.OK;
        Tag tag;
        try {
            tag = service.read(id);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            tag = new Tag();
            tag.setId(id);
        }
        return new ResponseEntity<>(tag, status);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> update(@PathVariable("id") long id, @RequestBody Tag tag) {
        tag.setId(id);
        HttpStatus status = HttpStatus.OK;
        boolean isFound = false;
        try {
            if (service.read(id) != null) {
                isFound = true;
            }
            service.update(tag);
        } catch (ServiceException e) {
            logger.error(e);
            status = !isFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(tag, status);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> delete(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.OK;
        Tag tag;
        try {
            tag = service.delete(id);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            tag = new Tag();
            tag.setId(id);
        }
        return new ResponseEntity<>(tag, status);
    }
}
