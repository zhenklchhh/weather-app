package com.logic.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Логин не может быть пустым")
    @Size(min=3, message="Логин должен содержать не меньше 3 символов")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Логин не должен содержать специальные символы")
    @Column(name="login")
    private String login;

    @NotBlank(message="Электронная почта не может быть пустой")
    @Email(message = "Введите действительный адрес электронной почты")
    private String email;

    @Size(min=6, message="Пароль должен содержать не меньше 6 символов")
    @NotBlank(message="Пароль не может быть пустым")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Пароль не должен содержать специальные символы")
    @Column(name="password")
    private String password;
}
