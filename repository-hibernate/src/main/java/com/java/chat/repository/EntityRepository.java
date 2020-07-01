package com.java.chat.repository;

import com.java.chat.model.AbstractEntity;
import com.java.chat.specification.EntitySpecification;

import java.util.List;

public interface EntityRepository<T extends AbstractEntity> {

    T add(T entity);
    T update(T entity);
    Boolean remove(T entity);
    List<T> query(EntitySpecification<T> specification);

}
