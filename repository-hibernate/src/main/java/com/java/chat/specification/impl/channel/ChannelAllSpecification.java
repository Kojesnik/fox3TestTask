package com.java.chat.specification.impl.channel;

import com.java.chat.model.impl.Channel;
import com.java.chat.specification.EntitySpecification;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class ChannelAllSpecification implements EntitySpecification<Channel> {

    @Override
    public TypedQuery<Channel> specified(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Channel> query = builder.createQuery(Channel.class);
        Root<Channel> root = query.from(Channel.class);
        query.select(root);
        return entityManager.createQuery(query);
    }

}
