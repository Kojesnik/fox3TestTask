package com.java.chat.dto.impl;

import com.java.chat.dto.EntityDto;

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
}
