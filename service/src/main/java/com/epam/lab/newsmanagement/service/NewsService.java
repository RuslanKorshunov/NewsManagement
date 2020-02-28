package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.dao.entity.SearchCriteria;
import com.epam.lab.newsmanagement.dto.AuthorDto;
import com.epam.lab.newsmanagement.dto.NewsDto;
import com.epam.lab.newsmanagement.dto.TagDto;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.mapper.NewsMapper;
import com.epam.lab.newsmanagement.validator.NewsValidator;
import com.epam.lab.newsmanagement.validator.SearchCriteriaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@Service
@Qualifier("newsService")
public class NewsService implements IntService<News, NewsDto> {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewsDao dao;
    @Autowired
    private NewsValidator newsValidator;
    @Autowired
    private SearchCriteriaValidator searchCriteriaValidator;
    @Autowired
    private NewsMapper mapper;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public NewsDto create(NewsDto newsDto) throws ServiceException {
        try {
            AuthorDto authorDto = authorService.create(newsDto.getAuthorDto());
            List<TagDto> tagDtos = tagService.create(newsDto.getTagDtos());
            newsDto.setAuthorDto(authorDto);
            newsDto.setTagDtos(tagDtos);
            News news = mapper.toEntity(newsDto);
            newsValidator.validate(news);
            news = dao.create(news);
            newsDto = mapper.toDto(news);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    @Override
    public List<NewsDto> create(List<NewsDto> newsDtos) throws ServiceException {
        throw new ServiceException("Operation isn't supported by newsService.");
    }

    @Override
    public NewsDto read(long id) throws ServiceException {
        NewsDto newsDto;
        try {
            News news = dao.read(id);
            newsDto = mapper.toDto(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    @Override
    public List<NewsDto> read(SearchCriteria sc) throws ServiceException {
        List<NewsDto> newsDtos;
        try {
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
            AuthorDto authorDto = authorService.create(newsDto.getAuthorDto());
            List<TagDto> tagDtos = tagService.create(newsDto.getTagDtos());
            newsDto.setAuthorDto(authorDto);
            newsDto.setTagDtos(tagDtos);
            News news = mapper.toEntity(newsDto);
            newsValidator.validate(news);
            news = dao.update(news);
            newsDto = mapper.toDto(news);
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
            newsDto = mapper.toDto(news);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return newsDto;
    }

    private List<NewsDto> toNewsDtoList(List<News> newsList) {
        List<NewsDto> newsDtos = new ArrayList<>();
        newsList.forEach(n -> {
            NewsDto newsDto = mapper.toDto(n);
            newsDtos.add(newsDto);
        });
        return newsDtos;
    }
}
