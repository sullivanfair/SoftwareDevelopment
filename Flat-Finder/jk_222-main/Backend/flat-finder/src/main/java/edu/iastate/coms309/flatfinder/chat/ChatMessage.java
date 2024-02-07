package edu.iastate.coms309.flatfinder.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.iastate.coms309.flatfinder.users.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private MessageType type;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private User sender;

    @ManyToOne
    @JsonIgnoreProperties("chatMessages")
    private ChatRoom chatRoom;

    public ChatMessage(MessageType type, String content, User sender, ChatRoom chatRoom) {
        this.type = type;
        this.content = content;
        this.sender = sender;
        this.chatRoom = chatRoom;
    }

    public enum MessageType {
        CHAT, JOIN, LEAVE
    }
}