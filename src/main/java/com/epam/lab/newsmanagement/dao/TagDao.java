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
    private static final String SELECT_BY_NAME_QUERY;
    private static final String READ_BY_ID_QUERY;

    static {
        INSERT_QUERY = "INSERT INTO \"tag\"(\"name\") VALUES (?)";
        SELECT_BY_NAME_QUERY = "SELECT * FROM \"tag\" WHERE \"name\"=?";
        READ_BY_ID_QUERY = "SELECT * FROM \"tag\" WHERE \"id\"=?";
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
                jdbcTemplate.update(INSERT_QUERY, name);
                innerTag = supplier.get();
            } catch (DataAccessException e) {
                throw new DaoException(e);
            }
        }
        return innerTag;
    }

    @Override
    public Tag read(long id) throws DaoException {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(READ_BY_ID_QUERY, new Object[]{id},
                    (rs, rowNum) -> {
                        long idTag = rs.getLong("id");
                        String nameTag = rs.getString("name");
                        return new Tag(idTag, nameTag);
                    });
        } catch (DataAccessException e) {
            throw new DaoException(e);
        }
        return tag;
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
