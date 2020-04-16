package ru.itis.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itis.chat.models.CookieValue;
import ru.itis.chat.models.User;
import ru.itis.chat.repositories.CookieValueRepository;
import ru.itis.chat.repositories.UserRepository;


import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieValueRepository cookieValuesRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.getAll();
        return users;
    }


    @Override
    public User getUserByCookie(String cookie) {
        Optional<CookieValue> cookieValueOptional = cookieValuesRepository.findByValue(cookie);
        if (cookieValueOptional.isPresent()) {
            CookieValue cookieValue = cookieValueOptional.get();
            return cookieValue.getUser();
        }
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> userOptional = userRepository.findByLogin(login);
        return userOptional.orElse(null);
    }
}
