package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.config.DatabaseInitializer;
import com.epam.lab.newsmanagement.config.DaoTestConfig;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.epam.lab.newsmanagement.dao.NewsDao.SortCriteria;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DaoTestConfig.class})
public class NewsDaoTest {
    @Autowired
    private NewsDao dao;
    @Autowired
    private Logger logger;
    @Autowired
    private DatabaseInitializer dbi;

    private News news;
    private News existingNews;

    @Before
    public void initializeAuthor() {
        String title = "About me";
        String shortText = "Here I'd like to tell you about me.";
        String fullText = "\"My Name Is\" is a song by American rapper Eminem from his second studio album " +
                "The Slim Shady LP (1999). It is also the opening song of that album. " +
                "The song samples British singer Labi Siffre's 1975 track \"I Got The...\". " +
                "The song was ranked at #26 on \"VH1's 100 Greatest Songs of the '90s\". \"My Name Is\" was also ranked #6 on Q Magazine's \"1001 Best Songs Ever\". " +
                "The song was placed at number 39 by Rolling Stone on their list of \"100 Greatest Hip-Hop songs of all time\" in April 2016. " +
                "The recording garnered Eminem his first Grammy Award for Best Rap Solo Performance at the 42nd Grammy Awards in 2000.";
        Author author = new Author(5, "Maryia", "Biaseda");
        List<Tag> tags = new ArrayList<>();
        Tag tagOne = new Tag(9, "music");
        Tag tagTwo = new Tag(25, "usa");
        tags.add(tagOne);
        tags.add(tagTwo);
        news = new News();
        news.setTitle(title);
        news.setShortText(shortText);
        news.setFullText(fullText);
        news.setAuthor(author);
        news.setTags(tags);
        String titleExistingNews = "Passengers leave Diamond";
        String shortTextExistingNews = "Passengers have begun leaving a quarantined cruise ship.";
        String fullTextExistingNews = "nothing";
        Author authorExistingNews = new Author(46, "Bagdan", "Karchahin");
        existingNews = new News();
        existingNews.setTitle(titleExistingNews);
        existingNews.setShortText(shortTextExistingNews);
        existingNews.setFullText(fullTextExistingNews);
        existingNews.setAuthor(authorExistingNews);
        existingNews.setTags(tags);
        dbi.createDatabase();
    }

    @Test
    public void createNewsTest() {
        try {
            dao.create(news);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createNewsTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void createSomeNewsTest() throws DaoException {
        dao.create(new ArrayList<>());
        Assert.fail("createSomeNewsTest was failed.");
    }

    @Test
    public void readNewsTest() {
        try {
            dao.read(21);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void readNullNewsTest() throws DaoException {
        dao.read(100);
        Assert.fail("readNullNewsTest was failed.");
    }

    @Test
    public void readNewsBySearchCriteriaFirstTest() {
        try {
            SearchCriteria sc = new SearchCriteria();
            List<News> news = dao.read(sc);
            boolean result = news.size() > 0;
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsBySearchCriteriaFirstTest was failed.");
        }
    }

    @Test
    public void readNewsBySearchCriteriaSecondTest() {
        try {
            Author author = new Author(46, "Bagdan", "Karchahin");
            SearchCriteria sc = new SearchCriteria();
            sc.setAuthor(author);
            List<News> news = dao.read(sc);
            boolean result = news.size() > 0;
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsBySearchCriteriaSecondTest was failed.");
        }
    }

    @Test
    public void readNewsBySearchCriteriaThreeTest() {
        try {
            Tag tagOne = new Tag(44, "japan");
            Tag tagTwo = new Tag(47, "yokohama");
            SearchCriteria sc = new SearchCriteria();
            List<Tag> tags = new ArrayList<>();
            tags.add(tagOne);
            tags.add(tagTwo);
            sc.setTags(tags);
            List<News> news = dao.read(sc);
            boolean result = news.size() > 0;
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsBySearchCriteriaThirdTest was failed.");
        }
    }

    @Test
    public void readNewsBySortCriteriaFirstTest() {
        try {
            List<News> news = dao.read(SortCriteria.AUTHOR);
            boolean result = news.size() > 0;
            Assert.assertTrue(true);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsBySortCriteriaFirstTest was failed.");
        }
    }

    @Test
    public void readNewsBySortCriteriaSecondTest() {
        try {
            List<News> news = dao.read(SortCriteria.DATE);
            boolean result = news.size() > 0;
            Assert.assertTrue(true);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readNewsBySortCriteriaSecondTest was failed.");
        }
    }

    @Test
    public void updateNewsTest() {
        try {
            News news = dao.read(20);
            news.setTitle("Hello");
            dao.update(news);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("updateNewsTest was failed.");
        }
    }

    @Test
    public void deleteNewsTest() {
        try {
            News news = dao.delete(21);
            long id = news.getId();
            boolean result = id != 0;
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("deleteNewsTest was failed.");
        }
    }
}