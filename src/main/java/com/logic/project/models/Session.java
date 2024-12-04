package com.logic.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="sessions")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="userId")
    private int userId;

    @Column(name="expiresAt")
    private LocalDate expiryDate;

}
