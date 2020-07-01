package com.java.chat.repository.impl;

import com.java.chat.exception.InsertException;
import com.java.chat.exception.RemoveException;
import com.java.chat.exception.UpdateException;
import com.java.chat.model.impl.Channel;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.specification.EntitySpecification;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class ChannelRepositoryImpl implements ChannelRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Channel add(Channel channel) {
        entityManager.unwrap(Session.class).replicate(channel, ReplicationMode.IGNORE);
        if(channel.getId() == 0) {
            throw new InsertException("Channel insert exception");
        }
        return channel;
    }

    @Override
    @Transactional
    public Channel update(Channel channel) {
        Channel channelToUpdate = entityManager.find(Channel.class, channel.getId());
        if (Objects.isNull(channelToUpdate)) {
            throw new UpdateException("No such channel to update");
        }
        return entityManager.merge(channel);
    }

    @Override
    @Transactional
    public Boolean remove(Channel channel) {
        Channel channelToRemove = entityManager.find(Channel.class , channel.getId());
        if (Objects.isNull(channelToRemove)) {
            throw new RemoveException("No such channel to remove");
        }
        entityManager.remove(channelToRemove);
        return true;
    }

    @Override
    @Transactional
    public List<Channel> query(EntitySpecification<Channel> specification) {
        return specification.specified(entityManager).getResultList();
    }

}
