package com.java.chat.service.impl;

import static org.junit.Assert.*;
import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.exception.AlreadyExistsException;
import com.java.chat.exception.FindException;
import com.java.chat.mapper.impl.ChannelMapper;
import com.java.chat.model.impl.Channel;
import com.java.chat.repository.ChannelRepository;
import com.java.chat.repository.impl.ChannelRepositoryImpl;
import com.java.chat.service.ChannelService;
import com.java.chat.specification.impl.channel.ChannelAllSpecification;
import com.java.chat.specification.impl.channel.ChannelIdSpecification;
import com.java.chat.specification.impl.channel.ChannelNameSpecification;
import static org.mockito.Mockito.*;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import java.util.ArrayList;
import java.util.List;

public class ChannelServiceImplTest {

    private ChannelService channelService;
    private ChannelMapper channelMapper;
    private ChannelRepository channelRepository = mock(ChannelRepositoryImpl.class);

    @Before
    public void setUp() throws Exception {
        channelMapper = new ChannelMapper(new ModelMapper());
        channelService = new ChannelServiceImpl(channelRepository, channelMapper);
    }

    @Test
    public void add() {
        Channel channel = new Channel(1l, "java");
        ChannelDto expected = new ChannelDto(1l, "java");
        when(channelRepository.add(channel)).thenReturn(channel);
        ChannelDto actual = channelService.add(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).add(channel);
    }

    @Test(expected = AlreadyExistsException.class)
    public void addWithException() {
        Channel channel = new Channel(1l, "java");
        ChannelDto expected = new ChannelDto(1l, "java");
        List<Channel> channels = new ArrayList<>();
        channels.add(channel);
        when(channelRepository.add(channel)).thenReturn(channel);
        when(channelRepository.query(any(ChannelNameSpecification.class))).thenReturn(channels);
        ChannelDto actual = channelService.add(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelNameSpecification.class));
    }

    @Test
    public void update() {
        Channel channel = new Channel(1l, "java");
        ChannelDto expected = new ChannelDto(1l, "java");
        when(channelRepository.update(channel)).thenReturn(channel);
        ChannelDto actual = channelService.update(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).update(channel);
    }

    @Test(expected = AlreadyExistsException.class)
    public void updateWithException() {
        Channel channel = new Channel(1l, "java");
        ChannelDto expected = new ChannelDto(1l, "java");
        List<Channel> channels = new ArrayList<>();
        channels.add(channel);
        when(channelRepository.update(channel)).thenReturn(channel);
        when(channelRepository.query(any(ChannelNameSpecification.class))).thenReturn(channels);
        ChannelDto actual = channelService.update(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelNameSpecification.class));
    }

    @Test
    public void remove() {
        Channel channel = new Channel(1l, "java");
        Boolean expected = true;
        when(channelRepository.remove(channel)).thenReturn(true);
        Boolean actual = channelService.remove(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).remove(channel);
    }

    @Test
    public void get() {
        Channel channel = new Channel(1l, "java");
        List<Channel> channels = new ArrayList();
        channels.add(channel);
        when(channelRepository.query(any(ChannelIdSpecification.class))).thenReturn(channels);
        ChannelDto expected = new ChannelDto(1l, "java");
        ChannelDto actual = channelService.get(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelIdSpecification.class));
    }

    @Test(expected = FindException.class)
    public void getWithException() {
        Channel channel = new Channel(1l, "java");
        List<Channel> channels = new ArrayList();
        when(channelRepository.query(any(ChannelIdSpecification.class))).thenReturn(channels);
        ChannelDto expected = new ChannelDto(1l, "java");
        ChannelDto actual = channelService.get(channelMapper.toDto(channel));
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelIdSpecification.class));
    }

    @Test
    public void getAll() {
        Channel channel = new Channel(1l, "java");
        List<Channel> channels = new ArrayList();
        channels.add(channel);
        when(channelRepository.query(any(ChannelAllSpecification.class))).thenReturn(channels);
        List<ChannelDto> expected = new ArrayList<>();
        expected.add(new ChannelDto(1l, "java"));
        List<ChannelDto> actual = channelService.getAll();
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelAllSpecification.class));
    }

    @Test
    public void getAllId() {
        Channel channel = new Channel(1l, "java");
        List<Channel> channels = new ArrayList();
        channels.add(channel);
        when(channelRepository.query(any(ChannelAllSpecification.class))).thenReturn(channels);
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        List<Integer> actual = channelService.getAllId();
        assertEquals(expected, actual);
        verify(channelRepository, times(1)).query(any(ChannelAllSpecification.class));
    }
}