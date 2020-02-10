package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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
                a = jdbcTemplate.queryForObject(SELECT_BY_NAME_AND_SURNAME_QUERY, new Object[]{name, surname},
                        (rs, rowNum) -> {
                            long idAuthor = rs.getLong("id");
                            String nameAuthor = rs.getString("name");
                            String surnameAuthor = rs.getString("surname");
                            return new Author(idAuthor, nameAuthor, surnameAuthor);
                        });
            } catch (DataAccessException e) {
                a = null;
            }
            return a;
        };
        Author innerAuthor = supplier.get();
        if (innerAuthor == null) {
            try {
                jdbcTemplate.update(INSERT_QUERY, name, surname);
                innerAuthor = supplier.get();
            } catch (DataAccessException e) {
                throw new DaoException(e);
            }
        }
        return innerAuthor;
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
}
