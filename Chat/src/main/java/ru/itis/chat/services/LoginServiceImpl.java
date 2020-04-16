package ru.itis.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.itis.chat.dto.LoginDto;
import ru.itis.chat.models.CookieValue;
import ru.itis.chat.models.User;
import ru.itis.chat.repositories.CookieValueRepository;
import ru.itis.chat.repositories.UserRepository;


import java.util.Optional;
import java.util.UUID;

@Component
public class LoginServiceImpl implements LoginService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookieValueRepository cookieValueRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<String> login(LoginDto loginDto) {

        Optional<User> userOptional = userRepository.findByLogin(loginDto.getLogin());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            String hashPassword = user.getPassword();
            String rawPassword = loginDto.getPassword();
            if (passwordEncoder.matches(rawPassword, hashPassword)) {
                String newCookie = UUID.randomUUID().toString();

                CookieValue cookieValue = CookieValue.builder()
                        .valuee(newCookie)
                        .user(user)
                        .build();

                cookieValueRepository.save(cookieValue);
                return Optional.of(newCookie);
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}


