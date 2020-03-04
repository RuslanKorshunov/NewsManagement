package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.config.ServiceTestConfig;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class AuthorMapperTest {
    @Autowired
    private AuthorMapper mapper;

    private static AuthorDto dto;
    private static Author author;

    @BeforeClass
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
    public void toAuthorTest() {
        Author author = mapper.toEntity(dto);
        boolean result = author.equals(this.author);
        Assert.assertTrue(result);
    }

    @Test
    public void toDtoTest() {
        AuthorDto dto = mapper.toDto(author);
        boolean result = dto.equals(this.dto);
        Assert.assertTrue(result);
    }
}