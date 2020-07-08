package com.java.chat.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import com.java.chat.application.Application;
import com.java.chat.dto.impl.ChatMessage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = Application.class)
public class ChatControllerTest {

    @Value("${local.server.port}")
    private int port;

    private WebSocketStompClient stompClient;
    private StompSession stompSession1;
    private StompSession stompSession2;
    private StompSession stompSession3;
    private StompSession stompSession4;

    @Autowired
    private ChatController chatController;

    @Before
    public void setUp() throws Exception {
        String wsUrl = "ws://localhost:" + port  + "/ws";
        stompClient = createWebSocketClient();
        stompSession1 = stompClient.connect(wsUrl, new MyStompSessionHandler()).get();
        stompSession2 = stompClient.connect(wsUrl, new MyStompSessionHandler()).get();
        stompSession3 = stompClient.connect(wsUrl, new MyStompSessionHandler()).get();
        stompSession4 = stompClient.connect(wsUrl, new MyStompSessionHandler()).get();
    }

    @After
    public void tearDown() throws Exception {
        stompSession1.disconnect();
        stompSession2.disconnect();
        stompSession3.disconnect();
        stompSession4.disconnect();
        stompClient.stop();
    }

    @Test
    public void connectsToSocket() throws Exception {
        Assert.assertTrue(stompSession1.isConnected());
        Assert.assertTrue(stompSession2.isConnected());
        Assert.assertTrue(stompSession3.isConnected());
        Assert.assertTrue(stompSession4.isConnected());
    }

    @Test
    public void receivesMessageFromSubscribedQueue() throws Exception {
        BlockingQueue<ChatMessage> blockingQueue1 = new LinkedBlockingQueue<>();
        BlockingQueue<ChatMessage> blockingQueue2 = new LinkedBlockingQueue<>();
        BlockingQueue<ChatMessage> blockingQueue3 = new LinkedBlockingQueue<>();
        BlockingQueue<ChatMessage> blockingQueue4 = new LinkedBlockingQueue<>();
        stompSession1.subscribe("/channel/public/channel1", new MyStompFrameHandler(blockingQueue1));
        stompSession2.subscribe("/channel/public/channel1", new MyStompFrameHandler(blockingQueue2));
        stompSession3.subscribe("/channel/public/channel1", new MyStompFrameHandler(blockingQueue3));
        stompSession4.subscribe("/channel/public/channel2", new MyStompFrameHandler(blockingQueue4));
        ChatMessage chatMessageToChannel1 = new ChatMessage("Message to channel1", "Misha", "channel1");
        chatController.sendMessage(chatMessageToChannel1);
        ChatMessage messageInClient1Session = blockingQueue1.poll(5, SECONDS);
        ChatMessage messageInClient2Session = blockingQueue2.poll(5, SECONDS);
        ChatMessage messageInClient3Session = blockingQueue3.poll(5, SECONDS);
        ChatMessage messageInClient4Session = blockingQueue4.poll(5, SECONDS);
        ChatMessage expected = new ChatMessage("Message to channel1", "Misha", "channel1");
        assertEquals(expected, messageInClient1Session);
        assertEquals(expected, messageInClient2Session);
        assertEquals(expected, messageInClient3Session);
        assertNull(messageInClient4Session);
        ChatMessage chatMessageToChannel2 = new ChatMessage("Message to channel2", "Nikita", "channel2");
        chatController.sendMessage(chatMessageToChannel2);
        messageInClient4Session = blockingQueue4.poll(5, SECONDS);
        ChatMessage expected2 = new ChatMessage("Message to channel2", "Nikita", "channel2");
        assertEquals(expected2, messageInClient4Session);
        assertNull(blockingQueue1.poll(5, SECONDS));
        assertNull(blockingQueue2.poll(5, SECONDS));
        assertNull(blockingQueue3.poll(5, SECONDS));
    }

    private WebSocketStompClient createWebSocketClient() {
        WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        return stompClient;
    }

    private List<Transport> createTransportClient() {
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        return transports;
    }

    private class MyStompSessionHandler extends StompSessionHandlerAdapter {
        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            super.afterConnected(session, connectedHeaders);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            super.handleException(session, command, headers, payload, exception);
        }
    }

    private class MyStompFrameHandler implements StompFrameHandler {

        private BlockingQueue<ChatMessage> blockingQueue;

        MyStompFrameHandler(BlockingQueue<ChatMessage> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public Type getPayloadType(StompHeaders headers) {
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders headers, Object payload) {
            blockingQueue.offer((ChatMessage) payload);
        }
    }

}