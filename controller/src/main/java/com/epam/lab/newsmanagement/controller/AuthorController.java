package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.service.AuthorService;
import com.epam.lab.newsmanagement.service.IntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/author/")
public class AuthorController extends AbstractController<Author, AuthorDto> {
    @Autowired
    private AuthorService service;

    @Override
    @PostMapping(value = "/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<AuthorDto> create(@RequestBody AuthorDto authorDto) {
        return super.create(authorDto);
    }

    @Override
    @GetMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<AuthorDto> read(@PathVariable("id") long id) {
        return super.read(id);
    }

    @Override
    @PutMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<AuthorDto> update(@PathVariable long id, @RequestBody AuthorDto authorDto) {
        return super.update(id, authorDto);
    }

    @Override
    @DeleteMapping(value = "/{id}/",
            produces = PRODUCES,
            consumes = CONSUMES)
    public ResponseEntity<AuthorDto> delete(@PathVariable("id") long id) {
        return super.delete(id);
    }

    @Override
    IntService getService() {
        return service;
    }

    @Override
    AuthorDto createEntity(long id) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(id);
        return authorDto;
    }

    @Override
    void setId(AuthorDto authorDto, long id) {
        authorDto.setId(id);
    }
}
