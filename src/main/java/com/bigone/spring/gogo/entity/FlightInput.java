package com.bigone.spring.gogo.entity;

import lombok.Data;

import java.util.List;

@Data
public class FlightInput {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private int totalSeats;
    private List<AirlineClassInput> classes;
}
