package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDaoInterface;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.SearchCriteriaDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.AbstractMapper;
import com.epam.lab.newsmanagement.mapper.AuthorMapper;
import com.epam.lab.newsmanagement.mapper.NewsMapper;
import com.epam.lab.newsmanagement.mapper.TagMapper;
import com.epam.lab.newsmanagement.validator.NewsValidator;
import com.epam.lab.newsmanagement.validator.SearchCriteriaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@Service("newsService")
public class NewsService implements NewsServiceInterface {
    private AuthorServiceInterface authorService;
    private TagServiceInterface tagService;
    private NewsDaoInterface dao;
    private NewsValidator newsValidator;
    private SearchCriteriaValidator searchCriteriaValidator;
    private NewsMapper newsMapper;
    private AuthorMapper authorMapper;
    private TagMapper tagMapper;

    @Autowired
    public NewsService(AuthorServiceInterface authorService,
                       TagServiceInterface tagService,
                       NewsDaoInterface dao,
                       NewsValidator newsValidator,
                       SearchCriteriaValidator searchCriteriaValidator,
                       NewsMapper newsMapper,
                       AuthorMapper authorMapper,
                       TagMapper tagMapper) {
        this.authorService = authorService;
        this.tagService = tagService;
        this.dao = dao;
        this.newsValidator = newsValidator;
        this.searchCriteriaValidator = searchCriteriaValidator;
        this.newsMapper = newsMapper;
        this.authorMapper = authorMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NewsDto create(NewsDto newsDto) throws ServiceException {
        try {
            News news = checkNews(newsDto);
            news = dao.create(news);
            newsDto = newsMapper.toDto(news);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    @Override
    public NewsDto read(long id) throws ServiceException {
        NewsDto newsDto;
        try {
            News news = dao.read(id);
            newsDto = newsMapper.toDto(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    @Override
    public List<NewsDto> read(SearchCriteriaDto scd) throws ServiceException {
        List<NewsDto> newsDtos;
        try {
            AbstractMapper<SearchCriteria, SearchCriteriaDto> mapper = getSearchCriteriaMapper();
            SearchCriteria sc = mapper.toEntity(scd);
            searchCriteriaValidator.validate(sc);
            List<News> newsList = dao.read(sc);
            newsDtos = toNewsDtoList(newsList);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return newsDtos;
    }

    @Override
    public List<NewsDto> read(SortCriteria sc) throws ServiceException {
        if (sc == null) {
            throw new ServiceException("SortCriteria can't be null.");
        }
        List<NewsDto> newsDtos;
        try {
            List<News> news = dao.read(sc);
            newsDtos = toNewsDtoList(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDtos;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NewsDto update(NewsDto newsDto) throws ServiceException {
        try {
            News news = checkNews(newsDto);
            news = dao.update(news);
            newsDto = newsMapper.toDto(news);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    @Override
    public NewsDto delete(long id) throws ServiceException {
        NewsDto newsDto;
        try {
            News news = dao.delete(id);
            newsDto = newsMapper.toDto(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    private List<NewsDto> toNewsDtoList(List<News> newsList) {
        List<NewsDto> newsDtos = new ArrayList<>();
        newsList.forEach(n -> {
            NewsDto newsDto = newsMapper.toDto(n);
            newsDtos.add(newsDto);
        });
        return newsDtos;
    }

    private AbstractMapper<SearchCriteria, SearchCriteriaDto> getSearchCriteriaMapper() {
        return new AbstractMapper<SearchCriteria, SearchCriteriaDto>() {
            @Override
            public SearchCriteria toEntity(SearchCriteriaDto dto) {
                SearchCriteria searchCriteria = new SearchCriteria();
                if (dto != null) {
                    AuthorDto authorDto = dto.getAuthorDto();
                    Author author = authorMapper.toEntity(authorDto);
                    if (author != null) {
                        searchCriteria.setAuthor(author);
                    }
                    List<TagDto> tagDtoList = dto.getTagDtoList();
                    List<Tag> tags = new ArrayList<>();
                    if (tagDtoList != null) {
                        tagDtoList.forEach(tagDto -> {
                            Tag tag = tagMapper.toEntity(tagDto);
                            if (tag != null) {
                                tags.add(tag);
                            }
                        });
                    }
                    searchCriteria.setTags(tags);
                }
                return searchCriteria;
            }

            @Override
            public SearchCriteriaDto toDto(SearchCriteria searchCriteria) {
                return super.toDto(searchCriteria);
            }

            @Override
            protected SearchCriteria getEntity(SearchCriteriaDto searchCriteriaDto) {
                return null;
            }

            @Override
            protected SearchCriteriaDto getDto(SearchCriteria searchCriteria) {
                return null;
            }
        };
    }

    private News checkNews(NewsDto newsDto) throws ServiceException, IncorrectDataException {
        AuthorDto authorDto = authorService.create(newsDto.getAuthorDto());
        List<TagDto> tagDtos = tagService.create(newsDto.getTagDtoList());
        newsDto.setAuthorDto(authorDto);
        newsDto.setTagDtoList(tagDtos);
        News news = newsMapper.toEntity(newsDto);
        newsValidator.validate(news);
        return news;
    }
}
