package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.config.DatabaseInitializer;
import com.epam.lab.newsmanagement.config.TestConfig;
import com.epam.lab.newsmanagement.entity.Tag;
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
import java.util.List;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {TestConfig.class})
public class TagDaoTest {
    @Autowired
    private TagDao dao;
    @Autowired
    private Logger logger;
    @Autowired
    private DatabaseInitializer dbi;

    private Tag newTag;
    private Tag existingTagOne;
    private Tag existingTagTwo;
    private Tag existingTagThree;

    @Before
    public void initializeAuthor() {
        newTag = new Tag("snape");
        existingTagOne = new Tag(31, "policy");
        existingTagTwo = new Tag(22, "uk");
        existingTagThree = new Tag(3, "italy");
        dbi.createDatabase();
    }

    @Test
    public void createExistingTagTest() {
        try {
            Tag tag = dao.create(existingTagOne);
            long id = tag.getId();
            boolean result = id == existingTagOne.getId();
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createExistingTagTest was failed.");
        }
    }

    @Test
    public void createNewTagTest() {
        try {
            Tag tag = dao.create(newTag);
            long id = tag.getId();
            boolean result = id != 0;
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createExistingTagTest was failed.");
        }
    }

    @Test
    public void createTags() {
        try {
            List<Tag> tags = new ArrayList<>();
            tags.add(existingTagOne);
            tags.add(existingTagTwo);
            dao.create(tags);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("createTags was failed.");
        }
    }

    @Test
    public void readExistingTagTest() {
        try {
            Tag tag = dao.read(31);
            boolean result = tag.equals(existingTagOne);
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("readExistingTagTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void readNullAuthorTest() throws DaoException {
        Tag tag = dao.read(100);
        Assert.fail("readNullTagTest was failed.");
    }

    @Test
    public void updateExistingTagTest() {
        try {
            Tag tag = existingTagThree.clone();
            tag.setName("Greece");
            dao.update(tag);
            long id = tag.getId();
            Tag updatingTag = dao.read(id);
            boolean result = tag.getName().equals(updatingTag.getName());
            Assert.assertTrue(result);
        } catch (CloneNotSupportedException | DaoException e) {
            logger.error(e);
            Assert.fail("updateExistingTagTest was failed.");
        }
    }

    @Test
    public void deleteExistingTagTest() {
        try {
            Tag tag = dao.delete(22);
            boolean result = tag.equals(existingTagTwo);
            Assert.assertTrue(result);
        } catch (DaoException e) {
            logger.error(e);
            Assert.fail("deleteExistingTagTest was failed.");
        }
    }

    @Test(expected = DaoException.class)
    public void deleteNullTagTest() throws DaoException {
        Tag tag = dao.delete(100);
        Assert.fail("deleteNullTagTest was failed.");
    }

    @After
    public void deleteDatabase() {
        dbi.dropDatabase();
    }
}