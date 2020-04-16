package ru.itis.chat.repositories;

import ru.itis.chat.models.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> getAllByReceiver_Login(String receiver);

    List<Message> getAllBySender_Login(String sender);

    List<Message> getAllBySender_LoginAndReceiver_Login(String senderEmail, String receiverEmail);

    void save(Message message);
}
