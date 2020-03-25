package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class AuthorMapperTest {
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AuthorMapper authorMapper;

    private static AuthorDto dto;
    private static Author author;

    @BeforeClass
    @Ignore
    public static void initialize() {
        long id = 31;
        String name = "Ruslan";
        String surname = "Korshunov";
        dto = new AuthorDto(id, name, surname);
        dto.setId(id);
        dto.setName(name);
        dto.setSurname(surname);
        author = new Author(id, name, surname);
    }

    @Test
    @Ignore
    public void toAuthorTest() {
        Mockito.when(mapper.map(dto, Author.class)).thenReturn(author);
        Author author = authorMapper.toEntity(dto);
        boolean result = author.equals(this.author);
        Assert.assertTrue(result);
    }

    @Test
    @Ignore
    public void toDtoTest() {
        Mockito.when(mapper.map(author, AuthorDto.class)).thenReturn(dto);
        AuthorDto dto = authorMapper.toDto(author);
        boolean result = dto.equals(this.dto);
        Assert.assertTrue(result);
    }
}