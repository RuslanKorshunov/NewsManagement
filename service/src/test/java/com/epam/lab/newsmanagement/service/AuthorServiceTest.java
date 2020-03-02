package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AuthorMapper;
import com.epam.lab.newsmanagement.validator.AuthorValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthorServiceTest {
    @Mock
    private AuthorDao dao;

    @Mock
    private AuthorValidator validator;

    @Mock
    private AuthorMapper mapper;

    @InjectMocks
    private AuthorService service;

    private AuthorDto rightAuthorDtoWithoutId;
    private AuthorDto rightAuthorDtoResult;
    private AuthorDto wrongAuthorDto;
    private AuthorDto nullAuthorDto;

    @Before
    public void initialize() {
        long id = 31;
        long idNull = 100;
        String name = "Ruslan";
        String surname = "Korshunov";

        rightAuthorDtoWithoutId = new AuthorDto();
        rightAuthorDtoWithoutId.setName(name);
        rightAuthorDtoWithoutId.setSurname(surname);

        rightAuthorDtoResult = new AuthorDto();
        rightAuthorDtoResult.setId(id);
        rightAuthorDtoResult.setName(name);
        rightAuthorDtoResult.setSurname(surname);

        wrongAuthorDto = new AuthorDto();
        wrongAuthorDto.setId(id);
        wrongAuthorDto.setName(name);

        nullAuthorDto = new AuthorDto();
        nullAuthorDto.setId(idNull);
        nullAuthorDto.setName(name);
        nullAuthorDto.setSurname(surname);
    }

    @Test
    public void createRightAuthorTest() {
        try {
            Mockito.when(service.create(rightAuthorDtoWithoutId)).thenReturn(rightAuthorDtoResult);
            AuthorDto authorDto = service.create(rightAuthorDtoWithoutId);
            Assert.assertEquals(rightAuthorDtoResult, authorDto);
        } catch (ServiceException e) {
            Assert.fail("createRightAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void createWrongAuthorTest() throws ServiceException {
        Mockito.when(service.create(wrongAuthorDto)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.create(rightAuthorDtoWithoutId);
        Assert.fail("addWrongAuthor was failed.");
    }

    @Test
    public void readExistingAuthorTest() {
        try {
            long id = 31;
            Mockito.when(service.read(id)).thenReturn(rightAuthorDtoResult);
            AuthorDto authorDto = service.read(id);
            Assert.assertEquals(rightAuthorDtoResult, authorDto);
        } catch (ServiceException e) {
            Assert.fail("readExistingAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void readNullAuthorTest() throws ServiceException {
        long id = 100;
        Mockito.when(service.read(id)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.read(id);
        Assert.fail("readNullAuthorTest was failed.");
    }

    @Test
    public void updateExistingAuthorTest() {
        try {
            Mockito.when(service.update(rightAuthorDtoResult)).thenReturn(rightAuthorDtoResult);
            AuthorDto authorDto = service.update(rightAuthorDtoResult);
            Assert.assertEquals(rightAuthorDtoResult, authorDto);
        } catch (ServiceException e) {
            Assert.fail("updateExistingAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void updateWrongAuthorTest() throws ServiceException {
        Mockito.when(service.update(wrongAuthorDto)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.update(rightAuthorDtoResult);
        Assert.fail("updateWrongAuthorTest was failed.");
    }

    @Test(expected = ServiceException.class)
    public void updateNullAuthorTest() throws ServiceException {
        Mockito.when(service.update(nullAuthorDto)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.update(rightAuthorDtoResult);
        Assert.fail("updateNullAuthorTest was failed.");
    }

    @Test
    public void deleteExistingAuthor() {
        try {
            long id = 31;
            Mockito.when(service.delete(id)).thenReturn(rightAuthorDtoResult);
            AuthorDto authorDto = service.delete(id);
            Assert.assertEquals(rightAuthorDtoResult, authorDto);
        } catch (ServiceException e) {
            Assert.fail("deleteExistingAuthor was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteNullAuthor() throws ServiceException {
        long id = 100;
        Mockito.when(service.delete(id)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.delete(id);
        Assert.fail("deleteNullAuthor was failed.");
    }
}