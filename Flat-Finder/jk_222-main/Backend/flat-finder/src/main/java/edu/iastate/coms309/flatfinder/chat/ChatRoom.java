package edu.iastate.coms309.flatfinder.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.iastate.coms309.flatfinder.users.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer chatRoomId;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "chat_room_participants",
            joinColumns = @JoinColumn(name = "chat_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("chatRooms") // Add this to prevent circular reference
    private List<User> chatRoomParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.EAGER)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public ChatRoom(String chatRoomName, List<User> chatRoomParticipants) {
        this.chatRoomName = chatRoomName;
        this.chatRoomParticipants = chatRoomParticipants;
    }
}
