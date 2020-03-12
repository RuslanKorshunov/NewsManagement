package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

@RunWith(MockitoJUnitRunner.class)
public class TagMapperTest {
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private TagMapper tagMapper;

    private static TagDto dto;
    private static Tag tag;

    @BeforeClass
    public static void initialize() {
        long id = 31;
        String name = "ruslan";
        dto = new TagDto();
        dto.setId(id);
        dto.setName(name);
        tag = new Tag(id, name);
    }

    @Test
    public void toTagTest() {
        Mockito.when(mapper.map(dto, Tag.class)).thenReturn(tag);
        Tag tag = tagMapper.toEntity(dto);
        boolean result = tag.equals(this.tag);
        Assert.assertTrue(result);
    }

    @Test
    public void toDtoTest() {
        Mockito.when(mapper.map(tag, TagDto.class)).thenReturn(dto);
        TagDto dto = tagMapper.toDto(this.tag);
        boolean result = dto.equals(this.dto);
        Assert.assertTrue(result);
    }
}