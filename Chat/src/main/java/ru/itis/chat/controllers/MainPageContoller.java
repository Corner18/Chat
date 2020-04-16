package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.models.User;
import ru.itis.chat.services.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/main")
public class MainPageContoller {

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView getMainPage(HttpServletRequest request) {
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
                List<User> users = userService.getAllUsers();
                Map<String, Object> model = new HashMap<>();
                model.put("user", user);
                model.put("users", users);
                return new ModelAndView("main", model);
            }
        }
        return new ModelAndView("redirect:/login");
    }
}
