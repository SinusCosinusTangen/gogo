package com.bigone.spring.gogo.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "flight")
public class Flight {
    @Id
    private Long id;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private ZonedDateTime departureTime;
    private ZonedDateTime arrivalTime;
    private int availableSeats;
    private int totalSeats;

    @org.springframework.data.annotation.Transient
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AirlineClass> classes;
}