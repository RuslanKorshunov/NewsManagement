package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.AuthorDao;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
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
    private AuthorDto rightAuthorDtoWithId;
    private AuthorDto wrongAuthorDtoWithoutId;
    private AuthorDto wrongAuthorDto;
    private AuthorDto nullAuthorDto;
    private Author rightAuthorWithoutId;
    private Author rightAuthorWithId;
    private Author wrongAuthorWithoutId;
    private Author nullAuthor;

    @Before
    public void initialize() {
        long id = 31;
        long idNull = 100;
        String name = "Ruslan";
        String surname = "Korshunov";

        rightAuthorDtoWithoutId = new AuthorDto();
        rightAuthorDtoWithoutId.setName(name);
        rightAuthorDtoWithoutId.setSurname(surname);

        rightAuthorDtoWithId = new AuthorDto();
        rightAuthorDtoWithId.setId(id);
        rightAuthorDtoWithId.setName(name);
        rightAuthorDtoWithId.setSurname(surname);

        wrongAuthorDtoWithoutId = new AuthorDto();
        wrongAuthorDtoWithoutId.setName(name);

        wrongAuthorDto = new AuthorDto();
        wrongAuthorDto.setId(id);
        wrongAuthorDto.setName(name);

        nullAuthorDto = new AuthorDto();
        nullAuthorDto.setId(idNull);
        nullAuthorDto.setName(name);
        nullAuthorDto.setSurname(surname);

        rightAuthorWithoutId = new Author(name, surname);

        rightAuthorWithId = new Author(id, name, surname);

        wrongAuthorWithoutId = new Author();
        wrongAuthorWithoutId.setName(name);

        nullAuthor = new Author(idNull, name, surname);
    }

    @Test
    public void createRightAuthorTest() {
        try {
            Mockito.when(mapper.toEntity(rightAuthorDtoWithoutId)).thenReturn(rightAuthorWithoutId);
            Mockito.doNothing().when(validator).validate(rightAuthorWithId);
            Mockito.when(mapper.toDto(rightAuthorWithId)).thenReturn(rightAuthorDtoWithId);
            Mockito.when(dao.create(rightAuthorWithoutId)).thenReturn(rightAuthorWithId);
            AuthorDto authorDto = service.create(rightAuthorDtoWithoutId);
            Assert.assertEquals(rightAuthorDtoWithId, authorDto);
        } catch (ServiceException | DaoException | IncorrectDataException e) {
            Assert.fail("createRightAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void createWrongAuthorTest() throws ServiceException, IncorrectDataException {
        Mockito.when(mapper.toEntity(wrongAuthorDtoWithoutId)).thenReturn(wrongAuthorWithoutId);
        Mockito.doThrow(ServiceException.class).when(validator).validate(wrongAuthorWithoutId);
        AuthorDto authorDto = service.create(wrongAuthorDtoWithoutId);
        Assert.fail("createWrongAuthorTest was failed.");
    }

    @Test
    public void readExistingAuthorTest() {
        try {
            long id = 31;
            Mockito.when(dao.read(id)).thenReturn(rightAuthorWithId);
            Mockito.when(mapper.toDto(rightAuthorWithId)).thenReturn(rightAuthorDtoWithId);
            AuthorDto authorDto = service.read(id);
            Assert.assertEquals(rightAuthorDtoWithId, authorDto);
        } catch (ServiceException | DaoException e) {
            Assert.fail("readExistingAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void readNullAuthorTest() throws ServiceException, DaoException {
        long id = 100;
        Mockito.when(dao.read(id)).thenThrow(DaoException.class);
        AuthorDto authorDto = service.read(id);
        Assert.fail("readNullAuthorTest was failed.");
    }

    @Test
    public void updateExistingAuthorTest() {
        try {
            Mockito.when(mapper.toEntity(rightAuthorDtoWithId)).thenReturn(rightAuthorWithId);
            Mockito.doNothing().when(validator).validate(rightAuthorWithId);
            Mockito.when(dao.update(rightAuthorWithId)).thenReturn(rightAuthorWithId);
            AuthorDto authorDto = service.update(rightAuthorDtoWithId);
            Assert.assertEquals(rightAuthorDtoWithId, authorDto);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("updateExistingAuthorTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void updateWrongAuthorTest() throws ServiceException, IncorrectDataException {
        Mockito.when(mapper.toEntity(wrongAuthorDto)).thenReturn(wrongAuthorWithoutId);
        Mockito.doThrow(IncorrectDataException.class).when(validator).validate(wrongAuthorWithoutId);
        AuthorDto authorDto = service.update(wrongAuthorDto);
        Assert.fail("updateWrongAuthorTest was failed.");
    }

    @Test(expected = ServiceException.class)
    public void updateNullAuthorTest() throws ServiceException, IncorrectDataException, DaoException {
        Mockito.when(mapper.toEntity(nullAuthorDto)).thenReturn(nullAuthor);
        Mockito.doNothing().when(validator).validate(nullAuthor);
        Mockito.when(dao.update(nullAuthor)).thenThrow(DaoException.class);
        AuthorDto authorDto = service.update(nullAuthorDto);
        Assert.fail("updateNullAuthorTest was failed.");
    }

    @Test
    public void deleteExistingAuthor() {
        try {
            long id = 31;
            Mockito.when(dao.delete(id)).thenReturn(rightAuthorWithId);
            Mockito.when(mapper.toDto(rightAuthorWithId)).thenReturn(rightAuthorDtoWithId);
            AuthorDto authorDto = service.delete(id);
            Assert.assertEquals(rightAuthorDtoWithId, authorDto);
        } catch (ServiceException | DaoException e) {
            Assert.fail("deleteExistingAuthor was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteNullAuthor() throws ServiceException, DaoException {
        long id = 100;
        Mockito.when(dao.delete(id)).thenThrow(ServiceException.class);
        AuthorDto authorDto = service.delete(id);
        Assert.fail("deleteNullAuthor was failed.");
    }
}