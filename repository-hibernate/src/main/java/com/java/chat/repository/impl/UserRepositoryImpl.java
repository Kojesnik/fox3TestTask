package com.java.chat.repository.impl;

import com.java.chat.exception.InsertException;
import com.java.chat.exception.RemoveException;
import com.java.chat.exception.UpdateException;
import com.java.chat.model.impl.User;
import com.java.chat.repository.UserRepository;
import com.java.chat.specification.EntitySpecification;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User add(User user) {
        entityManager.persist(user);
        if(user.getId() == 0) {
            throw new InsertException("User insert exception");
        }
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        User userToUpdate = entityManager.find(User.class, user.getId());
        if (Objects.isNull(userToUpdate)) {
            throw new UpdateException("No such user to update");
        }
        return entityManager.merge(user);
    }

    @Override
    @Transactional
    public Boolean remove(User user) {
        User userToRemove = entityManager.find(User.class , user.getId());
        if (Objects.isNull(userToRemove)) {
            throw new RemoveException("No such user to remove");
        }
        entityManager.remove(userToRemove);
        return true;
    }

    @Override
    @Transactional
    public List<User> query(EntitySpecification<User> specification) {
        return specification.specified(entityManager).getResultList();
    }
}
