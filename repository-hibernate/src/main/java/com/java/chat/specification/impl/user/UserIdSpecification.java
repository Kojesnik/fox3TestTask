package com.java.chat.specification.impl.user;

import com.java.chat.model.impl.User;
import com.java.chat.specification.EntitySpecification;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserIdSpecification implements EntitySpecification<User> {

    private static final String ID_FIELD = "id";

    private long id;

    public UserIdSpecification(long id) {
        this.id = id;
    }

    @Override
    public TypedQuery<User> specified(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        Predicate predicateId = builder.equal(root.get(ID_FIELD), id);
        Predicate predicate = builder.and(predicateId);
        query.select(root).where(predicate);
        return entityManager.createQuery(query);
    }

}
