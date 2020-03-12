package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.service.IntService;
import com.epam.lab.newsmanagement.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/tag/")
public class TagController extends AbstractController<Tag, TagDto> {
    private TagService service;

    @Autowired
    public TagController(TagService service) {
        this.service = service;
    }

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<TagDto> create(@RequestBody TagDto tagDto) {
        return super.create(tagDto);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<TagDto> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<TagDto> update(@PathVariable("id") long id, @RequestBody TagDto tagDto) {
        return super.update(id, tagDto);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<TagDto> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    IntService getService() {
        return service;
    }

    @Override
    TagDto createEntity(long id) {
        TagDto tagDto = new TagDto();
        tagDto.setId(id);
        return tagDto;
    }

    @Override
    void setId(TagDto tag, long id) {
        tag.setId(id);
    }
}
