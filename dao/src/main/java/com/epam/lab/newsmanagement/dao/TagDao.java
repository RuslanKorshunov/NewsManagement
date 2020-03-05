package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Repository
@Qualifier("tagDao")
public class TagDao extends AbstractDao<Tag> {
    private static final String INSERT_QUERY;
    private static final String SELECT_BY_NAME_QUERY;
    private static final String SELECT_BY_ID_QUERY;
    private static final String UPDATE_QUERY;
    private static final String DELETE_QUERY;

    static {
        INSERT_QUERY = "INSERT INTO \"tag\"(\"name\") VALUES (?)";
        SELECT_BY_NAME_QUERY = "SELECT * FROM \"tag\" WHERE \"name\"=?";
        SELECT_BY_ID_QUERY = "SELECT * FROM \"tag\" WHERE \"id\"=?";
        UPDATE_QUERY = "UPDATE \"tag\" SET \"name\"=? WHERE \"id\"=?";
        DELETE_QUERY = "DELETE FROM \"tag\" WHERE \"id\"=?";
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Tag create(Tag tag) throws DaoException {
        return super.create(tag);
    }

    @Override
    public List<Tag> create(List<Tag> tags) throws DaoException {
        List<Tag> innerTags = new ArrayList<>();
        for (Tag tag : tags) {
            innerTags.add(create(tag));
        }
        return innerTags;
    }

    @Override
    public Tag read(long id) throws DaoException {
        return super.read(id);
    }

    @Override
    public Tag update(Tag tag) throws DaoException {
        return super.update(tag);
    }

    @Override
    public Tag delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Supplier<Tag> getSupplier(Tag tag) {
        return super.getSupplier(tag);
    }

    @Override
    JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    @Override
    Tag readEntity(String query, Object... objects) throws DataAccessException {
        return getJdbcTemplate().queryForObject(query, objects,
                (rs, rowNum) -> {
                    long idTag = rs.getLong("id");
                    String nameTag = rs.getString("name");
                    return new Tag(idTag, nameTag);
                });
    }

    @Override
    String getQueryForSupplier() {
        return SELECT_BY_NAME_QUERY;
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
    Object[] getParametersForSupplier(Tag tag) {
        return new Object[]{tag.getName()};
    }

    @Override
    Object[] getParametersForUpdate(Tag tag) {
        long id = tag.getId();
        String name = tag.getName();
        return new Object[]{name, id};
    }

    @Override
    PreparedStatementCreator getCreatorForCreateQuery(Tag tag) throws DataAccessException {
        String name = tag.getName();
        return con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
            ps.setString(1, name);
            return ps;
        };
    }

    @Override
    Tag getClone(Tag tag) throws CloneNotSupportedException {
        return tag.clone();
    }

    @Override
    void setId(Tag tag, long id) {
        tag.setId(id);
    }
}
