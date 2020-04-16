package ru.itis.chat.repositories;

import ru.itis.chat.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByLogin(String login);

    List<User> getAll();

    void save(User user);
}
