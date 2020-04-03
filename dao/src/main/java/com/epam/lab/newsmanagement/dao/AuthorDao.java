package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Author;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Repository("authorDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
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

    @Override
    @Modifying
    public Author create(Author author) throws DaoException {
        return super.create(author);
    }

    @Override
    public Author read(long id) throws DaoException {
        return super.read(id);
    }

    @Override
    @Modifying
    public Author update(Author author) throws DaoException {
        return super.update(author);
    }

    @Override
    @Modifying
    public Author delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Class<Author> getClassObject() {
        return Author.class;
    }
}
