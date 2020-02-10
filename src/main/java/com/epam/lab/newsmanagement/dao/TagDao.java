package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
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
@Qualifier("tagDao")
public class TagDao implements Dao<Tag> {
    private static final String INSERT_QUERY;
    private static final String SELECT_MAX_INDEX_QUERY;
    public static final String SELECT_BY_NAME_QUERY;

    static {
        INSERT_QUERY = "INSERT INTO \"tag\" VALUES (?, ?)";
        SELECT_MAX_INDEX_QUERY = "SELECT MAX(\"id\") FROM \"tag\"";
        SELECT_BY_NAME_QUERY = "SELECT * FROM \"tag\" WHERE \"name\"=?";
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
                t = jdbcTemplate.queryForObject(SELECT_BY_NAME_QUERY, new Object[]{name},
                        (rs, rowNum) -> {
                            long idTag = rs.getLong("id");
                            String nameTag = rs.getString("name");
                            return new Tag(idTag, nameTag);
                        });
            } catch (DataAccessException e) {
                t = null;
            }
            return t;
        };
        Tag innerTag = supplier.get();
        if (innerTag == null) {
            try {
                long id = getMaxId(jdbcTemplate, SELECT_MAX_INDEX_QUERY) + 1;
                jdbcTemplate.update(INSERT_QUERY, id, name);
                innerTag = tag;
                innerTag.setId(id);
            } catch (DataAccessException e) {
                throw new DaoException(e);
            }
        }
        return innerTag;
    }

    @Override
    public Tag read(long id) throws DaoException {
        return null;
    }

    @Override
    public Tag update(Tag tag) throws DaoException {
        return null;
    }

    @Override
    public Tag delete(long id) throws DaoException {
        return null;
    }
}
