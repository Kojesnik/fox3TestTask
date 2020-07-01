package com.java.chat.mapper.impl;

import static org.junit.Assert.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import com.java.chat.dto.impl.UserDto;
import com.java.chat.model.impl.User;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserMapperTest {

    private UserMapper userMapper;

    @Before
    public void setUp() throws Exception {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(PRIVATE);
        userMapper = new UserMapper(mapper);
    }

    @Test
    public void toEntity() {
        UserDto userDto = new UserDto(1l, "kojes","123");
        User expected = new User(1l, "kojes", "123");
        User actual = userMapper.toEntity(userDto);
        assertEquals(expected, actual);
    }

    @Test
    public void toDto() {
        User user = new User(1l, "kojes", "123");
        UserDto expected = new UserDto(1l, "kojes","123");
        UserDto actual = userMapper.toDto(user);
        actual.setChannels(null);
        assertEquals(expected, actual);
    }

    @Test
    public void toDtoList() {
        List<User> users = new ArrayList<>();
        Collections.addAll(users, new User(1l, "kojes", "123"),
                                    new User(2l, "nik", "321"));
        List<UserDto> expected = new ArrayList<>();
        Collections.addAll(expected, new UserDto(1l, "kojes","123"),
                                        new UserDto(2l, "nik","321"));
        List<UserDto> actual = userMapper.toDtoList(users);
        for (UserDto userDto: actual) {
            userDto.setChannels(null);
        }
        assertEquals(expected, actual);
    }

    @Test
    public void toEntityList() {
        List<UserDto> userDtos = new ArrayList<>();
        Collections.addAll(userDtos, new UserDto(1l, "kojes","123"),
                                        new UserDto(2l, "nik","321"));
        List<User> expected = new ArrayList<>();
        Collections.addAll(expected, new User(1l, "kojes", "123"),
                                        new User(2l, "nik", "321"));
        List<User> actual = userMapper.toEntityList(userDtos);
        assertEquals(expected, actual);
    }
}