package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.AuthorService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {
    @Mock
    private AuthorService service;

    @InjectMocks
    private AuthorController controller;

    private static AuthorDto authorDtoWithoutId;
    private static AuthorDto authorDtoWithId;

    @BeforeClass
    public static void initialize() {
        long id = 1;
        String name = "Ruslan";
        String surname = "Korshunov";

        authorDtoWithoutId = new AuthorDto(name, surname);

        authorDtoWithId = new AuthorDto(id, name, surname);
    }

    @Test
    public void createAuthorTest() {
        try {
            Mockito.when(service.create(authorDtoWithoutId)).thenReturn(authorDtoWithId);

            ResponseEntity<AuthorDto> responseEntity = controller.create(authorDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.CREATED, status);
        } catch (ServiceException e) {
            Assert.fail("createAuthorTest was failed.");
        }
    }

    @Test
    public void createAuthorWrongTest() {
        try {
            Mockito.when(service.create(authorDtoWithoutId)).thenThrow(ServiceException.class);

            ResponseEntity<AuthorDto> responseEntity = controller.create(authorDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
        } catch (ServiceException e) {
            Assert.fail("createAuthorWrongTest was failed.");
        }
    }

    @Test
    public void readAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(authorDtoWithId);

            ResponseEntity<AuthorDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("readAuthorTest was failed.");
        }
    }

    @Test
    public void readAuthorWrongTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenThrow(ServiceException.class);

            ResponseEntity<AuthorDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("readAuthorWrongTest was failed.");
        }
    }

    @Test
    public void updateAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(authorDtoWithId);
            Mockito.when(service.update(authorDtoWithId)).thenReturn(authorDtoWithId);

            ResponseEntity<AuthorDto> responseEntity = controller.update(id, authorDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("updateAuthorTest was failed.");
        }
    }

    @Test
    public void updateNullAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(null);
            Mockito.when(service.update(authorDtoWithId)).thenThrow(ServiceException.class);

            ResponseEntity<AuthorDto> responseEntity = controller.update(id, authorDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("updateNullAuthorTest was failed.");
        }
    }

    @Test
    public void deleteAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenReturn(authorDtoWithId);

            ResponseEntity<AuthorDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("deleteAuthorTest was failed.");
        }
    }

    @Test
    public void deleteNullAuthorTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenThrow(ServiceException.class);

            ResponseEntity<AuthorDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("deleteNullAuthorTest was failed.");
        }
    }
}