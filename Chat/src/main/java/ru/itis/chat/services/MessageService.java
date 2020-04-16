package ru.itis.chat.services;



import ru.itis.chat.dto.MessageDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MessageService {
    void save(MessageDto messageDto);
    List<MessageDto> getDialogue(String login1, String login2);
    Set<String> getEmailsForDialoguePage(String login);
}
