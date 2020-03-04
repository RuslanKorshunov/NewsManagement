package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.config.ServiceTestConfig;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.Tag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class NewsMapperTest {
    @Autowired
    private NewsMapper mapper;

    private static News news;
    private static NewsDto newsDto;

    @BeforeClass
    public static void initialize() {
        long id = 20;
        String title = "Passengers leave Diamond";
        String shortText = "Passengers have begun leaving a quarantined cruise ship.";
        String fullText = "One Japanese health expert who visited the Diamond Princess at the port in Yokohama";
        LocalDate creationDate = LocalDate.now();

        long idAuthor = 31;
        String nameAuthor = "Ruslan";
        String surnameAuthor = "Korhunov";
        Author author = new Author(idAuthor, nameAuthor, surnameAuthor);
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(idAuthor);
        authorDto.setName(nameAuthor);
        authorDto.setSurname(surnameAuthor);

        long idTagOne = 1;
        String nameTagOne = "japan";
        Tag tagOne = new Tag(idTagOne, nameTagOne);
        TagDto tagDtoOne = new TagDto();
        tagDtoOne.setId(idTagOne);
        tagDtoOne.setName(nameTagOne);

        long idTagTwo = 2;
        String nameTagTwo = "coronavirus";
        Tag tagTwo = new Tag(idTagTwo, nameTagTwo);
        TagDto tagDtoTwo = new TagDto();
        tagDtoTwo.setId(idTagOne);
        tagDtoTwo.setName(nameTagOne);

        List<Tag> tags = Arrays.asList(tagOne, tagTwo);
        List<TagDto> tagDtos = Arrays.asList(tagDtoOne, tagDtoTwo);

        News n = new News();
        n.setId(id);
        n.setTitle(title);
        n.setShortText(shortText);
        n.setFullText(fullText);
        n.setAuthor(author);
        n.setTags(tags);
        n.setCreationDate(creationDate);
        n.setModificationDate(creationDate);
        news = n;

        NewsDto dto = new NewsDto();
        dto.setId(id);
        dto.setTitle(title);
        dto.setShortText(shortText);
        dto.setFullText(fullText);
        dto.setAuthorDto(authorDto);
        dto.setTagDtos(tagDtos);
        dto.setCreationDate(creationDate);
        dto.setModificationDate(creationDate);
        newsDto = dto;
    }

    @Test
    public void toNewsTest() {
        News news = mapper.toEntity(newsDto);
        boolean result = news.getAuthor() != null && news.getTags() != null;
        Assert.assertTrue(result);
    }

    @Test
    public void toDtoTest() {
        NewsDto dto = mapper.toDto(news);
        boolean result = dto.getAuthorDto() != null && dto.getTagDtos() != null;
        Assert.assertTrue(result);
    }
}
