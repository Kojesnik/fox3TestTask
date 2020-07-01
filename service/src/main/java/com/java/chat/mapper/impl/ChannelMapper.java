package com.java.chat.mapper.impl;

import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.mapper.AbstractMapper;
import com.java.chat.model.impl.Channel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ChannelMapper implements AbstractMapper<ChannelDto, Channel> {

    private ModelMapper modelMapper;

    @Autowired
    public ChannelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public Channel toEntity(ChannelDto channelDto) {
        return modelMapper.map(channelDto, Channel.class);
    }

    @Override
    public ChannelDto toDto(Channel channel) {
        return modelMapper.map(channel, ChannelDto.class);
    }

    @Override
    public List<ChannelDto> toDtoList(List<Channel> channels) {
        return channels.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<Channel> toEntityList(List<ChannelDto> channelDtoList) {
        return channelDtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

}
