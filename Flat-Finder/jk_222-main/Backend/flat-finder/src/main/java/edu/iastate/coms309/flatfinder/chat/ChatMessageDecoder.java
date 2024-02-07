package edu.iastate.coms309.flatfinder.chat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ChatMessage decode(String s) throws jakarta.websocket.DecodeException {
        try {
            return objectMapper.readValue(s, ChatMessage.class);
        } catch (Exception e) {
            throw new jakarta.websocket.DecodeException(s, "Unable to decode message", e);
        }
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
    }
}