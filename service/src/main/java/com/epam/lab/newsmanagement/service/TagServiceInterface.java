package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.ServiceException;

import java.util.List;

public interface TagServiceInterface extends ServiceInterface<Tag, TagDto> {
    @Override
    TagDto create(TagDto tagDto) throws ServiceException;

    List<TagDto> create(List<TagDto> tagDtoLists) throws ServiceException;

    @Override
    TagDto read(long id) throws ServiceException;

    @Override
    TagDto update(TagDto tagDto) throws ServiceException;

    @Override
    TagDto delete(long id) throws ServiceException;
}
