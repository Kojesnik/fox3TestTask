package com.java.chat.service.impl;

import com.java.chat.dto.impl.UserDto;
import com.java.chat.exception.FindException;
import com.java.chat.exception.NotExistsException;
import com.java.chat.mapper.AbstractMapper;
import com.java.chat.model.impl.Channel;
import com.java.chat.model.impl.User;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.repository.UserRepository;
import com.java.chat.service.UserService;
import com.java.chat.specification.impl.channel.ChannelIdSpecification;
import com.java.chat.specification.impl.user.UserIdSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ChannelRepository channelRepository;
    private AbstractMapper<UserDto, User> userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, AbstractMapper<UserDto, User> userMapper, ChannelRepository channelRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.channelRepository = channelRepository;
    }

    @Override
    public UserDto add(UserDto userDto) {
        return userMapper.toDto(userRepository.add(userMapper.toEntity(userDto)));
    }

    @Override
    public UserDto update(UserDto userDto) {
        return userMapper.toDto(userRepository.update(userMapper.toEntity(userDto)));
    }

    @Override
    public Boolean remove(UserDto userDto) {
        return userRepository.remove(userMapper.toEntity(userDto));
    }

    @Override
    public UserDto get(UserDto userDto) {
        List<User> users = userRepository.query(new UserIdSpecification(userDto.getId()));
        if (users.isEmpty()) {
            throw new FindException("No user found");
        }
        return userMapper.toDto(users.get(0));
    }

    @Override
    public UserDto join(UserDto userDto) {
        long channelId = userDto.getChannels().get(0).getId();
        long userId = userDto.getId();
        User user = userRepository.query(new UserIdSpecification(userId)).get(0);
        List<Channel> channels = channelRepository.query(new ChannelIdSpecification(channelId));
        if (channels.isEmpty()) {
            throw new NotExistsException("Such channel doesn't exists");
        }
        user.getChannels().add(channels.get(0));
        return userMapper.toDto(userRepository.update(user));
    }

    @Override
    public UserDto leave(long[] ids, long userId) {
        User user = userRepository.query(new UserIdSpecification(userId)).get(0);
        for (long id: ids) {
            List<Channel> channels = channelRepository.query(new ChannelIdSpecification(id));
            if (!channels.isEmpty()) {
                user.getChannels().remove(channels.get(0));
            }
        }
        return userMapper.toDto(userRepository.update(user));
    }
}
