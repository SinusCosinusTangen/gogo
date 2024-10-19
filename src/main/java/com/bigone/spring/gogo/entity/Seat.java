package com.bigone.spring.gogo.entity;

import com.bigone.spring.gogo.enumerate.SeatStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "seat")
public class Seat {
    @Id
    private Long id;
    private String seatNumber;
    private SeatStatus seatStatus;

    @ManyToOne
    @JoinColumn(name = "airline_class_id")
    private Long airlineClassId;
}