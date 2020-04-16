package ru.itis.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.models.Message;
import ru.itis.chat.repositories.MessageRepository;
import ru.itis.chat.repositories.UserRepository;


import java.util.*;

@Component
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;


    @Override
    public void save(MessageDto messageDto) {

        Message message = Message.builder()
                .receiver(messageDto.getReceiver())
                .sender(messageDto.getSender())
                .text(messageDto.getText())
                .pageId(messageDto.getPageId())
                .build();
        messageRepository.save(message);
    }

    @Override
    public List<MessageDto> getDialogue(String login1, String login2) {
        List<Message> dialogue = messageRepository.getAllBySender_LoginAndReceiver_Login(login1, login2);
        dialogue.addAll(messageRepository.getAllBySender_LoginAndReceiver_Login(login2,login1));
        dialogue.sort(Comparator.comparingLong(Message::getId));
        return MessageDto.from(dialogue);
    }

    @Override
    public Set<String> getEmailsForDialoguePage(String login) {
        Set<String> logins = new HashSet<>();
        //достали все входящие сообщение
        List<Message> messages = messageRepository.getAllByReceiver_Login(login);
        // теперь достаем все логины отправителей
        for (Message message : messages) {
            logins.add(message.getSender());
        }
        // теперь достаем исходящие сообщения
        List<Message> messages1 = messageRepository.getAllBySender_Login(login);
        // достаем логины получателей
        for (Message message : messages1) {
            logins.add(message.getReceiver());
        }
        return logins;
    }


}
