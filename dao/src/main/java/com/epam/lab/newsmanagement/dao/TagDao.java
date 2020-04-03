package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository("tagDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
public class TagDao extends AbstractDao<Tag> implements TagDaoInterface {
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

    @Override
    @Modifying
    public Tag create(Tag tag) throws DaoException {
        return super.create(tag);
    }

    @Override
    @Modifying
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
    @Modifying
    public Tag update(Tag tag) throws DaoException {
        return super.update(tag);
    }

    @Override
    @Modifying
    public Tag delete(long id) throws DaoException {
        return super.delete(id);
    }

    @Override
    Class<Tag> getClassObject() {
        return Tag.class;
    }
}
