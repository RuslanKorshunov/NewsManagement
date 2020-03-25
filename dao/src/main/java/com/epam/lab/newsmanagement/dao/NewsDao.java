package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.entity.News;
import com.epam.lab.newsmanagement.entity.SearchCriteria;
import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import com.epam.lab.newsmanagement.validator.NumberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("newsDao")
public class NewsDao implements NewsDaoInterface {
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
    }

    @Autowired
    private NumberValidator validator;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public News create(News news) throws DaoException {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News read(long id) throws DaoException {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<News> read(SearchCriteria sc) throws DaoException {
        return null;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<News> read(SortCriteria sc) throws DaoException {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public News update(News news) throws DaoException {
        return null;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
