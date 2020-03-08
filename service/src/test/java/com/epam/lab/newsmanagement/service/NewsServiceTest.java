package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AuthorMapper;
import com.epam.lab.newsmanagement.mapper.NewsMapper;
import com.epam.lab.newsmanagement.mapper.TagMapper;
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

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

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
    private NewsMapper newsMapper;
    @Mock
    private AuthorMapper authorMapper;
    @Mock
    private TagMapper tagMapper;

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
    private static SearchCriteriaDto searchCriteriaDto;
    private static SearchCriteria searchCriteria;
    private static List<News> newsWithIdList;
    private static List<NewsDto> newsDtoWithIdList;

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

        tag = new Tag(nameTag);

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
        newsDto.setTagDtoList(tagDtos);

        newsDtoWithId = new NewsDto();
        newsDtoWithId.setId(idNews);
        newsDtoWithId.setAuthorDto(authorDtoWithId);
        newsDtoWithId.setTagDtoList(tagDtosWithId);

        news = new News();
        news.setAuthor(author);
        news.setTags(tagsWithId);

        searchCriteriaDto = new SearchCriteriaDto();
        searchCriteriaDto.setAuthorDto(authorDto);
        searchCriteriaDto.setTagDtoList(tagDtos);

        searchCriteria = new SearchCriteria();
        searchCriteria.setAuthor(author);
        searchCriteria.setTags(tags);

        newsWithIdList = new ArrayList<>();
        newsWithIdList.add(newsWithId);

        newsDtoWithIdList = new ArrayList<>();
        newsDtoWithIdList.add(newsDtoWithId);
    }

    @Test
    public void createRightNewsTest() {
        try {
            Mockito.when(authorService.create(newsDto.getAuthorDto())).thenReturn(authorDtoWithId);
            Mockito.when(tagService.create(tagDtos)).thenReturn(tagDtosWithId);
            Mockito.when(newsMapper.toEntity(newsDto)).thenReturn(news);
            Mockito.doNothing().when(newsValidator).validate(news);
            Mockito.when(dao.create(news)).thenReturn(newsWithId);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            NewsDto result = newsService.create(newsDto);
            Assert.assertEquals(newsDtoWithId, result);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("createRightNewsTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void createWrongNewsTest() throws ServiceException, IncorrectDataException {
        Mockito.when(authorService.create(newsDto.getAuthorDto())).thenReturn(authorDtoWithId);
        Mockito.when(tagService.create(tagDtos)).thenReturn(tagDtosWithId);
        Mockito.when(newsMapper.toEntity(newsDto)).thenReturn(news);
        Mockito.doThrow(IncorrectDataException.class).when(newsValidator).validate(news);
        NewsDto result = newsService.create(newsDto);
        Assert.fail("createWrongNewsTest test was failed.");
    }

    @Test
    public void readExistingNewsTest() {
        try {
            long id = 1;
            Mockito.when(dao.read(id)).thenReturn(newsWithId);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            NewsDto result = newsService.read(id);
            Assert.assertEquals(newsDtoWithId, result);
        } catch (DaoException | ServiceException e) {
            Assert.fail("readExistingNewsTest test was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void readNullNewsTest() throws ServiceException, DaoException {
        long id = 2;
        Mockito.when(dao.read(id)).thenThrow(DaoException.class);
        NewsDto newsDto = newsService.read(id);
        Assert.fail("readNullNewsTest was failed.");
    }

    @Test
    public void readNewsBySearchCriteria() {
        try {
            Mockito.when(authorMapper.toEntity(authorDto)).thenReturn(author);
            Mockito.when(tagMapper.toEntity(tagDto)).thenReturn(tag);
            Mockito.doNothing().when(searchCriteriaValidator).validate(searchCriteria);
            Mockito.when(dao.read(searchCriteria)).thenReturn(newsWithIdList);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            List<NewsDto> result = newsService.read(searchCriteriaDto);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("readNewsBySearchCriteriaTest was failed.");
        }
    }

    @Test
    public void readNewsBySortCriteriaTest() {
        try {
            Mockito.when(dao.read(SortCriteria.AUTHOR)).thenReturn(newsWithIdList);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            List<NewsDto> result = newsService.read(SortCriteria.AUTHOR);
            boolean isEmpty = result.size() == 0;
            Assert.assertFalse(isEmpty);
        } catch (ServiceException | DaoException e) {
            Assert.fail("readNewsBySortCriteriaTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void readNewsByNullSortCriteriaTest() throws ServiceException {
        List<NewsDto> result = newsService.read((SortCriteria) null);
        Assert.fail("readNewsByNullSortCriteriaTest was failed.");
    }

    @Test(expected = ServiceException.class)
    public void readNewsByWrongCriteriaTest() throws ServiceException, DaoException {
        Mockito.when(dao.read(SortCriteria.AUTHOR)).thenThrow(DaoException.class);
        List<NewsDto> result = newsService.read(SortCriteria.AUTHOR);
        Assert.fail("readNewsByWrongCriteriaTest was failed.");
    }

    @Test
    public void updateNewsTest() {
        try {
            Mockito.when(authorService.create(newsDto.getAuthorDto())).thenReturn(authorDtoWithId);
            Mockito.when(tagService.create(tagDtos)).thenReturn(tagDtosWithId);
            Mockito.when(newsMapper.toEntity(newsDto)).thenReturn(news);
            Mockito.doNothing().when(newsValidator).validate(news);
            Mockito.when(dao.update(news)).thenReturn(newsWithId);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            NewsDto result = newsService.update(newsDto);
            Assert.assertEquals(newsDtoWithId, result);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("updateNewsTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void updateNullNewsTest() throws ServiceException, IncorrectDataException, DaoException {
        Mockito.when(authorService.create(newsDto.getAuthorDto())).thenReturn(authorDtoWithId);
        Mockito.when(tagService.create(tagDtos)).thenReturn(tagDtosWithId);
        Mockito.when(newsMapper.toEntity(newsDto)).thenReturn(news);
        Mockito.doNothing().when(newsValidator).validate(news);
        Mockito.when(dao.update(news)).thenThrow(DaoException.class);
        NewsDto result = newsService.update(newsDto);
        Assert.fail("updateNullNewsTest was failed.");
    }

    @Test
    public void deleteNewsTest() {
        long id = 1;
        try {
            Mockito.when(dao.delete(id)).thenReturn(newsWithId);
            Mockito.when(newsMapper.toDto(newsWithId)).thenReturn(newsDtoWithId);
            NewsDto result = newsService.delete(id);
            Assert.assertEquals(newsDtoWithId, result);
        } catch (ServiceException | DaoException e) {
            Assert.fail("deleteNewsTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteNullNewsTest() throws ServiceException, DaoException {
        long id = 1;
        Mockito.when(dao.delete(id)).thenThrow(DaoException.class);
        NewsDto result = newsService.delete(id);
        Assert.fail("deleteNullNewsTest was failed.");
    }
}