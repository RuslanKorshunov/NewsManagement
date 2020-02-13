package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news/")
public class NewsController implements Controller<News> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(NewsController.class);
    }

    @Autowired
    private NewsService service;

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> create(@RequestBody News news) {
        logger.info(news);
        HttpStatus status = HttpStatus.CREATED;
        try {
            news = service.create(news);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> read(@PathVariable("id") long id) {
        HttpStatus status = HttpStatus.OK;
        News news;
        try {
            news = service.read(id);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            news = new News();
            news.setId(id);
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> update(@PathVariable("id") long id, @RequestBody News news) {
        news.setId(id);
        HttpStatus status = HttpStatus.OK;
        boolean isFound = false;
        try {
            if (service.read(id) != null) {
                isFound = true;
            }
            news = service.update(news);
        } catch (ServiceException e) {
            logger.error(e);
            status = !isFound ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    public ResponseEntity<News> delete(long id) {
        return null;
    }
}
