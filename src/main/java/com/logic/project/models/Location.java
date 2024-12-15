package com.logic.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="locations")
@Setter
@Getter
@NoArgsConstructor
public class Location {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="locationname")
    private String name;

    @Column(name="userid")
    private long userID;

    @Column(name="latitude")
    private double latitude;

    @Column(name="longitude")
    private double longitude;

    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(String name, int userID, double latitude, double longitude) {
        this.name = name;
        this.userID = userID;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
