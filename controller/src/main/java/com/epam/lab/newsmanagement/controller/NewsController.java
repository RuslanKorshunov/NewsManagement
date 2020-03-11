package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.entity.News;
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
public class NewsController extends AbstractController<News, NewsDto> {
    private NewsService service;

    @Autowired
    public NewsController(NewsService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<NewsDto> create(@RequestBody NewsDto newsDto) {
        return super.create(newsDto);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<NewsDto> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<NewsDto> update(@PathVariable("id") long id, @RequestBody NewsDto newsDto) {
        return super.update(id, newsDto);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<NewsDto> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping(value = "/search/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<List<NewsDto>> read(@RequestBody SearchCriteriaDto scd) {
        HttpStatus status = HttpStatus.OK;
        List<NewsDto> news;
        try {
            news = service.read(scd);
        } catch (ServiceException e) {
            Logger logger = LogManager.getLogger(NewsController.class);
            logger.error(e);
            status = HttpStatus.NOT_FOUND;
            news = new ArrayList<>();
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    @GetMapping(value = "/sort/{criteria}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<List<NewsDto>> read(@PathVariable("criteria") SortCriteria sc) {
        HttpStatus status = HttpStatus.OK;
        List<NewsDto> news;
        try {
            news = service.read(sc);
        } catch (ServiceException e) {
            getLogger().error(e);
            status = HttpStatus.NOT_FOUND;
            news = new ArrayList<>();
        }
        return new ResponseEntity<>(news, status);
    }

    @Override
    IntService getService() {
        return service;
    }

    @Override
    NewsDto createEntity(long id) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(id);
        return newsDto;
    }

    @Override
    void setId(NewsDto newsDto, long id) {
        newsDto.setId(id);
    }
}
