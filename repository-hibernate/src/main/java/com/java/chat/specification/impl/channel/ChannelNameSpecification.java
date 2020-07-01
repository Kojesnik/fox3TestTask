package com.java.chat.specification.impl.channel;

import com.java.chat.model.impl.Channel;
import com.java.chat.specification.EntitySpecification;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ChannelNameSpecification implements EntitySpecification<Channel> {

    private static final String NAME_FIELD = "channelName";

    private String name;

    public ChannelNameSpecification(String name) {
        this.name = name;
    }

    @Override
    public TypedQuery<Channel> specified(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Channel> query = builder.createQuery(Channel.class);
        Root<Channel> root = query.from(Channel.class);
        Predicate predicateName = builder.equal(root.get(NAME_FIELD), name);
        Predicate predicate = builder.and(predicateName);
        query.select(root).where(predicate);
        return entityManager.createQuery(query);
    }

}
