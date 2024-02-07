package edu.iastate.coms309.flatfinder.chat;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {
    ChatRoom findByChatRoomName(String chatRoomName);
}