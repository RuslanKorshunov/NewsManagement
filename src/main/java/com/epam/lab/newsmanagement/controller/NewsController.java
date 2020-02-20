package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.IntService;
import com.epam.lab.newsmanagement.service.NewsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@RestController
@RequestMapping(value = "/news/")
public class NewsController extends AbstractController<News> {
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
        return super.create(news);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> update(@PathVariable("id") long id, @RequestBody News news) {
        return super.update(id, news);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<News> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping(value = "/search/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<List<News>> read(@RequestBody SearchCriteria searchCriteria) {
        HttpStatus status = HttpStatus.OK;
        List<News> news;
        try {
            news = service.read(searchCriteria);
        } catch (ServiceException e) {
            Logger logger = LogManager.getLogger(NewsController.class);
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            news = new ArrayList<>();
        }
        return new ResponseEntity<>(news, status);
    }

    @GetMapping(value = "/sort/{criteria}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<List<News>> read(@PathVariable("criteria") SortCriteria sc) {
        HttpStatus status = HttpStatus.OK;
        List<News> news;
        try {
            news = service.read(sc);
        } catch (ServiceException e) {
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            news = new ArrayList<>();
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    IntService<News> getService() {
        return service;
    }

    @Override
    News createEntity(long id) {
        News news = new News();
        news.setId(id);
        return news;
    }

    @Override
    void setId(News news, long id) {
        news.setId(id);
    }

    @Override
    Logger getLogger() {
        return logger;
    }
}
