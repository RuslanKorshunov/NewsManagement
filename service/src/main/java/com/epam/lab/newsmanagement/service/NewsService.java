package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.IncorrectDataException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import com.epam.lab.newsmanagement.validator.NewsValidator;
import com.epam.lab.newsmanagement.validator.SearchCriteriaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@Service
@Qualifier("newsService")
public class NewsService implements IntService<News> {
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

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News create(News news) throws ServiceException {
        try {
            newsValidator.validate(news);
            news = news.clone();
            Author author = news.getAuthor();
            author = authorService.create(author);
            news.setAuthor(author);
            List<Tag> tags = news.getTags();
            tags = tagService.create(tags);
            news.setTags(tags);
            news = dao.create(news);
        } catch (DaoException | CloneNotSupportedException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> create(List<News> news) throws ServiceException {
        throw new ServiceException("Operation isn't supported by newsService.");
    }

    @Override
    public News read(long id) throws ServiceException {
        News news;
        try {
            news = dao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> read(SearchCriteria sc) throws ServiceException {
        List<News> news;
        try {
            searchCriteriaValidator.validate(sc);
            news = dao.read(sc);
        } catch (DaoException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public List<News> read(SortCriteria sc) throws ServiceException {
        if (sc == null) {
            throw new ServiceException("SortCriteria can't be null.");
        }
        List<News> news;
        try {
            news = dao.read(sc);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News update(News news) throws ServiceException {
        try {
            newsValidator.validate(news);
            news = news.clone();
            Author author = news.getAuthor();
            author = authorService.create(author);
            news.setAuthor(author);
            List<Tag> tags = news.getTags();
            tags = tagService.create(tags);
            news.setTags(tags);
            news = dao.update(news);
        } catch (DaoException | CloneNotSupportedException | IncorrectDataException e) {
            throw new ServiceException(e);
        }
        return news;
    }

    @Override
    public News delete(long id) throws ServiceException {
        News news;
        try {
            news = dao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return news;
    }
}
