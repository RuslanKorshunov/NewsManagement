package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.NewsMapper;
import com.epam.lab.newsmanagement.validator.NewsValidator;
import com.epam.lab.newsmanagement.validator.SearchCriteriaValidator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class NewsServiceTest {
    @Mock
    private AuthorService authorService;
    @Mock
    private TagService tagService;
    @Mock
    private NewsDao dao;
    @Mock
    private NewsValidator newsValidator;
    @Mock
    private SearchCriteriaValidator searchCriteriaValidator;
    @Mock
    private NewsMapper mapper;

    @InjectMocks
    private NewsService newsService;

    private static AuthorDto authorDto;
    private static AuthorDto authorDtoWithId;
    private static Author author;
    private static Author authorWithId;
    private static TagDto tagDto;
    private static TagDto tagDtoWithId;
    private static Tag tag;
    private static Tag tagWithId;
    private static List<TagDto> tagDtos;
    private static List<TagDto> tagDtosWithId;
    private static List<Tag> tags;
    private static List<Tag> tagsWithId;
    private static NewsDto newsDto;
    private static NewsDto newsDtoWithId;
    private static News news;
    private static News newsWithId;

    @BeforeClass
    public static void initialize() {
        long idAuthor = 1;
        long idTag = 1;
        long idNews = 1;
        String name = "Ruslan";
        String surname = "Korshunov";
        String nameTag = "belarus";

        authorDto = new AuthorDto(name, surname);

        authorDtoWithId = new AuthorDto(idAuthor, name, surname);

        authorWithId = new Author(idAuthor, name, surname);

        tagDto = new TagDto(nameTag);

        tagDtoWithId = new TagDto(idTag, nameTag);

        tagWithId = new Tag(idTag, nameTag);

        tagDtos = new ArrayList<>();
        tagDtos.add(tagDto);

        tagDtosWithId = new ArrayList<>();
        tagDtosWithId.add(tagDtoWithId);

        tagsWithId = new ArrayList<>();
        tagsWithId.add(tagWithId);

        author = new Author(name, surname);

        newsDto = new NewsDto();
        newsDto.setAuthorDto(authorDto);
        newsDto.setTagDtos(tagDtos);

        newsDtoWithId = new NewsDto();
        newsDtoWithId.setId(idNews);
        newsDtoWithId.setAuthorDto(authorDtoWithId);
        newsDtoWithId.setTagDtos(tagDtosWithId);

        news = new News();
        news.setAuthor(author);
        news.setTags(tagsWithId);
    }

    @Test
    public void createRightNewsTest() {
        try {
            Mockito.when(authorService.create(newsDto.getAuthorDto())).thenReturn(authorDtoWithId);
            Mockito.when(tagService.create(tagDtos)).thenReturn(tagDtosWithId);
            Mockito.when(mapper.toEntity(newsDto)).thenReturn(news);
            Mockito.doNothing().when(newsValidator).validate(news);
            Mockito.when(dao.create(news)).thenReturn(newsWithId);
            Mockito.when(mapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            NewsDto result = newsService.create(newsDto);
            Assert.assertEquals(newsDtoWithId, result);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("createRightNewsTest was failed.");
        }
    }
}