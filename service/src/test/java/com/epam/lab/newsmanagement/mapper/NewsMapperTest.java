package com.epam.lab.newsmanagement.mapper;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NewsMapperTest {
    @Mock
    private ModelMapper mapper;
    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private NewsMapper newsMapper;

    private static News news;
    private static NewsDto newsDto;
    private static Author author;
    private static AuthorDto authorDto;
    private static Tag tag;
    private static TagDto tagDto;

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
        author = new Author(idAuthor, nameAuthor, surnameAuthor);
        authorDto = new AuthorDto(idAuthor, nameAuthor, surnameAuthor);

        long idTag = 1;
        String nameTag = "japan";
        tag = new Tag(idTag, nameTag);
        tagDto = new TagDto(idTag, nameTag);

        List<Tag> tags = Arrays.asList(tag);
        List<TagDto> tagDtos = Arrays.asList(tagDto);

        news = new News(id, title, shortText, fullText, author, tags, creationDate, creationDate);

        newsDto = new NewsDto(id, title, shortText, fullText, authorDto, tagDtos, creationDate, creationDate);
    }

    @Test
    public void toNewsTest() {
        Mockito.when(mapper.map(newsDto, News.class)).thenReturn(news);
        Mockito.when(authorMapper.toEntity(authorDto)).thenReturn(author);
        Mockito.when(tagMapper.toEntity(tagDto)).thenReturn(tag);
        News news = newsMapper.toEntity(newsDto);
        boolean result = news.getAuthor() != null && news.getTags() != null;
        Assert.assertTrue(result);
    }

    @Test
    public void toDtoTest() {
        Mockito.when(mapper.map(news, NewsDto.class)).thenReturn(newsDto);
        Mockito.when(authorMapper.toDto(author)).thenReturn(authorDto);
        Mockito.when(tagMapper.toDto(tag)).thenReturn(tagDto);
        NewsDto dto = newsMapper.toDto(news);
        boolean result = dto.getAuthorDto() != null && dto.getTagDtoList() != null;
        Assert.assertTrue(result);
    }
}
