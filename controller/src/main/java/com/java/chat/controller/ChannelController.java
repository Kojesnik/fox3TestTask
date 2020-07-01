package com.java.chat.controller;

import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.dto.impl.UserDto;
import com.java.chat.service.ChannelService;
import com.java.chat.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/channel")
public class ChannelController {

    private ChannelService channelService;
    private UserService userService;

    @Autowired
    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ChannelDto> add(@RequestBody ChannelDto channelDto, @RequestParam long createrId) {
        UserDto userDto = new UserDto();
        userDto.setId(createrId);
        List<ChannelDto> channels = new ArrayList<>();
        channelDto = channelService.add(channelDto);
        channels.add(channelDto);
        userDto.setChannels(channels);
        userService.join(userDto);
        return new ResponseEntity<>(channelDto, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> get(@PathVariable long id) {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(id);
        return new ResponseEntity<>(channelService.get(channelDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable long id) {
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(id);
        return new ResponseEntity<>(channelService.remove(channelDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChannelDto> update(@RequestBody ChannelDto channelDto,
                                          @PathVariable long id) {
        channelDto.setId(id);
        return new ResponseEntity<>(channelService.update(channelDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ChannelDto>> getAll() {
        return new ResponseEntity<>(channelService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/id")
    public ResponseEntity<List<Integer>> getAllId() {
        return new ResponseEntity<>(channelService.getAllId(), HttpStatus.OK);
    }

}
