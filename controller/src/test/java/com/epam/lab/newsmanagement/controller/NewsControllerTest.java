package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.NewsService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@RunWith(MockitoJUnitRunner.class)
public class NewsControllerTest {
    @Mock
    private NewsService service;

    @InjectMocks
    private NewsController controller;

    private static NewsDto newsDtoWithoutId;
    private static NewsDto newsDtoWithId;
    private static SearchCriteriaDto searchCriteriaDto;
    private static List<NewsDto> newsDtoList;

    @BeforeClass
    @Ignore
    public static void initialize() {
        long idAuthor = 1;
        long idTag = 1;
        String name = "Ruslan";
        String surname = "Korshunov";
        String nameTag = "belarus";

        AuthorDto authorDtoWithoutId = new AuthorDto(name, surname);
        AuthorDto authorDtoWithId = new AuthorDto(idAuthor, name, surname);

        TagDto tagDtoWithoutId = new TagDto(nameTag);
        List<TagDto> tagDtoListWithoutId = new ArrayList<>();
        tagDtoListWithoutId.add(tagDtoWithoutId);

        TagDto tagDtoWithId = new TagDto(idTag, nameTag);
        List<TagDto> tagDtoListWithId = new ArrayList<>();
        tagDtoListWithId.add(tagDtoWithId);

        newsDtoWithoutId = new NewsDto();
        newsDtoWithoutId.setAuthorDto(authorDtoWithoutId);
        newsDtoWithoutId.setTagDtoList(tagDtoListWithoutId);

        newsDtoWithId = new NewsDto();
        newsDtoWithId.setAuthorDto(authorDtoWithId);
        newsDtoWithId.setTagDtoList(tagDtoListWithId);

        searchCriteriaDto = new SearchCriteriaDto();
        searchCriteriaDto.setAuthorDto(authorDtoWithoutId);

        newsDtoList = new ArrayList<>();
        newsDtoList.add(newsDtoWithId);
    }

    @Test
    @Ignore
    public void createNewsTest() {
        try {
            Mockito.when(service.create(newsDtoWithoutId)).thenReturn(newsDtoWithId);

            ResponseEntity<NewsDto> responseEntity = controller.create(newsDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.CREATED, status);
        } catch (ServiceException e) {
            Assert.fail("createNewsTest was failed.");
        }
    }

    @Test
    @Ignore
    public void createNewsWrongTest() {
        try {
            Mockito.when(service.create(newsDtoWithoutId)).thenThrow(ServiceException.class);

            ResponseEntity<NewsDto> responseEntity = controller.create(newsDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
        } catch (ServiceException e) {
            Assert.fail("createNewsWrongTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(newsDtoWithId);

            ResponseEntity<NewsDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsWrongTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenThrow(ServiceException.class);

            ResponseEntity<NewsDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsWrongTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsBySearchCriteriaTest() {
        try {
            Mockito.when(service.read(searchCriteriaDto)).thenReturn(newsDtoList);

            ResponseEntity<List<NewsDto>> responseEntity = controller.read(searchCriteriaDto);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsBySearchCriteriaTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsBySearchCriteriaWrongTest() {
        try {
            Mockito.when(service.read(searchCriteriaDto)).thenThrow(ServiceException.class);

            ResponseEntity<List<NewsDto>> responseEntity = controller.read(searchCriteriaDto);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsBySearchCriteriaWrongTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsBySortCriteriaTest() {
        try {
            Mockito.when(service.read(SortCriteria.AUTHOR)).thenReturn(newsDtoList);

            ResponseEntity<List<NewsDto>> responseEntity = controller.read(SortCriteria.AUTHOR);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsBySortCriteriaTest was failed.");
        }
    }

    @Test
    @Ignore
    public void readNewsBySortCriteriaWrongTest() {
        try {
            Mockito.when(service.read(SortCriteria.AUTHOR)).thenThrow(ServiceException.class);

            ResponseEntity<List<NewsDto>> responseEntity = controller.read(SortCriteria.AUTHOR);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("readNewsBySortCriteriaWrongTest was failed.");
        }
    }

    @Test
    @Ignore
    public void updateNewsTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(newsDtoWithId);
            Mockito.when(service.update(newsDtoWithId)).thenReturn(newsDtoWithId);

            ResponseEntity<NewsDto> responseEntity = controller.update(id, newsDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("updateNewsTest was failed.");
        }
    }

    @Test
    @Ignore
    public void deleteNewsTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenReturn(newsDtoWithId);

            ResponseEntity<NewsDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("deleteNewsTest was failed.");
        }
    }

    @Test
    @Ignore
    public void deleteNullAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenThrow(ServiceException.class);

            ResponseEntity<NewsDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("deleteNullNewsTest was failed.");
        }
    }
}