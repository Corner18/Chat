package ru.itis.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.services.LoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping
    public ModelAndView getLoginPage(HttpServletRequest request) {
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
                return new ModelAndView("redirect:/main");
            }
        }
        return new ModelAndView("login");
    }

    @PostMapping
    public ModelAndView login(HttpServletResponse response, LoginDto loginDto) throws IOException {
        Optional<String> cookieCandidate = loginService.login(loginDto);
        if (cookieCandidate.isPresent()) {
            Cookie cookie = new Cookie("AUTH", cookieCandidate.get());
            response.addCookie(cookie);
            return new ModelAndView("redirect:/main");
        } else {
            return new ModelAndView("redirect:/login");
        }

    }
}
