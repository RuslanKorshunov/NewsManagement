package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
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
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Repository
@Qualifier("tagDao")
public class TagDao implements Dao<Tag> {
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Tag create(Tag tag) throws DaoException {
        String name = tag.getName();
        Supplier<Tag> supplier = () -> {
            Tag t;
            try {
                t = read(SELECT_BY_NAME_QUERY, name);
            } catch (DataAccessException e) {
                t = null;
            }
            return t;
        };
        Tag innerTag = supplier.get();
        if (innerTag == null) {
            try {
                KeyHolder keyHolder = new GeneratedKeyHolder();
                jdbcTemplate.update(con -> {
                    PreparedStatement ps = con.prepareStatement(INSERT_QUERY, new String[]{"id"});
                    ps.setString(1, name);
                    return ps;
                }, keyHolder);
                long id = keyHolder.getKey().longValue();
                innerTag = new Tag(id, name);
            } catch (DataAccessException e) {
                throw new DaoException(e);
            }
        }
        return innerTag;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<Tag> create(List<Tag> tags) throws DaoException {
        List<Tag> innerTags = new ArrayList<>();
        for (Tag tag : tags) {
            innerTags.add(create(tag));
        }
        return innerTags;
    }

    @Override
    public Tag read(long id) throws DaoException {
        Tag tag;
        try {
            tag = read(SELECT_BY_ID_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return tag;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Tag update(Tag tag) throws DaoException {
        long id = tag.getId();
        String name = tag.getName();
        try {
            jdbcTemplate.update(UPDATE_QUERY, name, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return tag;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Tag delete(long id) throws DaoException {
        Tag tag = read(id);
        try {
            jdbcTemplate.update(DELETE_QUERY, id);
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return tag;
    }

    private Tag read(String query, Object... objects) throws DataAccessException {
        return jdbcTemplate.queryForObject(query, objects,
                (rs, rowNum) -> {
                    long idTag = rs.getLong("id");
                    String nameTag = rs.getString("name");
                    return new Tag(idTag, nameTag);
                });
    }

    private Tag getExistingTag(Tag tag) throws DataAccessException {
        String name = tag.getName();
        Supplier<Tag> supplier = () -> {
            Tag t;
            try {
                t = read(SELECT_BY_NAME_QUERY, name);
            } catch (DataAccessException e) {
                t = null;
            }
            return t;
        };
        return supplier.get();
    }
}
