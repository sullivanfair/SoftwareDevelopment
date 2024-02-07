package edu.iastate.coms309.flatfinder.chat;

import edu.iastate.coms309.flatfinder.users.user.User;
import edu.iastate.coms309.flatfinder.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chats")
public class ChatController {
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/messages/{chatRoomName}")
    public ResponseEntity<List<ChatMessage>> getMessagesByChatRoomName(@PathVariable String chatRoomName) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
        if (chatRoom != null) {
            List<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomAndType(chatRoom, ChatMessage.MessageType.CHAT);
            return ResponseEntity.ok(chatMessages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/participants/{chatRoomName}")
    public ResponseEntity<List<User>> getParticipantsByChatRoomName(@PathVariable String chatRoomName) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
        if (chatRoom != null) {
            List<User> participants = chatRoom.getChatRoomParticipants();
            return ResponseEntity.ok(participants);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/chatrooms/{userName}")
    public ResponseEntity<List<ChatRoom>> getChatRoomsByUserName(@PathVariable String userName) {
        User user = userRepository.findByUserName(userName).orElse(null);
        if (user != null) {
            List<ChatRoom> chatRooms = user.getChatRooms();
            return ResponseEntity.ok(chatRooms);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/chatrooms")
    public ResponseEntity<ChatRoom> createChatRoom(@RequestBody Map<String, String> requestBody) {
        String chatRoomName = requestBody.get("chatRoomName");
        if (chatRoomName != null) {
            ChatRoom chatRoom = new ChatRoom(chatRoomName, new ArrayList<>()); // Create an empty chat room
            ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
            return ResponseEntity.ok(savedChatRoom);
        } else {
            return ResponseEntity.badRequest().build(); // Handle the case when chatRoomName is missing in the JSON body
        }
    }

    @PutMapping("/chatrooms/{chatRoomId}")
    public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable Integer chatRoomId, @RequestBody ChatRoom updatedChatRoom) {
        ChatRoom existingChatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
        if (existingChatRoom != null) {
            existingChatRoom.setChatRoomName(updatedChatRoom.getChatRoomName());
            ChatRoom savedChatRoom = chatRoomRepository.save(existingChatRoom);
            return ResponseEntity.ok(savedChatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/chatrooms/{chatRoomName}/participants")
    public ResponseEntity<ChatRoom> addParticipantToChatRoom(@PathVariable String chatRoomName, @RequestBody User participant) {
        ChatRoom existingChatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
        User existingUser = userRepository.findByUserName(participant.getUserName()).orElse(null);

        if (existingChatRoom != null && existingUser != null) {
            if (!existingChatRoom.getChatRoomParticipants().contains(existingUser)) {
                existingChatRoom.getChatRoomParticipants().add(existingUser);
                chatRoomRepository.save(existingChatRoom);
            }
            return ResponseEntity.ok(existingChatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/chatrooms/{chatRoomName}/participants/{userName}")
    public ResponseEntity<ChatRoom> removeParticipantFromChatRoom(
            @PathVariable String chatRoomName,
            @PathVariable String userName
    ) {
        ChatRoom existingChatRoom = chatRoomRepository.findByChatRoomName(chatRoomName);
        User existingUser = userRepository.findByUserName(userName).orElse(null);

        if (existingChatRoom != null && existingUser != null) {
            if (existingChatRoom.getChatRoomParticipants().contains(existingUser)) {
                existingChatRoom.getChatRoomParticipants().remove(existingUser);
                chatRoomRepository.save(existingChatRoom);
            }
            return ResponseEntity.ok(existingChatRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/chatrooms/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Integer chatRoomId) {
        chatRoomRepository.deleteById(chatRoomId);
        return ResponseEntity.noContent().build();
    }
}