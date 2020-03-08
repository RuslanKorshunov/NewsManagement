package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TagMapper extends AbstractMapper<Tag, TagDto> {
    @Autowired
    private ModelMapper mapper;

    @Override
    protected Tag getEntity(TagDto dto) {
        return mapper.map(dto, Tag.class);
    }

    @Override
    protected TagDto getDto(Tag tag) {
        return mapper.map(tag, TagDto.class);
    }
}
