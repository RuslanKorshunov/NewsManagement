package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.Supplier;

@Repository
@Qualifier("authorDao")
public class AuthorDao implements Dao<Author> {
    private static final String INSERT_QUERY;
    private static final String SELECT_BY_ID_QUERY;
    private static final String UPDATE_QUERY;
    private static final String DELETE_QUERY;
    private static final String SELECT_BY_NAME_AND_SURNAME_QUERY;

    static {
        INSERT_QUERY = "INSERT INTO \"author\" (\"name\", \"surname\") VALUES (?, ?)";
        SELECT_BY_ID_QUERY = "SELECT * FROM \"author\" WHERE \"id\"=?";
        SELECT_BY_NAME_AND_SURNAME_QUERY = "SELECT * FROM \"author\" WHERE \"name\"=? AND \"surname\"=?";
        UPDATE_QUERY = "UPDATE \"author\" SET \"name\"=?, \"surname\"=? WHERE \"id\"=?";
        DELETE_QUERY = "DELETE FROM \"author\" WHERE \"id\"=?";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Author create(Author author) throws DaoException {
        String name = author.getName();
        String surname = author.getSurname();
        Supplier<Author> supplier = () -> {
            Author a;
            try {
                a = read(SELECT_BY_NAME_AND_SURNAME_QUERY, name, surname);
            } catch (DataAccessException e) {
                a = null;
            }
            return a;
        };
        Author innerAuthor = supplier.get();
        if (innerAuthor == null) {
            try {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
                    ps.setString(1, name);
                    ps.setString(2, surname);
                    return ps;
                }, keyHolder);
                long id = keyHolder.getKey().longValue();
                innerAuthor = new Author(id, name, surname);
            } catch (DataAccessException e) {
                throw new DaoException(e);
            }
        }
        return innerAuthor;
    }

    @Override
    public List<Author> create(List<Author> authors) throws DaoException {
        throw new DaoException("Operation isn't supported by authorDao.");
    }

    @Override
    public Author read(long id) throws DaoException {
        Author author;
        try {
            author = read(SELECT_BY_ID_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return author;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Author update(Author author) throws DaoException {
        long id = author.getId();
        String name = author.getName();
        String surname = author.getSurname();
        try {
            jdbcTemplate.update(UPDATE_QUERY, name, surname, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return author;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Author delete(long id) throws DaoException {
        Author author = read(id);
        try {
            jdbcTemplate.update(DELETE_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return author;
    }

    private Author read(String query, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForObject(query, objects,
                (rs, rowNum) -> {
                    long idAuthor = rs.getLong("id");
                    String nameAuthor = rs.getString("name");
                    String surnameAuthor = rs.getString("surname");
                    return new Author(idAuthor, nameAuthor, surnameAuthor);
                });
    }
}
