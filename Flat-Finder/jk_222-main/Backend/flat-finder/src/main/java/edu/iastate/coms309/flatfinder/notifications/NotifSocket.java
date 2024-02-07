package edu.iastate.coms309.flatfinder.notifications;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Controller
@ServerEndpoint(value = "/notif/{email}")
public class NotifSocket {
    private static NotifRepository notifRepo;
    private static final Map<Session, String> sessionEmailMap = new Hashtable<>();
    private static final Map<String, Session> emailSessionMap = new Hashtable<>();
    private final Logger logger = LoggerFactory.getLogger(NotifSocket.class);

    @Autowired
    public void setNotifRepo(NotifRepository repo) {
        notifRepo = repo;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("email") String email) throws IOException {
        logger.info("Entered into Open");
        sessionEmailMap.put(session, email);
        emailSessionMap.put(email, session);
        sendNotification(email, getNotifHistory(email));
    }

    @OnMessage
    public void onMessage(Session session, String notif) throws IOException {
        logger.info("Entered into message: Got Message: " + notif);
        String email = sessionEmailMap.get(session);
        int space = notif.indexOf(' ');
        String destUsername = notif.substring(0, space);
        Date date = new Date();
        String subContent = notif.substring(space);
        if (subContent.equals("Clear Notification History")) {
            clearNotifHistory(destUsername);
            sendNotification(destUsername, "[HISTORY CLEARED]");
            return;
        }
        String completeContent = "[" + email + "]" + subContent + " [SENT] " + date;
        sendNotification(destUsername, completeContent);
        sendNotification(email, "[SENT]");
        notifRepo.save(new Notification(destUsername, completeContent, date));
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into close");
        String email = sessionEmailMap.get(session);
        sessionEmailMap.remove(session);
        emailSessionMap.remove(email);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.info("Entered into error");
        throwable.printStackTrace();
    }

    public void sendNotification(String email, String notif) {
        try {
            emailSessionMap.get(email).getBasicRemote().sendText(notif);
        } catch (Exception e) {
            logger.info("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String getNotifHistory(String email) {
        List<Notification> notifs = notifRepo.findAll();
        StringBuilder sb = new StringBuilder();
        if (!notifs.isEmpty()) {
            for (Notification notif : notifs) {
                if (notif.getEmail().equals(email)) {
                    sb.append(notif.getContent()).append("\n");
                }
            }
        }
        if (sb.isEmpty()) {
            return "[No Previous Notifications]";
        }
        return sb.toString();
    }

    private void clearNotifHistory(String email) {
        List<Notification> notifs = notifRepo.findAll();
        notifs.removeIf(notif -> notif.getEmail().equals(email));
    }
}