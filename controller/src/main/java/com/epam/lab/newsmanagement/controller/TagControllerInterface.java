package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.TagDto;
import org.springframework.http.ResponseEntity;

public interface TagControllerInterface extends ControllerInterface<TagDto> {
    @Override
    ResponseEntity<TagDto> create(TagDto tagDto);

    @Override
    ResponseEntity<TagDto> read(long id);

    @Override
    ResponseEntity<TagDto> update(long id, TagDto tagDto);

    @Override
    ResponseEntity<TagDto> delete(long id);
}
