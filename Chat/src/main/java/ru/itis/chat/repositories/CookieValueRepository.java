package ru.itis.chat.repositories;

import ru.itis.chat.models.CookieValue;
import ru.itis.chat.models.User;

import java.util.Optional;

public interface CookieValueRepository {
    Optional<CookieValue> findByValue(String value);

    void save(CookieValue cookieValue);
}
