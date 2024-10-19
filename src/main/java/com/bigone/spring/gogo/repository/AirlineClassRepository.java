package com.bigone.spring.gogo.repository;

import com.bigone.spring.gogo.entity.AirlineClass;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineClassRepository extends R2dbcRepository<AirlineClass, Long> {
}
