package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.dto.RegistrationDto;
import ru.itis.chat.services.RegistrationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping
    public ModelAndView getRegistrationPage(HttpServletRequest request) {
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
                return new ModelAndView("redirect:/profile");
            }
        }
        return new ModelAndView("registration");
    }

    @PostMapping
    public ModelAndView registration(RegistrationDto form) {
        registrationService.registration(form);
        return new ModelAndView("redirect:/login");
    }
}
