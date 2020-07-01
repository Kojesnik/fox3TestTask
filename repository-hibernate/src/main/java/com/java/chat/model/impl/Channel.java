package com.java.chat.model.impl;

import com.java.chat.model.AbstractEntity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "channel")
public class Channel extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "channel_name")
    private String channelName;

    @ManyToMany(mappedBy = "channels")
    List<User> users = new ArrayList<>();

    public Channel(long id, String channelName) {
        this.id = id;
        this.channelName = channelName;
    }

    public Channel(String channelName) {
        this.channelName = channelName;
    }

    public Channel() {}

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return id == channel.id &&
                Objects.equals(channelName, channel.channelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelName);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("id=").append(id);
        sb.append(", channelName='").append(channelName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
