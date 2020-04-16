package ru.itis.chat.services;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.itis.chat.dto.RegistrationDto;
import ru.itis.chat.models.User;
import ru.itis.chat.repositories.UserRepository;


import java.util.concurrent.ExecutorService;

@Component
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ExecutorService threadPool;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void registration(RegistrationDto form) {
        String rawPassword = form.getPassword();
        String hashPassword = passwordEncoder.encode(rawPassword);
        User user = User.builder()
                .login(form.getLogin())
                .password(hashPassword)
                .build();

        usersRepository.save(user);
    }
}
