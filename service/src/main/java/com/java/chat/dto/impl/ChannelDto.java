package com.java.chat.dto.impl;

import com.java.chat.dto.EntityDto;

import java.util.Objects;

public class ChannelDto extends EntityDto {

    private long id;
    private String channelName;

    public ChannelDto(long id, String channelName) {
        this.id = id;
        this.channelName = channelName;
    }

    public ChannelDto(String channelName) {
        this.channelName = channelName;
    }

    public ChannelDto() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChannelDto that = (ChannelDto) o;
        return id == that.id &&
            Objects.equals(channelName, that.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChannelDto{");
        sb.append("id=").append(id);
        sb.append(", channelName='").append(channelName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
