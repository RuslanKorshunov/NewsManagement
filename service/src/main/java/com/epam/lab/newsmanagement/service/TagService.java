package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.Dao;
import com.epam.lab.newsmanagement.dao.TagDao;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AbstractMapper;
import com.epam.lab.newsmanagement.mapper.TagMapper;
import com.epam.lab.newsmanagement.validator.TagValidator;
import com.epam.lab.newsmanagement.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("tagService")
public class TagService extends AbstractService<Tag, TagDto> {
    @Autowired
    private TagDao dao;
    @Autowired
    private TagValidator validator;
    @Autowired
    private TagMapper mapper;

    @Override
    public TagDto create(TagDto tagDto) throws ServiceException {
        Tag tag = mapper.toEntity(tagDto);
        try {
            validator.validate(tag);
            String name = tag.getName();
            name = name.toLowerCase();
            tag.setName(name);
            tag = dao.create(tag);
            tagDto = mapper.toDto(tag);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tagDto;
    }

    @Override
    public List<TagDto> create(List<TagDto> tagDtos) throws ServiceException {
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtos) {
            Tag tag = mapper.toEntity(tagDto);
            tags.add(tag);
        }
        if (tags.isEmpty()) {
            throw new ServiceException("parameter \"tags\" can't be null or empty.");
        }
        List<TagDto> result = new ArrayList<>();
        try {
            for (Tag tag : tags) {
                validator.validate(tag);
                toLoverCase(tag);
            }
            tags = dao.create(tags);
            for (Tag tag : tags) {
                TagDto tagDto = mapper.toDto(tag);
                result.add(tagDto);
            }
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public TagDto read(long id) throws ServiceException {
        return super.read(id);
    }

    @Override
    public TagDto update(TagDto tagDto) throws ServiceException {
        Tag tag = mapper.toEntity(tagDto);
        try {
            validator.validate(tag);
            toLoverCase(tag);
            getDao().update(tag);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tagDto;
    }

    @Override
    public TagDto delete(long id) throws ServiceException {
        return super.delete(id);
    }

    @Override
    Dao<Tag> getDao() {
        return dao;
    }

    @Override
    Validator<Tag> getValidator() {
        return validator;
    }

    @Override
    AbstractMapper<Tag, TagDto> getMapper() {
        return mapper;
    }

    private void toLoverCase(Tag tag) {
        String name = tag.getName();
        name = name.toLowerCase();
        tag.setName(name);
    }
}
