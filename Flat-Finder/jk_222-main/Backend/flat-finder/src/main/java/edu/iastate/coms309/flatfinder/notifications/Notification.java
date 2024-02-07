package edu.iastate.coms309.flatfinder.notifications;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "notifications")
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "content")
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent_date")
    private Date sent = new Date();

    public Notification() {
    }

    public Notification(String email, String content, Date sent) {
        this.email = email;
        this.content = content;
        this.sent = sent;
    }
}