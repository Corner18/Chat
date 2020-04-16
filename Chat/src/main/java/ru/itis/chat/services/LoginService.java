package ru.itis.chat.services;



import ru.itis.chat.dto.LoginDto;

import java.util.Optional;

public interface LoginService {
    Optional<String> login(LoginDto loginDto);
}
