package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.config.ServiceTestConfig;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Tag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class TagMapperTest {
    @Autowired
    private TagMapper mapper;

    private TagDto dto;
    private Tag tag;

    @Before
    public void initialize() {
        long id = 31;
        String name = "ruslan";
        dto = new TagDto();
        dto.setId(id);
        dto.setName(name);
        tag = new Tag(id, name);
    }

    @Test
    public void toTagTest() {
        Tag tag = mapper.toEntity(dto);
        boolean result = tag.equals(this.tag);
        Assert.assertTrue(result);
    }

    @Test
    public void toDtoTest() {
        TagDto dto = mapper.toDto(this.tag);
        boolean result = dto.equals(this.dto);
        Assert.assertTrue(result);
    }
}