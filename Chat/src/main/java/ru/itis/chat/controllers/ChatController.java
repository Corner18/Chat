package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.dto.MessageDto;
import ru.itis.chat.models.Message;
import ru.itis.chat.models.User;
import ru.itis.chat.services.MessageService;
import ru.itis.chat.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ChatController {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/chat/{receiver}")
    public ModelAndView getChatPage(@PathVariable("receiver") String receiver, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String cookie = null;
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("AUTH")) {
                    cookie = c.getValue();
                    break;
                }
            }
            if (cookie != null) {
                User user = userService.getUserByCookie(cookie);
                Map<String, Object> model = new HashMap<>();
                model.put("user", user);
                model.put("receiver", receiver);
                List<MessageDto> messages = messageService.getDialogue(user.getLogin(), receiver);
                model.put("messages", messages);
                if(messages.isEmpty()){
                    model.put("pageId", UUID.randomUUID().toString());
                } else {
                    model.put("pageId", messages.get(0).getPageId());
                }
                return new ModelAndView("chat", model);
            }
        }
        return new ModelAndView("redirect:/login");
    }
}
