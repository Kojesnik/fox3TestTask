package com.java.chat.repository.impl;

import static org.junit.Assert.*;

import com.java.chat.TestConfig;
import com.java.chat.exception.RemoveException;
import com.java.chat.exception.UpdateException;
import com.java.chat.model.impl.Channel;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.specification.impl.channel.ChannelAllSpecification;
import com.java.chat.specification.impl.channel.ChannelIdSpecification;
import com.java.chat.specification.impl.channel.ChannelNameSpecification;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ChannelRepositoryImplTest {

    @Autowired
    private ChannelRepository channelRepository;

    @Test
    public void add() {
        Channel expected = new Channel(6l, "it");
        channelRepository.add(new Channel("it"));
        Channel actual = channelRepository.query(new ChannelIdSpecification(6l)).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Channel expected = new Channel(1l, "javajava");
        channelRepository.update(new Channel(1l, "javajava"));
        Channel actual = channelRepository.query(new ChannelIdSpecification(1l)).get(0);
        assertEquals(expected, actual);
    }

    @Test(expected = UpdateException.class)
    public void updateWithException() {
        channelRepository.update(new Channel(100l, "javajava"));
    }

    @Test
    public void remove() {
        boolean expected = true;
        boolean actual = false;
        channelRepository.remove(new Channel(1l, "java"));
        if (channelRepository.query(new ChannelIdSpecification(1l)).isEmpty()) {
            actual = true;
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RemoveException.class)
    public void removeWithException() {
        channelRepository.remove(new Channel(100l, "java"));
    }

    @Test
    public void queryById() {
        List<Channel> expected = new ArrayList<>();
        expected.add(new Channel(1l, "java"));
        List<Channel> actual = channelRepository.query(new ChannelIdSpecification(1l));
        assertEquals(expected, actual);
    }

    @Test
    public void queryByLogin() {
        List<Channel> expected = new ArrayList<>();
        expected.add(new Channel(1l, "java"));
        List<Channel> actual = channelRepository.query(new ChannelNameSpecification("java"));
        assertEquals(expected, actual);
    }

    @Test
    public void queryAll() {
        List<Channel> expected = new ArrayList<>();
        Collections.addAll(expected, new Channel(1l, "java"), new Channel(2l, "web"),
                                    new Channel(3l, "fox3"), new Channel(4l, "test"),
                                    new Channel(5l, "car"));
        List<Channel> actual = channelRepository.query(new ChannelAllSpecification());
        assertEquals(expected, actual);
    }

}