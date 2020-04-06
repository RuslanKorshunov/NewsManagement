package com.epam.lab.newsmanagement.dao;

import com.epam.lab.newsmanagement.entity.Tag;
import com.epam.lab.newsmanagement.exception.DaoException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
        Tag result = read(tag);
        if (result == null) {
            result = super.create(tag);
        }
        return result;
    }

    @Override
    @Modifying
    public List<Tag> create(List<Tag> tags) throws DaoException {
        List<Tag> result = new ArrayList<>();
        for (Tag tag : tags) {
            Tag resultTag = read(tag);
            if (resultTag == null) {
                resultTag = super.create(tag);
            }
            result.add(resultTag);
        }
        return result;
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

    private Tag read(Tag tag) throws DaoException {
        Tag result;
        try {
            CriteriaBuilder builder =
                    getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Tag> query = builder.createQuery(getClassObject());
            Root<Tag> root = query.from(getClassObject());
            query.select(root);
            query.where(builder.equal(root.get(Constants.NAME), tag.getName()));
            result = getEntityManager().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            result = null;
        } catch (Exception e) {
            throw new DaoException(e);
        }
        return result;
    }
}
