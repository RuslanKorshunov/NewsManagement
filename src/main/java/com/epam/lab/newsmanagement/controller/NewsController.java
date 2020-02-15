package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.service.IntService;
import com.epam.lab.newsmanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news/")
public class NewsController extends AbstractController<News> {

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
}
