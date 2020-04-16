package ru.itis.chat.models;

import lombok.*;

import javax.persistence.*;

@Entity(name = "cookie_value")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder()
@Table(name = "cookie_value")
public class CookieValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String valuee;

    @ManyToOne
    @JoinColumn(name = "userr")
    private User user;
}
