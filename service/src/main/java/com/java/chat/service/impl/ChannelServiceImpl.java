package com.java.chat.service.impl;

import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.exception.AlreadyExistsException;
import com.java.chat.exception.FindException;
import com.java.chat.mapper.AbstractMapper;
import com.java.chat.model.impl.Channel;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.service.ChannelService;
import com.java.chat.specification.impl.channel.ChannelAllSpecification;
import com.java.chat.specification.impl.channel.ChannelIdSpecification;
import com.java.chat.specification.impl.channel.ChannelNameSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    private ChannelRepository channelRepository;
    private AbstractMapper<ChannelDto, Channel> channelMapper;

    @Autowired
    public ChannelServiceImpl(ChannelRepository channelRepository, AbstractMapper<ChannelDto, Channel> channelMapper) {
        this.channelRepository = channelRepository;
        this.channelMapper = channelMapper;
    }

    @Override
    public ChannelDto add(ChannelDto channelDto) {
        if (isChannelExists(channelDto.getChannelName())) {
            throw new AlreadyExistsException("Such channel already exists");
        }
        return channelMapper.toDto(channelRepository.add(channelMapper.toEntity(channelDto)));
    }

    private boolean isChannelExists(String channelName) {
        return !channelRepository.query(new ChannelNameSpecification(channelName)).isEmpty();
    }

    @Override
    public ChannelDto update(ChannelDto channelDto) {
        if (isChannelExists(channelDto.getChannelName())) {
            throw new AlreadyExistsException("Such channel already exists");
        }
        return channelMapper.toDto(channelRepository.update(channelMapper.toEntity(channelDto)));
    }

    @Override
    public Boolean remove(ChannelDto channelDto) {
        return channelRepository.remove(channelMapper.toEntity(channelDto));
    }

    @Override
    public ChannelDto get(ChannelDto channelDto) {
        List<Channel> channels = channelRepository.query(new ChannelIdSpecification(channelDto.getId()));
        if (channels.isEmpty()) {
            throw new FindException("No channel found");
        }
        return channelMapper.toDto(channels.get(0));
    }

    @Override
    public List<ChannelDto> getAll() {
        return channelMapper.toDtoList(channelRepository.query(new ChannelAllSpecification()));
    }

    @Override
    public List<Integer> getAllId() {
        List<Channel> channels = channelRepository.query(new ChannelAllSpecification());
        List<Integer> channelIdList = new ArrayList<>();
        for (Channel channel: channels) {
            String id = Long.toString(channel.getId());
            channelIdList.add(Integer.parseInt(id));
        }
        return channelIdList;
    }

}
