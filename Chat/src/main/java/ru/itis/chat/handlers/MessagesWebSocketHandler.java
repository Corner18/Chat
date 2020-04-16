package ru.itis.chat.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.services.MessageService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MessagesWebSocketHandler extends TextWebSocketHandler {


    static private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    private static final Map<String, List<WebSocketSession>> sessionz = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageService messageService;


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {

        MessageDto messageDto = objectMapper.readValue(((String) message.getPayload()), MessageDto.class);

        if (!sessionz.containsKey(messageDto.getPageId())) {
            List<WebSocketSession> webSocketSessions = new ArrayList<>();
            webSocketSessions.add(session);
            sessionz.put(messageDto.getPageId(), webSocketSessions);
        } else if (!sessionz.get(messageDto.getPageId()).contains(session)){
            sessionz.get(messageDto.getPageId()).add(session);
        }

        messageService.save(messageDto);

        for (WebSocketSession currentSession : sessionz.get(messageDto.getPageId())) {
            currentSession.sendMessage(message);

        }

      /*  for (WebSocketSession webSocketSession : sessions) {
            if ((webSocketSession.getPrincipal().getName().equals(messageDto.getReceiver()) && webSocketSession.isOpen()) ||
                    (webSocketSession.getPrincipal().getName().equals(messageDto.getSender()) && webSocketSession.isOpen())) {
                webSocketSession.sendMessage(message);
            } else if (!webSocketSession.isOpen()) {
                sessions.remove(webSocketSession);
            }
        }
        messageService.save(messageDto);

       */
    }


   /* @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    */
}


