package ru.itis.chat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.itis.chat.handlers.AuthHandshakeHandler;
import ru.itis.chat.handlers.MessagesWebSocketHandler;


@Configuration
@EnableAspectJAutoProxy
@Component
@EnableWebSocket
@ComponentScan({"ru.itis.chat"})
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Autowired
    private MessagesWebSocketHandler messagesWebSocketHandler;

    @Autowired
    private AuthHandshakeHandler authHandshakeHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(messagesWebSocketHandler, "/socket")
                .setHandshakeHandler(authHandshakeHandler)
                .withSockJS();
    }
}