package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.PreparedStatement;
import java.util.function.Supplier;

@Repository("authorDao")
public class AuthorDao extends AbstractDao<Author> implements AuthorDaoInterface {
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

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Author create(Author author) throws DaoException {
        return super.create(author);
    }

    @Override
    public Author read(long id) throws DaoException {
        return super.read(id);
    }

    @Override
    public Author update(Author author) throws DaoException {
        return super.update(author);
    }

    @Override
    public Author delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Supplier<Author> getSupplier(Author author) {
        return super.getSupplier(author);
    }

    @Override
    Author readEntity(String query, Object... objects) throws DataAccessException {
        return getJdbcTemplate().queryForObject(query, objects,
                (rs, rowNum) -> {
                    long idAuthor = rs.getLong("id");
                    String nameAuthor = rs.getString("name");
                    String surnameAuthor = rs.getString("surname");
                    return new Author(idAuthor, nameAuthor, surnameAuthor);
                });
    }

    @Override
    String getQueryForSupplier() {
        return SELECT_BY_NAME_AND_SURNAME_QUERY;
    }

    @Override
    String getSelectByIdQuery() {
        return SELECT_BY_ID_QUERY;
    }

    @Override
    String getUpdateQuery() {
        return UPDATE_QUERY;
    }

    @Override
    String getDeleteQuery() {
        return DELETE_QUERY;
    }

    @Override
    Object[] getParametersForSupplier(Author author) {
        String name = author.getName();
        String surname = author.getSurname();
        return new Object[]{name, surname};
    }

    @Override
    Object[] getParametersForUpdate(Author author) {
        long id = author.getId();
        String name = author.getName();
        String surname = author.getSurname();
        return new Object[]{name, surname, id};
    }

    @Override
    PreparedStatementCreator getCreatorForCreateQuery(Author author) throws DataAccessException {
        String name = author.getName();
        String surname = author.getSurname();
        return con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, name);
            ps.setString(2, surname);
            return ps;
        };
    }

    @Override
    Author getClone(Author author) throws CloneNotSupportedException {
        return author.clone();
    }

    @Override
    void setId(Author author, long id) {
        author.setId(id);
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return null;
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    Class<Author> getClassObject() {
        return Author.class;
    }
}
