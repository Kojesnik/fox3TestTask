package com.java.chat.specification.impl.channel;

import com.java.chat.model.impl.Channel;
import com.java.chat.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ChannelIdSpecification implements EntitySpecification<Channel> {

    private static final String ID_FIELD = "id";

    private long id;

    public ChannelIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public TypedQuery<Channel> specified(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Channel> query = builder.createQuery(Channel.class);
        Root<Channel> root = query.from(Channel.class);
        Predicate predicateId = builder.equal(root.get(ID_FIELD), id);
        Predicate predicate = builder.and(predicateId);
        query.select(root).where(predicate);
        return entityManager.createQuery(query);
    }

}
