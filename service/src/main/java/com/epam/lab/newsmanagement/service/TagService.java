package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.TagDao;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.TagMapper;
import com.epam.lab.newsmanagement.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("tagService")
public class TagService extends AbstractService<Tag, TagDto> {
    @Autowired
    public TagService(TagDao dao, TagValidator validator, TagMapper mapper) {
        super(dao, validator, mapper);
    }

    @Override
    public TagDto create(TagDto tagDto) throws ServiceException {
        Tag tag = getMapper().toEntity(tagDto);
        try {
            getValidator().validate(tag);
            String name = tag.getName();
            name = name.toLowerCase();
            tag.setName(name);
            tag = getDao().create(tag);
            tagDto = getMapper().toDto(tag);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return tagDto;
    }

    @Override
    public List<TagDto> create(List<TagDto> tagDtoList) throws ServiceException {
        List<Tag> tags = new ArrayList<>();
        for (TagDto tagDto : tagDtoList) {
            Tag tag = getMapper().toEntity(tagDto);
            tags.add(tag);
        }
        if (tags.isEmpty()) {
            throw new ServiceException("parameter \"tags\" can't be null or empty.");
        }
        List<TagDto> result = new ArrayList<>();
        try {
            for (Tag tag : tags) {
                getValidator().validate(tag);
                toLoverCase(tag);
            }
            tags = getDao().create(tags);
            for (Tag tag : tags) {
                TagDto tagDto = getMapper().toDto(tag);
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
        Tag tag = getMapper().toEntity(tagDto);
        try {
            getValidator().validate(tag);
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

    private void toLoverCase(Tag tag) {
        String name = tag.getName();
        name = name.toLowerCase();
        tag.setName(name);
    }
}
