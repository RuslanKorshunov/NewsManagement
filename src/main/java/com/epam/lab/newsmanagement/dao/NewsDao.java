package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
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
    private static final String SELECT_NEWS_QUERY;
    private static final String SELECT_TAGS_QUERY;
    private static final String UPDATE_QUERY;
    private static final String UPDATE_NEWS_AUTHOR_QUERY;
    private static final String DELETE_NEWS_TAG_QUERY;

    static {
        INSERT_INTO_NEWS_QUERY = "INSERT INTO \"news\" (\"title\", \"short_text\", " +
                "\"full_text\", \"creation_date\", \"modification_date\") VALUES (?, ?, ?, ?, ?)";
        INSERT_INTO_NEWS_AUTHOR_QUERY = "INSERT INTO \"news_author\" VALUES (?, ?)";
        INSERT_INTO_NEWS_TAG_QUERY = "INSERT INTO \"news_tag\" VALUES (?, ?)";
        SELECT_NEWS_QUERY = "SELECT \"news\".\"id\", \"title\", \"short_text\", \"full_text\",\"creation_date\", " +
                "\"modification_date\", \"news_author\".\"author_id\", \"author\".\"name\", " +
                "\"author\".\"surname\" FROM \"news\" JOIN \"news_author\" ON " +
                "\"news\".\"id\"=\"news_author\".\"news_id\" JOIN \"author\" ON \"author\".\"id\"=\"news_author\".\"author_id\" " +
                "WHERE \"news\".\"id\"=?";
        SELECT_TAGS_QUERY = "SELECT \"tag_id\", \"name\" FROM \"news_tag\" JOIN \"tag\" ON \"news_tag\".\"tag_id\"=\"tag\".\"id\" where \"news_id\"=?";
        UPDATE_QUERY = "UPDATE \"news\" SET \"title\"=?, \"short_text\"=?, \"full_text\"=?, " +
                "\"modification_date\"=? WHERE \"id\"=?";
        UPDATE_NEWS_AUTHOR_QUERY = "UPDATE \"news_author\" SET \"author_id\"=? WHERE \"news_id\"=?";
        DELETE_NEWS_TAG_QUERY = "DELETE FROM \"news_tag\" WHERE \"news_id\"=?";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
            news = jdbcTemplate.queryForObject(SELECT_NEWS_QUERY, new Object[]{id},
                    (rs, rowNum) -> {
                        long idNews = rs.getLong("id");
                        String title = rs.getString("title");
                        String short_text = rs.getString("short_text");
                        String full_text = rs.getString("full_text");
                        LocalDate creationDate = rs.getDate("creation_date").toLocalDate();
                        LocalDate modificationDate = rs.getDate("modification_date").toLocalDate();
                        long idAuthor = rs.getLong("author_id");
                        String name = rs.getString("name");
                        String surname = rs.getString("surname");
                        Author author = new Author(idAuthor, name, surname);
                        return new News(idNews, title, short_text, full_text, author, new ArrayList<>(), creationDate, modificationDate);
                    });
            List<Tag> tags = jdbcTemplate.query(SELECT_TAGS_QUERY, new Object[]{id}, (rs, rowNum) -> {
                long idTag = rs.getLong("tag_id");
                String name = rs.getString("name");
                return new Tag(idTag, name);
            });
            news.setTags(tags);
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
    public News delete(long id) throws DaoException {
        return null;
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
}
