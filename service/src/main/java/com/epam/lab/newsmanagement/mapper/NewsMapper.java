package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsMapper extends AbstractMapper<News, NewsDto> {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private TagMapper tagMapper;

    @Override
    public News toEntity(NewsDto newsDto) {
        News news = getEntity(newsDto);
        if (news != null) {
            AuthorDto authorDto = newsDto.getAuthorDto();
            Author author = authorMapper.toEntity(authorDto);
            if (author != null) {
                news.setAuthor(author);
            }
            List<TagDto> tagDtoList = newsDto.getTagDtoList();
            List<Tag> tags = new ArrayList<>();
            if (tagDtoList != null) {
                tagDtoList.forEach(tagDto -> {
                    Tag tag = tagMapper.toEntity(tagDto);
                    if (tag != null) {
                        tags.add(tag);
                    }
                });
            }
            news.setTags(tags);
        }
        return news;
    }

    @Override
    public NewsDto toDto(News news) {
        NewsDto newsDto = getDto(news);
/*        if (newsDto != null) {
            Author author = news.getAuthor();
            AuthorDto authorDto = authorMapper.toDto(author);
            if (authorDto != null) {
                newsDto.setAuthorDto(authorDto);
            }
            List<Tag> tags = news.getTags();
            List<TagDto> tagDtos = new ArrayList<>();
            if (tags != null) {
                tags.forEach(tag -> {
                    TagDto tagDto = tagMapper.toDto(tag);
                    if (tagDto != null) {
                        tagDtos.add(tagDto);
                    }
                });
            }
            newsDto.setTagDtoList(tagDtos);
        }*/
        return newsDto;
    }

    @Override
    protected News getEntity(NewsDto dto) {
        return mapper.map(dto, News.class);
    }

    @Override
    protected NewsDto getDto(News news) {
        return mapper.map(news, NewsDto.class);
    }
}
