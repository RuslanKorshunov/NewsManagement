package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Qualifier("newsDao")
public class NewsDao implements Dao<News> {
    private static final String INSERT_INTO_NEWS_QUERY;
    private static final String INSERT_INTO_NEWS_AUTHOR_QUERY;
    private static final String INSERT_INTO_NEWS_TAG_QUERY;
    private static final String SELECT_NEWS_BY_ID_QUERY;
    private static final String BEGIN_SELECT_NEWS_QUERY;
    private static final String END_SELECT_BY_CRITERIA_QUERY;
    private static final String UPDATE_QUERY;
    private static final String UPDATE_NEWS_AUTHOR_QUERY;
    private static final String DELETE_NEWS_TAG_QUERY;
    private static final String ID_SUFFIX;
    private static final String DELETE_QUERY;
    private static final String AUTHOR_SUFFIX;
    private static final String TAG_SUFFIX;
    private static final String WHERE;
    private static final String AND;
    private static final String OR;
    private static final String ORDER_BY_NEWS_ID;
    private static final String ORDER_BY_AUTHOR;
    private static final String ORDER_BY_DATE;

    private static final Logger logger;

    static {
        ID_SUFFIX = "WHERE \"news\".\"id\"=?";
        AUTHOR_SUFFIX = "\"author\".\"name\"=? AND \"author\".\"surname\"=?";
        TAG_SUFFIX = "\"tag\".\"name\"=?";
        WHERE = "WHERE";
        OR = "OR";
        AND = "AND";
        ORDER_BY_NEWS_ID = "ORDER BY \"news\".\"id\"";
        ORDER_BY_AUTHOR = "ORDER BY \"author\".\"surname\", \"author\".\"name\"";
        ORDER_BY_DATE = "ORDER BY \"news\".\"modification_date\" DESC";
        BEGIN_SELECT_NEWS_QUERY = "SELECT \"news\".\"id\", STRING_AGG(\"tag\".\"id\"||'-'||\"tag\".\"name\", ',') as \"tags\", \"news\".\"title\", \"news\".\"short_text\", \"news\".\"full_text\", " +
                "\"news\".\"creation_date\", \"news\".\"modification_date\", \"author\".\"id\" AS \"author_id\", \"author\".\"name\", \"author\".\"surname\" FROM \"tag\" " +
                "JOIN \"news_tag\" ON \"tag\".\"id\"=\"news_tag\".\"tag_id\" " +
                "JOIN \"news\" ON \"news\".\"id\"=\"news_tag\".\"news_id\" " +
                "JOIN \"news_author\" ON \"news_author\".\"news_id\"=\"news\".\"id\" " +
                "JOIN \"author\" ON \"news_author\".\"author_id\"=\"author\".\"id\"";
        END_SELECT_BY_CRITERIA_QUERY = "GROUP BY \"news\".\"id\", \"author\".\"id\"";
        INSERT_INTO_NEWS_QUERY = "INSERT INTO \"news\" (\"title\", \"short_text\", " +
                "\"full_text\", \"creation_date\", \"modification_date\") VALUES (?, ?, ?, ?, ?)";
        INSERT_INTO_NEWS_AUTHOR_QUERY = "INSERT INTO \"news_author\" VALUES (?, ?)";
        INSERT_INTO_NEWS_TAG_QUERY = "INSERT INTO \"news_tag\" VALUES (?, ?)";
        SELECT_NEWS_BY_ID_QUERY = BEGIN_SELECT_NEWS_QUERY + " " + ID_SUFFIX + " " + END_SELECT_BY_CRITERIA_QUERY;
        UPDATE_QUERY = "UPDATE \"news\" SET \"title\"=?, \"short_text\"=?, \"full_text\"=?, " +
                "\"modification_date\"=? WHERE \"id\"=?";
        UPDATE_NEWS_AUTHOR_QUERY = "UPDATE \"news_author\" SET \"author_id\"=? WHERE \"news_id\"=?";
        DELETE_NEWS_TAG_QUERY = "DELETE FROM \"news_tag\" WHERE \"news_id\"=?";
        DELETE_QUERY = "DELETE FROM \"news\" WHERE \"id\"=?";
        logger = LogManager.getLogger(NewsDao.class);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NumberValidator validator;

    public enum SortCriteria {
        AUTHOR,
        DATE
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News create(News news) throws DaoException {
        String title = news.getTitle();
        String shortText = news.getShortText();
        String fullText = news.getFullText();
        LocalDate creationDate = news.getCreationDate();
        LocalDate modificationDate = news.getModificationDate();
        Author author = news.getAuthor();
        List<Tag> tags = news.getTags();
        News innerNews;
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(INSERT_INTO_NEWS_QUERY, new String[]{"id"});
                ps.setString(1, title);
                ps.setString(2, shortText);
                ps.setString(3, fullText);
                ps.setDate(4, Date.valueOf(creationDate));
                ps.setDate(5, Date.valueOf(modificationDate));
                return ps;
            }, keyHolder);
            long id = (long) keyHolder.getKey();
            innerNews = new News(id, title, shortText, fullText, author, tags, creationDate, modificationDate);
            long idAuthor = author.getId();
            jdbcTemplate.update(INSERT_INTO_NEWS_AUTHOR_QUERY, id, idAuthor);
            jdbcTemplate.batchUpdate(INSERT_INTO_NEWS_TAG_QUERY, getBatchPreparedStatementSetter(tags, id));
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return innerNews;
    }

