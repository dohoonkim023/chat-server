package com.example.sockettest.domain;

import com.example.sockettest.service.ChatService;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

@Getter
public class ChatRoom {

    private final String roomId;
    private final Set<WebSocketSession> sessions = new HashSet<>();

    @Builder
    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void handlerActions(WebSocketSession session, ChatMessage chatMessage,
        ChatService chatService) throws IOException {
        if (chatMessage.getType().equals(ChatMessage.MessageType.LEAVE)) {
            if (sessions.contains(session)) {
                sessions.remove(session);
                session.close();
            }
            chatMessage.setMessage(chatMessage.getSender() + "님이 나갔습니다.");
            sendMessage(chatMessage, chatService);
            return;
        }

        if (chatMessage.getType().equals(ChatMessage.MessageType.ENTER)) {
            sessions.add(session);
            chatMessage.setMessage(chatMessage.getSender() + "님이 입장했습니다.");
        }
        sendMessage(chatMessage, chatService);
    }

    private <T> void sendMessage(T message, ChatService chatService) {
        sessions.parallelStream()
            .forEach(session -> chatService.sendMessage(session, message));
    }
}
