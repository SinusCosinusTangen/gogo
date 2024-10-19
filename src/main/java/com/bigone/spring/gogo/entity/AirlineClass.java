package com.bigone.spring.gogo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "airline_class")
public class AirlineClass {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Long flightId;
    private String className;
    private int price;
    private int availableSeats;
    private int totalSeats;

    @org.springframework.data.annotation.Transient
    @OneToMany(mappedBy = "airline_class", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}