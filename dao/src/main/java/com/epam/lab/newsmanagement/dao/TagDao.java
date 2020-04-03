package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("tagDao")
@Transactional(isolation = Isolation.READ_COMMITTED,
        rollbackFor = DaoException.class)
public class TagDao extends AbstractDao<Tag> implements TagDaoInterface {
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
        Tag old = read(tag.getId());
        try {
            CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
            CriteriaUpdate<Tag> update = builder.createCriteriaUpdate(Tag.class);
            Root<Tag> root = update.from(Tag.class);
            update.set(Constants.NAME, tag.getName());
            update.where(builder.equal(root.get(Constants.ID), tag.getId()));
            getEntityManager().createQuery(update).executeUpdate();
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return old;
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
