package com.epam.lab.newsmanagement.controller;

import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.service.TagService;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RunWith(MockitoJUnitRunner.class)
public class TagControllerTest {
    @Mock
    private TagService service;

    @InjectMocks
    private TagController controller;

    private static TagDto tagDtoWithoutId;
    private static TagDto tagDtoWithId;

    @BeforeClass
    public static void initialize() {
        String name = "europe";
        long id = 1;

        tagDtoWithoutId = new TagDto(name);

        tagDtoWithId = new TagDto(id, name);
    }

    @Test
    public void createTagTest() {
        try {


            Mockito.when(service.create(tagDtoWithoutId)).thenReturn(tagDtoWithId);

            ResponseEntity<TagDto> responseEntity = controller.create(tagDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.CREATED, status);
        } catch (ServiceException e) {
            Assert.fail("createTagTest was failed.");
        }
    }

    @Test
    public void createTagWrongTest() {
        try {
            Mockito.when(service.create(tagDtoWithoutId)).thenThrow(ServiceException.class);

            ResponseEntity<TagDto> responseEntity = controller.create(tagDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, status);
        } catch (ServiceException e) {
            Assert.fail("createTagWrongTest was failed.");
        }
    }

    @Test
    public void readTagTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(tagDtoWithId);

            ResponseEntity<TagDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("readTagTest was failed.");
        }
    }

    @Test
    public void readTagWrongTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenThrow(ServiceException.class);

            ResponseEntity<TagDto> responseEntity = controller.read(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("readTagWrongTest was failed.");
        }
    }

    @Test
    public void updateTagTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(tagDtoWithId);
            Mockito.when(service.update(tagDtoWithId)).thenReturn(tagDtoWithId);

            ResponseEntity<TagDto> responseEntity = controller.update(id, tagDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("updateTagTest was failed.");
        }
    }

    @Test
    public void updateNullTagTest() {
        long id = 1;
        try {
            Mockito.when(service.read(id)).thenReturn(null);
            Mockito.when(service.update(tagDtoWithId)).thenThrow(ServiceException.class);

            ResponseEntity<TagDto> responseEntity = controller.update(id, tagDtoWithoutId);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("updateNullTagTest was failed.");
        }
    }

    @Test
    public void deleteTagTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenReturn(tagDtoWithId);

            ResponseEntity<TagDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.OK, status);
        } catch (ServiceException e) {
            Assert.fail("updateNullTagTest was failed.");
        }
    }

    @Test
    public void deleteNullTagTest() {
        long id = 1;
        try {
            Mockito.when(service.delete(id)).thenThrow(ServiceException.class);

            ResponseEntity<TagDto> responseEntity = controller.delete(id);
            HttpStatus status = responseEntity.getStatusCode();
            Assert.assertEquals(HttpStatus.NOT_FOUND, status);
        } catch (ServiceException e) {
            Assert.fail("deleteNullTagTest was failed.");
        }
    }
}