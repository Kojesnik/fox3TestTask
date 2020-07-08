package com.java.chat.dto.impl;

import com.java.chat.dto.EntityDto;

import java.util.Objects;

public class ChatMessage extends EntityDto {

    private String content;
    private String sender;
    private String channel;

    public ChatMessage(String content, String sender, String channel) {
        this.content = content;
        this.sender = sender;
        this.channel = channel;
    }

    public ChatMessage() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(content, that.content) &&
            Objects.equals(sender, that.sender) &&
            Objects.equals(channel, that.channel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, sender, channel);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ChatMessage{");
        sb.append("content='").append(content).append('\'');
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", channel='").append(channel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
