package com.bigone.spring.gogo.entity;

import lombok.Data;

import java.util.List;

@Data
public class AirlineClassInput {
    private String className;
    private int price;
    private int totalSeats;
    private List<SeatInput> seats;
}
