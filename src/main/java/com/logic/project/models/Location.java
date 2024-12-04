package com.logic.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="locations")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(name="name")
    private String name;

    @NotEmpty
    @Column(name="userId")
    private int userID;

    @NotEmpty
    @Column(name="latitude")
    private double latitude;

    @NotEmpty
    @Column(name="longitude")
    private double longitude;
}
