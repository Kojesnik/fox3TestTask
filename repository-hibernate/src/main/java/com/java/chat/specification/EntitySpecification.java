package com.java.chat.specification;

import com.java.chat.model.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public interface EntitySpecification<E extends AbstractEntity> {

    TypedQuery<E> specified(EntityManager entityManager);

}
