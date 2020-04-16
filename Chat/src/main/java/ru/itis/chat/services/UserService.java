package ru.itis.chat.services;


import ru.itis.chat.models.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByCookie(String cookie);

    User getUserByLogin(String login);
}
