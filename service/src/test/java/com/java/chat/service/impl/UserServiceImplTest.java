package com.java.chat.service.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.dto.impl.UserDto;
import com.java.chat.exception.FindException;
import com.java.chat.exception.NotExistsException;
import com.java.chat.mapper.impl.UserMapper;
import com.java.chat.model.impl.Channel;
import com.java.chat.model.impl.User;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.repository.UserRepository;
import com.java.chat.repository.impl.ChannelRepositoryImpl;
import com.java.chat.repository.impl.UserRepositoryImpl;
import com.java.chat.service.UserService;
import com.java.chat.specification.impl.channel.ChannelIdSpecification;
import com.java.chat.specification.impl.user.UserIdSpecification;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserServiceImplTest {

    private UserService userService;
    private UserMapper userMapper;
    private UserRepository userRepository = mock(UserRepositoryImpl.class);
    private ChannelRepository channelRepository = mock(ChannelRepositoryImpl.class);

    @Before
    public void setUp() throws Exception {
        userMapper = new UserMapper(new ModelMapper());
        userService = new UserServiceImpl(userRepository, userMapper, channelRepository);
    }

    @Test
    public void add() {
        User user = new User(1l, "kojes", "123");
        UserDto expected = new UserDto(1l, "kojes", "123");
        when(userRepository.add(user)).thenReturn(user);
        UserDto actual = userService.add(userMapper.toDto(user));
        actual.setChannels(null);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).add(user);
    }

    @Test
    public void update() {
        User user = new User(1l, "kojes", "123");
        UserDto expected = new UserDto(1l, "kojes", "123");
        when(userRepository.update(user)).thenReturn(user);
        UserDto actual = userService.update(userMapper.toDto(user));
        actual.setChannels(null);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).update(user);
    }

    @Test
    public void remove() {
        User user = new User(1l, "kojes", "123");
        Boolean expected = true;
        when(userRepository.remove(user)).thenReturn(true);
        Boolean actual = userService.remove(userMapper.toDto(user));
        assertEquals(expected, actual);
        verify(userRepository, times(1)).remove(user);
    }

    @Test
    public void get() {
        User user = new User(1l, "kojes", "123");
        List<User> users = new ArrayList();
        users.add(user);
        when(userRepository.query(any(UserIdSpecification.class))).thenReturn(users);
        UserDto expected = new UserDto(1l, "kojes", "123");
        UserDto actual = userService.get(userMapper.toDto(user));
        actual.setChannels(null);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserIdSpecification.class));
    }

    @Test(expected = FindException.class)
    public void getWithException() {
        User user = new User(1l, "kojes", "123");
        List<User> users = new ArrayList();
        when(userRepository.query(any(UserIdSpecification.class))).thenReturn(users);
        UserDto expected = new UserDto(1l, "kojes", "123");
        UserDto actual = userService.get(userMapper.toDto(user));
        actual.setChannels(null);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserIdSpecification.class));
    }

    @Test
    public void join() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(1l);
        UserDto userDto = new UserDto();
        userDto.setId(1l);
        userDto.setChannels(Arrays.asList(channelDto));
        User user = new User(1l, "kojes", "123");
        List<Channel> channels = Arrays.asList(new Channel(1l, "java"));
        when(userRepository.query(any(UserIdSpecification.class))).thenReturn(Arrays.asList(user));
        when(channelRepository.query(any(ChannelIdSpecification.class))).thenReturn(channels);
        when(userRepository.update(user)).thenReturn(user);
        UserDto expected = new UserDto(1l, "kojes", "123");
        expected.setChannels(Arrays.asList(new ChannelDto(1l, "java")));
        UserDto actual = userService.join(userDto);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserIdSpecification.class));
        verify(userRepository, times(1)).update(user);
        verify(channelRepository, times(1)).query(any(ChannelIdSpecification.class));
    }

    @Test(expected = NotExistsException.class)
    public void joinWithException() {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(1l);
        UserDto userDto = new UserDto();
        userDto.setId(1l);
        userDto.setChannels(Arrays.asList(channelDto));
        User user = new User(1l, "kojes", "123");
        List<Channel> channels = new ArrayList<>();
        when(userRepository.query(any(UserIdSpecification.class))).thenReturn(Arrays.asList(user));
        when(channelRepository.query(any(ChannelIdSpecification.class))).thenReturn(channels);
        when(userRepository.update(user)).thenReturn(user);
        UserDto expected = new UserDto(1l, "kojes", "123");
        expected.setChannels(Arrays.asList(new ChannelDto(1l, "java")));
        UserDto actual = userService.join(userDto);
        assertEquals(expected, actual);
        verify(userRepository, times(1)).query(any(UserIdSpecification.class));
        verify(userRepository, times(1)).update(user);
        verify(channelRepository, times(1)).query(any(ChannelIdSpecification.class));
    }

    @Test
    public void leave() {
        long userId = 1;
        long[] ids = {1};
        User user = new User(1l, "kojes", "123");
        List<Channel> channels = new ArrayList<>();
        channels.add(new Channel(1l, "java"));
        user.setChannels(channels);
        when(userRepository.query(any(UserIdSpecification.class))).thenReturn(Arrays.asList(user));
        when(channelRepository.query(any(ChannelIdSpecification.class))).thenReturn(Arrays.asList(new Channel(1l, "java")));
        when(userRepository.update(user)).thenReturn(user);
        UserDto expected = new UserDto(1l, "kojes", "123");
        expected.setChannels(new ArrayList<>());
        UserDto actual = userService.leave(ids, userId);
        assertEquals(expected, actual);
    }
}