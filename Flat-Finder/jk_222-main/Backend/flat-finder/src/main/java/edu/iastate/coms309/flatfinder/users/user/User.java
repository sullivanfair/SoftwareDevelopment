package edu.iastate.coms309.flatfinder.users.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.coms309.flatfinder.chat.ChatRoom;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_password")
    private String userPassword;

    @Column(name = "account_status")
    private String accountStatus;

    @ManyToMany(mappedBy = "chatRoomParticipants", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ChatRoom> chatRooms = new ArrayList<>();
}