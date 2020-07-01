package com.java.chat.repository.impl;

import static org.junit.Assert.*;
import com.java.chat.TestConfig;
import com.java.chat.exception.RemoveException;
import com.java.chat.exception.UpdateException;
import com.java.chat.model.impl.User;
import com.java.chat.repository.UserRepository;
import com.java.chat.specification.impl.user.UserIdSpecification;
import com.java.chat.specification.impl.user.UserLoginSpecification;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Sql(scripts = "classpath:drop_all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class UserRepositoryImplTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void add() {
        User expected = new User(6l, "admin", "admin");
        userRepository.add(new User("admin", "admin"));
        User actual = userRepository.query(new UserIdSpecification(6l)).get(0);
        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        User expected = new User(1l, "kojesnik", "123aa");
        userRepository.update(new User(1l, "kojesnik", "123aa"));
        User actual = userRepository.query(new UserIdSpecification(1l)).get(0);
        assertEquals(expected, actual);
    }

    @Test(expected = UpdateException.class)
    public void updateWithException() {
        userRepository.update(new User(100l, "kojesnik", "123aa"));
    }

    @Test
    public void remove() {
        boolean expected = true;
        boolean actual = false;
        userRepository.remove(new User(1l, "kojes", "123"));
        if (userRepository.query(new UserIdSpecification(1l)).isEmpty()) {
            actual = true;
        }
        assertEquals(expected, actual);
    }

    @Test(expected = RemoveException.class)
    public void removeWithException() {
        userRepository.remove(new User(100l, "kojes", "123"));
    }

    @Test
    public void queryById() {
        List<User> expected = new ArrayList();
        expected.add(new User(1L, "kojes", "123"));
        List<User> actual = userRepository.query(new UserIdSpecification(1L));
        assertEquals(expected, actual);
    }

    @Test
    public void queryByLogin() {
        List<User> expected = new ArrayList();
        expected.add(new User(1L, "kojes", "123"));
        List<User> actual = userRepository.query(new UserLoginSpecification("kojes"));
        assertEquals(expected, actual);
    }
}