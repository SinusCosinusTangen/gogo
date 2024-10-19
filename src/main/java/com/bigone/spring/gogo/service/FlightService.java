package com.bigone.spring.gogo.service;

import com.bigone.spring.gogo.entity.Flight;
import com.bigone.spring.gogo.entity.FlightInput;
import reactor.core.publisher.Mono;

public interface FlightService {
    Mono<Flight> createFlight(FlightInput flightInput);
}
