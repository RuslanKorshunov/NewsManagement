package com.epam.lab.newsmanagement.service;

import com.epam.lab.newsmanagement.dao.NewsDao;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Qualifier("newsService")
public class NewsService implements IntService<News> {
    private static final Logger logger;

    static {
        logger = LogManager.getLogger(NewsService.class);
    }

    @Autowired
    private AuthorService authorService;
    @Autowired
    private TagService tagService;
    @Autowired
    private NewsDao dao;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News create(News news) throws ServiceException {
        validate(news);
        try {
            news = news.clone();
            Author author = news.getAuthor();
            author = authorService.create(author);
            news.setAuthor(author);
            List<Tag> tags = news.getTags();
            tags = tagService.create(tags);
            news.setTags(tags);
            news = dao.create(news);
        } catch (DaoException | CloneNotSupportedException e) {
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News update(News news) throws ServiceException {
        validate(news);
        try {
            news = news.clone();
            Author author = news.getAuthor();
            author = authorService.create(author);
            news.setAuthor(author);
            List<Tag> tags = news.getTags();
            tags = tagService.create(tags);
            news.setTags(tags);
            news = dao.update(news);
        } catch (DaoException | CloneNotSupportedException e) {
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

    @Override
    public void validate(News news) throws ServiceException {
        if (news == null) {
            throw new ServiceException("parameter \"news\" can't be null.");
        }
        String title = news.getTitle();
        if (title == null) {
            throw new ServiceException("News's title can't be null.");
        }
        if (title.length() > 30) {
            title = title.substring(0, 29);
            news.setTitle(title);
            logger.warn("News's title was cut because it length is more than 30 symbols.");
        }
        String shortText = news.getShortText();
        if (shortText == null) {
            throw new ServiceException("News's shortText can't be null.");
        }
        if (shortText.length() > 100) {
            shortText = shortText.substring(0, 99);
            news.setShortText(shortText);
            logger.warn("News's shortText was cut because it length is more than 100 symbols.");
        }
        String fullText = news.getFullText();
        if (fullText == null) {
            throw new ServiceException("News's fullText can't be null.");
        }
        if (fullText.length() > 2000) {
            fullText = fullText.substring(0, 1999);
            news.setFullText(fullText);
            logger.warn("News's fullText was cut because it length is more than 2000 symbols.");
        }
    }
}
