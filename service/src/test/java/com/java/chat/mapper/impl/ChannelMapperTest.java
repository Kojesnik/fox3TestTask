package com.java.chat.mapper.impl;

import static org.junit.Assert.*;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.model.impl.Channel;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelMapperTest {

    private ChannelMapper channelMapper;

    @Before
    public void setUp() throws Exception {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
            .setMatchingStrategy(MatchingStrategies.LOOSE)
            .setFieldMatchingEnabled(true)
            .setSkipNullEnabled(true)
            .setFieldAccessLevel(PRIVATE);
        channelMapper = new ChannelMapper(mapper);
    }

    @Test
    public void toEntity() {
        ChannelDto channelDto = new ChannelDto(1l, "java");
        Channel expected = new Channel(1l, "java");
        Channel actual = channelMapper.toEntity(channelDto);
        assertEquals(expected, actual);
    }

    @Test
    public void toDto() {
        Channel channel = new Channel(1L, "java");
        ChannelDto expected = new ChannelDto(1L, "java");
        ChannelDto actual = channelMapper.toDto(channel);
        assertEquals(expected, actual);
    }

    @Test
    public void toDtoList() {
        List<Channel> channels = new ArrayList<>();
        Collections.addAll(channels, new Channel(1L, "java"), new Channel(2L, "fox3"));
        List<ChannelDto> expected = new ArrayList<>();
        Collections.addAll(expected, new ChannelDto(1L, "java"), new ChannelDto(2L, "fox3"));
        List<ChannelDto> actual = channelMapper.toDtoList(channels);
        assertEquals(expected, actual);
    }

    @Test
    public void toEntityList() {
        List<ChannelDto> channelDtos = new ArrayList<>();
        Collections.addAll(channelDtos, new ChannelDto(1L, "java"), new ChannelDto(2L, "fox3"));
        List<Channel> expected = new ArrayList<>();
        Collections.addAll(expected, new Channel(1L, "java"), new Channel(2L, "fox3"));
        List<Channel> actual = channelMapper.toEntityList(channelDtos);
        assertEquals(expected, actual);
    }
}