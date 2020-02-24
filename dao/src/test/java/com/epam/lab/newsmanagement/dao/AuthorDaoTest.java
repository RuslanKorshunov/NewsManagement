package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.config.DatabaseInitializer;
import com.epam.lab.newsmanagement.config.TestConfig;
import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
public class AuthorDaoTest {
    @Autowired
    private AuthorDao dao;
    @Autowired
    private Logger logger;
    @Autowired
    private DatabaseInitializer dbi;

    private Author newAuthor;
    private Author existingAuthorOne;
    private Author existingAuthorTwo;
    private Author existingAuthorThree;

    @Before
    public void initializeAuthor() {
        newAuthor = new Author("Rigor", "Borodulin");
        existingAuthorOne = new Author(31, "Ruslan", "Korshunov");
        existingAuthorTwo = new Author(23, "Evgenij", "Burak");
        existingAuthorThree = new Author(35, "Mari", "Kurl");
        dbi.createDatabase();
    }

    @Test
    public void createExistingAuthorTest() {
        try {
            Author author = dao.create(existingAuthorOne);
            long id = author.getId();
            boolean result = id == existingAuthorOne.getId();
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createExistingAuthorTest was failed.");
        }
    }

    @Test
    public void createNewAuthorTest() {
        try {
            Author author = dao.create(newAuthor);
            long id = author.getId();
            boolean result = id != 0;
            Assert.assertTrue(id != 0);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createExistingAuthorTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void createAuthors() throws DaoException {
        dao.create(new ArrayList<>());
        Assert.fail("createAuthors was failed.");
    }

    @Test
    public void readExistingAuthorTest() {
        try {
            Author author = dao.read(31);
            boolean result = author.equals(existingAuthorOne);
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readExistingAuthorTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void readNullAuthorTest() throws DaoException {
        Author author = dao.read(100);
        Assert.fail("readNullAuthorTest was failed.");
    }

    @Test
    public void updateExistingAuthorTest() {
        try {
            Author author = existingAuthorThree.clone();
            author.setName("Arthem");
            dao.update(author);
            long id = author.getId();
            Author updatingAuthor = dao.read(id);
            boolean result = author.getName().equals(updatingAuthor.getName());
            Assert.assertTrue(result);
        } catch (CloneNotSupportedException | DaoException e) {
            logger.error(e);
            Assert.fail("updateExistingAuthorTest was failed.");
        }
    }

    @Test
    public void deleteExistingAuthorTest() {
        try {
            Author author = dao.delete(23);
            boolean result = author.equals(existingAuthorTwo);
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("deleteExistingAuthorTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void deleteNullAuthorTest() throws DaoException {
        dao.delete(100);
        Assert.fail("deleteNullAuthorTest was failed.");
    }

    @After
    public void deleteDatabase() {
        dbi.dropDatabase();
    }
}