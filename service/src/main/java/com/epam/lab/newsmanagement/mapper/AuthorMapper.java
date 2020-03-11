package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper extends AbstractMapper<Author, AuthorDto> {
    @Autowired
    private ModelMapper mapper;

    @Override
    protected Author getEntity(AuthorDto dto) {
        return mapper.map(dto, Author.class);
    }

    @Override
    protected AuthorDto getDto(Author author) {
        return mapper.map(author, AuthorDto.class);
    }
}
