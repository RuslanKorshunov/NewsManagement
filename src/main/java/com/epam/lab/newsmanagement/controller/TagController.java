package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.service.IntService;
import com.epam.lab.newsmanagement.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tag/")
public class TagController extends AbstractController<Tag> {

    @Autowired
    private TagService service;

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> create(@RequestBody Tag tag) {
        return super.create(tag);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> update(@PathVariable("id") long id, @RequestBody Tag tag) {
        return super.update(id, tag);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<Tag> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    IntService<Tag> getService() {
        return service;
    }

    @Override
    Tag createEntity(long id) {
        Tag tag = new Tag();
        tag.setId(id);
        return tag;
    }

    @Override
    void setId(Tag tag, long id) {
        tag.setId(id);
    }
}
