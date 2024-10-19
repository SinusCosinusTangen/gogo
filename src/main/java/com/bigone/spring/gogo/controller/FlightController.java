package com.bigone.spring.gogo.controller;

import com.bigone.spring.gogo.entity.Flight;
import com.bigone.spring.gogo.entity.FlightInput;
import com.bigone.spring.gogo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @MutationMapping
    public Mono<Flight> createFlight(@Argument FlightInput flightInput) {
        return flightService.createFlight(flightInput);
    }
}
