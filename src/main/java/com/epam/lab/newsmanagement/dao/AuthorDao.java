package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Qualifier("authorDao")
public class AuthorDao implements Dao<Author> {
    private static final String INSERT_QUERY;
    private static final String SELECT_MAX_INDEX_QUERY;
    private static final String SELECT_BY_ID_QUERY;

    static {
        INSERT_QUERY = "INSERT INTO \"author\" VALUES (?, ?, ?)";
        SELECT_MAX_INDEX_QUERY = "SELECT MAX(\"id\") FROM \"author\"";
        SELECT_BY_ID_QUERY = "SELECT * FROM \"author\" WHERE \"id\"=?";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Author create(Author author) throws DaoException {
        String name = author.getName();
        String surname = author.getSurname();
        try {
            long id = getMaxId() + 1;
            jdbcTemplate.update(INSERT_QUERY, id, name, surname);
            author.setId(id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return author;
    }

    @Override
    public Author read(long id) throws DaoException {
        Author author;
        try {
            author = jdbcTemplate.
                    queryForObject(SELECT_BY_ID_QUERY, new Object[]{id},
                            (rs, rowNum) -> {
                                String name = rs.getString("name");
                                String surname = rs.getString("surname");
                                return new Author(id, name, surname);
                            });
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return author;
    }

    private long getMaxId() throws DaoException {
        long id;
        try {
            Optional<Long> optionalLong = Optional.ofNullable(
                    jdbcTemplate.queryForObject(SELECT_MAX_INDEX_QUERY, Long.class));
            if (optionalLong.isPresent()) {
                id = optionalLong.get();
            } else {
                throw new DaoException("AuthorDao can't get max id.");
            }
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return id;
    }
}
