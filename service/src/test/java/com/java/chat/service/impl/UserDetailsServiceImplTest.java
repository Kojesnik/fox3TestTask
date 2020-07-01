package com.java.chat.service.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.java.chat.exception.FindException;
import com.java.chat.model.impl.User;
import com.java.chat.repository.UserRepository;
import com.java.chat.repository.impl.UserRepositoryImpl;
import com.java.chat.specification.impl.user.UserLoginSpecification;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDetailsServiceImplTest {

    private UserDetailsService userDetailsService;
    private UserRepository userRepository = mock(UserRepositoryImpl.class);
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void loadUserByUsername() {
        String username = "kojes";
        List<User> users = new ArrayList<>();
        User user = new User(1l, "kojes", "123");
        users.add(user);
        when(userRepository.query(any(UserLoginSpecification.class))).thenReturn(users);
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        org.springframework.security.core.userdetails.User expected =
            new org.springframework.security.core.userdetails.User("kojes", passwordEncoder.encode("123"), Arrays.asList(authority));
        org.springframework.security.core.userdetails.User actual = (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserLoginSpecification.class));
    }

    @Test(expected = FindException.class)
    public void loadUserByUsernameWithException() {
        String username = "kojes";
        List<User> users = new ArrayList<>();
        when(userRepository.query(any(UserLoginSpecification.class))).thenReturn(users);
        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        org.springframework.security.core.userdetails.User expected =
            new org.springframework.security.core.userdetails.User("kojes", passwordEncoder.encode("123"), Arrays.asList(authority));
        org.springframework.security.core.userdetails.User actual = (org.springframework.security.core.userdetails.User) userDetailsService.loadUserByUsername(username);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserLoginSpecification.class));
    }
}