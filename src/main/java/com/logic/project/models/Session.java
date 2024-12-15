package com.logic.project.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="sessions")
@Getter
@Setter
@NoArgsConstructor
public class Session {

    @Id
    @Column(name="id")
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name="userid")
    private long userId;

    @Column(name="expiresat")
    private Instant expiryDate;

}
