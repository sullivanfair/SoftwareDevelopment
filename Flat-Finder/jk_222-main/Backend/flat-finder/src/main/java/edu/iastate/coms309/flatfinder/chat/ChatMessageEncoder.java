package edu.iastate.coms309.flatfinder.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(ChatMessage chatMessage) throws jakarta.websocket.EncodeException {
        try {
            return objectMapper.writeValueAsString(chatMessage);
        } catch (Exception e) {
            throw new jakarta.websocket.EncodeException(chatMessage, "Unable to encode message", e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}