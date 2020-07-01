package com.java.chat.service;

import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.dto.impl.UserDto;

import java.util.List;

public interface ChannelService extends EntityService<ChannelDto> {

    List<ChannelDto> getAll();
    List<Integer> getAllId();

}
