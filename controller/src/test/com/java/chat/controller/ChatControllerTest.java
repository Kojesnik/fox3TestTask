package com.java.chat.controller;

import com.java.chat.dto.impl.ChatMessage;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import java.lang.reflect.Type;

public class ChatControllerTest {

    @Test
    public void givenValidSession_whenConnected_SendsMessage() {
        StompSession mockSession = Mockito.mock(StompSession.class);
        StompHeaders mockHeader = Mockito.mock(StompHeaders.class);
        MyStompSessionHandler sessionHandler = new MyStompSessionHandler();
        sessionHandler.afterConnected(mockSession, mockHeader);
        Mockito.verify(mockSession).subscribe("/channel/channel1", sessionHandler);
        Mockito.verify(mockSession).send(Mockito.anyString(), Mockito.anyObject());
    }

    private class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            session.subscribe("/channel/channel1", this);
            session.send("/chat/sendMessage", getSampleMessage());
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            ChatMessage msg = (ChatMessage) payload;
        }

        private ChatMessage getSampleMessage() {
            ChatMessage msg = new ChatMessage("some content", "kojes", "channel1");
            return msg;
        }
    }

}