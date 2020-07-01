package com.java.chat.controller;

import com.java.chat.dto.impl.ChannelDto;
import com.java.chat.dto.impl.UserDto;
import com.java.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> add(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.add(userDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable long id) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        return new ResponseEntity<>(userService.get(userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable long id) {
        UserDto userDto = new UserDto();
        userDto.setId(id);
        return new ResponseEntity<>(userService.remove(userDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(@RequestBody UserDto userDto,
                                         @PathVariable long id) {
        userDto.setId(id);
        return new ResponseEntity<>(userService.update(userDto), HttpStatus.OK);
    }

    @GetMapping("/join")
    public ResponseEntity<UserDto> join(@RequestParam long userId, @RequestParam long channelId) {
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        ChannelDto channelDto = new ChannelDto();
        channelDto.setId(channelId);
        List<ChannelDto> channels = new ArrayList<>();
        channels.add(channelDto);
        userDto.setChannels(channels);
        return new ResponseEntity<>(userService.join(userDto), HttpStatus.OK);
    }

    @GetMapping("/leave")
    public ResponseEntity<UserDto> leave(@RequestParam long[] ids, @RequestParam long userId) {
        return new ResponseEntity<>(userService.leave(ids, userId), HttpStatus.OK);
    }

}
