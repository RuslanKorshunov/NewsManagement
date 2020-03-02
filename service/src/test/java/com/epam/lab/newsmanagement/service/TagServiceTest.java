package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.TagDao;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.TagMapper;
import com.epam.lab.newsmanagement.validator.TagValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTest {
    @Mock
    private TagDao dao;
    @Mock
    private TagValidator validator;
    @Mock
    private TagMapper mapper;

    @InjectMocks
    private TagService service;

    private TagDto tagDtoOneWithoutId;
    private TagDto tagDtoOneWithId;
    private TagDto tagDtoTwoWithoutId;
    private TagDto tagDtoTwoWithId;
    private TagDto wrongTagDto;
    private TagDto nullTagDto;
    private Tag tagOneWithoutId;
    private Tag tagOneWithId;
    private Tag tagTwoWithoutId;
    private Tag tagTwoWithId;
    private Tag wrongTag;
    private Tag nullTag;

    @Before
    public void initialize() {
        long idOne = 15;
        String nameOne = "belarus";
        long idTwo = 16;
        String nameTwo = "russia";
        long idNull = 17;

        tagDtoOneWithoutId = new TagDto();
        tagDtoOneWithoutId.setName(nameOne);

        tagDtoOneWithId = new TagDto();
        tagDtoOneWithId.setId(idOne);
        tagDtoOneWithId.setName(nameOne);

        tagDtoTwoWithoutId = new TagDto();
        tagDtoTwoWithoutId.setName(nameTwo);

        tagDtoTwoWithId = new TagDto();
        tagDtoTwoWithId.setId(idTwo);
        tagDtoTwoWithId.setName(nameTwo);

        wrongTagDto = new TagDto();

        nullTagDto = new TagDto();
        nullTagDto.setId(idNull);
        nullTagDto.setName(nameOne);

        tagOneWithoutId = new Tag(nameOne);

        tagOneWithId = new Tag(idOne, nameOne);

        tagTwoWithoutId = new Tag(nameTwo);

        tagTwoWithId = new Tag(idTwo, nameTwo);

        wrongTag = new Tag();

        nullTag = new Tag(idNull, nameOne);
    }

    @Test
    public void createRightTagTest() {
        try {
            Mockito.when(mapper.toEntity(tagDtoOneWithoutId)).thenReturn(tagOneWithoutId);
            Mockito.doNothing().when(validator).validate(tagOneWithoutId);
            Mockito.when(dao.create(tagOneWithoutId)).thenReturn(tagOneWithId);
            Mockito.when(mapper.toDto(tagOneWithId)).thenReturn(tagDtoOneWithId);
            TagDto tagDto = service.create(tagDtoOneWithoutId);
            Assert.assertEquals(tagDtoOneWithId, tagDto);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("createRightTagTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void createWrongTagTest() throws ServiceException, IncorrectDataException {
        Mockito.when(mapper.toEntity(wrongTagDto)).thenReturn(wrongTag);
        Mockito.doThrow(IncorrectDataException.class).when(validator).validate(wrongTag);
        TagDto tagDto = service.create(wrongTagDto);
        Assert.fail("createWrongTagTest was failed.");
    }

    @Test
    public void createTagsTest() {
        try {
            List<TagDto> tagDtosWithoutId = new ArrayList<>();
            tagDtosWithoutId.add(tagDtoOneWithoutId);
            tagDtosWithoutId.add(tagDtoTwoWithoutId);

            List<TagDto> tagDtosWithId = new ArrayList<>();
            tagDtosWithId.add(tagDtoOneWithId);
            tagDtosWithId.add(tagDtoTwoWithId);

            List<Tag> tagsWithoutId = new ArrayList<>();
            tagsWithoutId.add(tagOneWithoutId);
            tagsWithoutId.add(tagTwoWithoutId);

            List<Tag> tagsWithId = new ArrayList<>();
            tagsWithId.add(tagOneWithId);
            tagsWithId.add(tagTwoWithId);

            Mockito.when(mapper.toEntity(tagDtoOneWithoutId)).thenReturn(tagOneWithoutId);
            Mockito.when(mapper.toEntity(tagDtoTwoWithoutId)).thenReturn(tagTwoWithoutId);
            Mockito.doNothing().when(validator).validate(tagOneWithoutId);
            Mockito.doNothing().when(validator).validate(tagTwoWithoutId);
            Mockito.when(dao.create(tagsWithoutId)).thenReturn(tagsWithId);
            Mockito.when(mapper.toDto(tagOneWithId)).thenReturn(tagDtoOneWithId);
            Mockito.when(mapper.toDto(tagTwoWithId)).thenReturn(tagDtoTwoWithId);

            List<TagDto> actual = service.create(tagDtosWithoutId);
            Assert.assertEquals(tagDtosWithId, actual);
        } catch (IncorrectDataException | DaoException | ServiceException e) {
            Assert.fail("createTagsTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void createEmptyTagsTest() throws ServiceException {
        List<TagDto> tagDtos = new ArrayList<>();
        List<TagDto> result = service.create(tagDtos);
        Assert.fail("createEmptyTagsTest was failed.");
    }

    @Test(expected = ServiceException.class)
    public void createWrongTagsTest() throws ServiceException, IncorrectDataException {
        List<TagDto> tagDtos = new ArrayList<>();
        tagDtos.add(wrongTagDto);
        Mockito.when(mapper.toEntity(wrongTagDto)).thenReturn(wrongTag);
        Mockito.doThrow(IncorrectDataException.class).when(validator).validate(wrongTag);
        List<TagDto> result = service.create(tagDtos);
        Assert.fail("createWrongTagsTest was failed.");
    }

    @Test
    public void readExistingTagTest() {
        try {
            long id = 15;
            Mockito.when(dao.read(id)).thenReturn(tagOneWithId);
            Mockito.when(mapper.toDto(tagOneWithId)).thenReturn(tagDtoOneWithId);
            TagDto tagDto = service.read(id);
            Assert.assertEquals(tagDtoOneWithId, tagDto);
        } catch (ServiceException | DaoException e) {
            Assert.fail("readExistingTagTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void readNullTagTest() throws ServiceException, DaoException {
        long id = 15;
        Mockito.when(dao.read(id)).thenThrow(ServiceException.class);
        TagDto tagDto = service.read(id);
        Assert.fail("readNullTagTest was failed.");
    }

    @Test
    public void updateTagTest() {
        try {
            Mockito.when(mapper.toEntity(tagDtoOneWithId)).thenReturn(tagOneWithId);
            Mockito.doNothing().when(validator).validate(tagOneWithId);
            Mockito.when(dao.create(tagOneWithId)).thenReturn(tagOneWithId);
            TagDto tagDto = service.update(tagDtoOneWithId);
            Assert.assertEquals(tagDtoOneWithId, tagDtoOneWithId);
        } catch (ServiceException | IncorrectDataException | DaoException e) {
            Assert.fail("updateTagTest was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void updateWrongTagTest() throws ServiceException, IncorrectDataException {
        Mockito.when(mapper.toEntity(wrongTagDto)).thenReturn(wrongTag);
        Mockito.doThrow(IncorrectDataException.class).when(validator).validate(wrongTag);
        TagDto tagDto = service.update(wrongTagDto);
        Assert.fail("updateWrongTest was failed.");
    }

    @Test(expected = ServiceException.class)
    public void updateNullTagTest() throws ServiceException, IncorrectDataException, DaoException {
        Mockito.when(mapper.toEntity(nullTagDto)).thenReturn(nullTag);
        Mockito.doNothing().when(validator).validate(nullTag);
        Mockito.when(dao.update(nullTag)).thenThrow(DaoException.class);
        TagDto tagDto = service.update(nullTagDto);
        Assert.fail("updateNullTest was failed.");
    }

    @Test
    public void deleteTag() {
        try {
            long id = 15;
            Mockito.when(dao.delete(id)).thenReturn(tagOneWithId);
            Mockito.when(mapper.toDto(tagOneWithId)).thenReturn(tagDtoOneWithId);
            TagDto tagDto = service.delete(id);
            Assert.assertEquals(tagDtoOneWithId, tagDto);
        } catch (ServiceException | DaoException e) {
            Assert.fail("deleteTag was failed.");
        }
    }

    @Test(expected = ServiceException.class)
    public void deleteNullTag() throws ServiceException, DaoException {
        long id = 17;
        Mockito.when(dao.delete(id)).thenThrow(DaoException.class);
        TagDto tagDto = service.delete(id);
        Assert.fail("deleteTag was failed.");

    }
}