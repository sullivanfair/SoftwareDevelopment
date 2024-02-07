package edu.iastate.coms309.flatfinder.chat;

import edu.iastate.coms309.flatfinder.users.user.User;
import edu.iastate.coms309.flatfinder.users.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ServerEndpoint(value = "/chat/{chatRoom}/{userName}", encoders = {ChatMessageEncoder.class}, decoders = {ChatMessageDecoder.class}, configurator = CustomSpringConfigurator.class)
@Component
public class ChatSocket {
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private static final Map<String, Set<Integer>> participants = new ConcurrentHashMap<>(); // Store participant IDs

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @OnOpen
    public void onOpen(Session session, @PathParam("chatRoom") String chatRoom, @PathParam("userName") String userName) {
        String sessionId = generateSessionId(chatRoom, userName);
        sessions.put(sessionId, session);
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        optionalUser.ifPresent(user -> handleUserOnOpen(chatRoom, user));
    }

    @OnClose
    public void onClose(Session session, @PathParam("chatRoom") String chatRoom, @PathParam("userName") String userName) {
        String sessionId = generateSessionId(chatRoom, userName);
        sessions.remove(sessionId);
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        optionalUser.ifPresent(user -> handleUserOnClose(chatRoom, user));
    }

    @OnMessage
    public void onMessage(String messageContent, @PathParam("chatRoom") String chatRoom, @PathParam("userName") String userName) {
        Optional<User> optionalUser = userRepository.findByUserName(userName);
        optionalUser.ifPresent(user -> handleUserMessage(chatRoom, user, messageContent));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.error("Error in WebSocket session: {}", session.getId(), throwable);
    }

    private void handleUserOnOpen(String chatRoom, User user) {
        ChatRoom existingChatRoom = chatRoomRepository.findByChatRoomName(chatRoom);
        if (existingChatRoom == null) {
            existingChatRoom = new ChatRoom(chatRoom, new ArrayList<>());
            chatRoomRepository.save(existingChatRoom);
        }
        // Check if the user is not already a participant before adding them
        if (!participants.containsKey(chatRoom)) {
            participants.put(chatRoom, new HashSet<>());
        }
        Set<Integer> participantIds = participants.get(chatRoom);
        if (!participantIds.contains(user.getUserId())) {
            existingChatRoom.getChatRoomParticipants().add(user);
            chatRoomRepository.save(existingChatRoom);
            participantIds.add(user.getUserId());
            broadcastJoin(chatRoom, user);
        }
    }

    private void handleUserOnClose(String chatRoom, User user) {
        ChatRoom existingChatRoom = chatRoomRepository.findByChatRoomName(chatRoom);
        if (existingChatRoom != null) {
            Set<Integer> participantIds = participants.get(chatRoom);
            if (participantIds.contains(user.getUserId())) {
                existingChatRoom.getChatRoomParticipants().remove(user);
                chatRoomRepository.save(existingChatRoom);
                participantIds.remove(user.getUserId());
                broadcastLeave(chatRoom, user);
            }
        }
    }

    private void handleUserMessage(String chatRoom, User user, String messageContent) {
        ChatRoom existingChatRoom = chatRoomRepository.findByChatRoomName(chatRoom);
        if (existingChatRoom != null) {
            ChatMessage chatMessage = new ChatMessage(ChatMessage.MessageType.CHAT, messageContent, user, existingChatRoom);
            chatMessageRepository.save(chatMessage);
            existingChatRoom.getChatMessages().add(chatMessage);
            chatRoomRepository.save(existingChatRoom);
            broadcast(chatRoom, chatMessage);
        }
    }

    private void broadcast(String chatRoom, ChatMessage chatMessage) {
        sessions.forEach((sessionId, session) -> {
            if (sessionId.startsWith(chatRoom)) {
                try {
                    session.getBasicRemote().sendObject(chatMessage);
                } catch (IOException | EncodeException e) {
                    log.error("Error broadcasting message", e);
                }
            }
        });
    }

    private void broadcastJoin(String chatRoom, User user) {
        ChatMessage joinMessage = new ChatMessage(ChatMessage.MessageType.JOIN, "", user, null);
        broadcast(chatRoom, joinMessage);
    }

    private void broadcastLeave(String chatRoom, User user) {
        ChatMessage leaveMessage = new ChatMessage(ChatMessage.MessageType.LEAVE, "", user, null);
        broadcast(chatRoom, leaveMessage);
    }

    private String generateSessionId(String chatRoom, String userName) {
        return chatRoom + "-" + userName;
    }
}