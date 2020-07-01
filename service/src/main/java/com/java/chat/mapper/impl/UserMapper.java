package com.java.chat.mapper.impl;

import com.java.chat.dto.impl.UserDto;
import com.java.chat.mapper.AbstractMapper;
import com.java.chat.model.impl.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements AbstractMapper<UserDto, User> {

    private ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> toDtoList(List<User> users) {
        return users.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntityList(List<UserDto> userDtoList) {
        return userDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
