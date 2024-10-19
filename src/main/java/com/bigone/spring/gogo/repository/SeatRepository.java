package com.bigone.spring.gogo.repository;

import com.bigone.spring.gogo.entity.Seat;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends R2dbcRepository<Seat, Long> {
}
