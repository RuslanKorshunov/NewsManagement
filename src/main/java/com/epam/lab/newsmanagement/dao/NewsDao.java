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
import java.util.List;

@Repository
@Qualifier("newsDao")
public class NewsDao implements Dao<News> {
    private static final String INSERT_INTO_NEWS_QUERY;
    private static final String INSERT_INTO_NEWS_AUTHOR_QUERY;
    private static final String INSERT_INTO_NEWS_TAG_QUERY;

    static {
        INSERT_INTO_NEWS_QUERY = "INSERT INTO \"news\" (\"title\", \"short_text\", " +
                "\"full_text\", \"creation_date\", \"modification_date\") VALUES (?, ?, ?, ?, ?)";
        INSERT_INTO_NEWS_AUTHOR_QUERY = "INSERT INTO \"news_author\" VALUES (?, ?)";
        INSERT_INTO_NEWS_TAG_QUERY = "INSERT INTO \"news_tag\" VALUES (?, ?)";
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
            jdbcTemplate.batchUpdate(INSERT_INTO_NEWS_TAG_QUERY, new BatchPreparedStatementSetter() {
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
            });
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return innerNews;
    }

    @Override
    public List<News> create(List<News> t) throws DaoException {
        return null;
    }

    @Override
    public News read(long id) throws DaoException {
        return null;
    }

    @Override
    public News update(News news) throws DaoException {
        return null;
    }

    @Override
    public News delete(long id) throws DaoException {
        return null;
    }
}