    @Override
    public List<News> create(List<News> t) throws DaoException {
        throw new DaoException("Operation isn't supported by newsDao.");
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News read(long id) throws DaoException {
        News news;
        try {
            RowMapper<News> rowMapper = getRowMapper();
            news = jdbcTemplate.queryForObject(SELECT_NEWS_BY_ID_QUERY, new Object[]{id}, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return news;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<News> read(SearchCriteria sc) throws DaoException {
        Author author = sc.getAuthor();
        List<Tag> tags = sc.getTags();
        String query = BEGIN_SELECT_NEWS_QUERY;
        boolean isExisted = tags != null && !tags.isEmpty();
        if (!(author == null && !isExisted)) {
            query += " " + WHERE;
            if (author != null) {
                query += " " + AUTHOR_SUFFIX;
                if (isExisted) {
                    query += " " + AND;
                }
            }
            if (isExisted) {
                int size = tags.size();
                query += " " + TAG_SUFFIX;
                for (int i = 1; i < size; i++) {
                    query += " " + OR + " " + TAG_SUFFIX;
                }
            }
        }
        query += " " + END_SELECT_BY_CRITERIA_QUERY + " " + ORDER_BY_NEWS_ID;
        List<News> news;
        try {
            List<String> parameters = new ArrayList<>();
            if (author != null) {
                parameters.add(author.getName());
                parameters.add(author.getSurname());
            }
            if (tags != null) {
                tags.forEach(tag -> {
                    String name = tag.getName();
                    parameters.add(name);
                });
            }
            RowMapper<News> rowMapper = getRowMapper();
            news = jdbcTemplate.query(query, parameters.toArray(), rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return news;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<News> read(SortCriteria sc) throws DaoException {
        List<News> news;
        String query = BEGIN_SELECT_NEWS_QUERY + " " + END_SELECT_BY_CRITERIA_QUERY;
        switch (sc) {
            case AUTHOR:
                query += " " + ORDER_BY_AUTHOR;
                break;
            case DATE:
                query += " " + ORDER_BY_DATE;
        }
        try {
            RowMapper<News> rowMapper = getRowMapper();
            news = jdbcTemplate.query(query, rowMapper);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return news;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News update(News news) throws DaoException {
        long idNews = news.getId();
        String title = news.getTitle();
        String shortText = news.getShortText();
        String fullText = news.getFullText();
        LocalDate modificationDate = LocalDate.now();
        List<Tag> tags = news.getTags();
        try {
            jdbcTemplate.update(UPDATE_QUERY, title, shortText, fullText, modificationDate, idNews);
            long idAuthor = news.getAuthor().getId();
            jdbcTemplate.update(UPDATE_NEWS_AUTHOR_QUERY, idAuthor, idNews);
            jdbcTemplate.update(DELETE_NEWS_TAG_QUERY, idNews);
            jdbcTemplate.batchUpdate(INSERT_INTO_NEWS_TAG_QUERY, getBatchPreparedStatementSetter(tags, idNews));
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return news;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News delete(long id) throws DaoException {
        News news = read(id);
        try {
            jdbcTemplate.update(DELETE_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return news;
    }

    private BatchPreparedStatementSetter getBatchPreparedStatementSetter(List<Tag> tags, long id) {
        return new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                long idTag = tag.getId();
                ps.setLong(1, id);
                ps.setLong(2, idTag);
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        };
    }

    private RowMapper<News> getRowMapper() {
        return (rs, rowNum) -> {
            long idNews = rs.getLong("id");
            String title = rs.getString("title");
            String short_text = rs.getString("short_text");
            String full_text = rs.getString("full_text");
            LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
            LocalDate modificationDate = rs.getDate("modification_date").toLocalDate();
            String tagsString = rs.getString("tags");
            List<Tag> tags = new ArrayList<>();
            for (String tagString : tagsString.split(",")) {
                String[] tagInfo = tagString.split("-");
                if (validator.validate(tagInfo[0])) {
                    long idTag = Long.parseLong(tagInfo[0]);
                    Tag tag = new Tag(idTag, tagInfo[1]);
                    tags.add(tag);
                } else {
                    logger.warn("Tag " + tagString + " can't be processed.");
                }
            }
            long idAuthor = rs.getLong("author_id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            Author author = new Author(idAuthor, name, surname);
            return new News(idNews, title, short_text, full_text, author, tags, creationDate, modificationDate);
        };
    }
}
