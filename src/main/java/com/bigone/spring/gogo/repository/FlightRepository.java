package com.bigone.spring.gogo.repository;

import com.bigone.spring.gogo.entity.Flight;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends R2dbcRepository<Flight, Long> {
}
