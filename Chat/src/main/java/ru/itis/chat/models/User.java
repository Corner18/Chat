package ru.itis.chat.models;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "itis_user")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@Table(name = "itis_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String login;
    private String password;
}
