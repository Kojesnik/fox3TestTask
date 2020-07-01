package com.java.chat.service;

import com.java.chat.dto.impl.UserDto;

public interface UserService extends  EntityService<UserDto> {

    UserDto join(UserDto userDto);
    UserDto leave(long[] ids, long userId);

}
