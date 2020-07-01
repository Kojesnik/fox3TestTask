package com.java.chat.dto.impl;

import com.java.chat.dto.EntityDto;
import java.util.List;
import java.util.Objects;

public class UserDto extends EntityDto {

    private long id;
    private String login;
    private String password;
    private List<ChannelDto> channels;

    public UserDto(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto() {}

    public long getId() {
        return id;
    }

    public List<ChannelDto> getChannels() {
        return channels;
    }

    public void setChannels(List<ChannelDto> channels) {
        this.channels = channels;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDto userDto = (UserDto) o;
        return id == userDto.id &&
            Objects.equals(login, userDto.login) &&
            Objects.equals(password, userDto.password) &&
            Objects.equals(channels, userDto.channels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, channels);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDto{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", channels=").append(channels);
        sb.append('}');
        return sb.toString();
    }
}
