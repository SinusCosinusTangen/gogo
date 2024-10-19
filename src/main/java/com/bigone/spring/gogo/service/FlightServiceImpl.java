package com.bigone.spring.gogo.service;

import com.bigone.spring.gogo.entity.*;
import com.bigone.spring.gogo.enumerate.SeatStatus;
import com.bigone.spring.gogo.exception.EmptyInputException;
import com.bigone.spring.gogo.repository.AirlineClassRepository;
import com.bigone.spring.gogo.repository.FlightRepository;
import com.bigone.spring.gogo.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final AirlineClassRepository classRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirlineClassRepository classRepository, SeatRepository seatRepository) {
        this.flightRepository = flightRepository;
        this.classRepository = classRepository;
        this.seatRepository = seatRepository;
    }

    public Mono<Flight> createFlight(FlightInput flightInput) {

        try {
            validateInput(flightInput);
        } catch (EmptyInputException | NullPointerException e) {
            return Mono.error(e);
        }

        Flight flight = new Flight();
        flight.setId(generateId());
        flight.setFlightNumber(flightInput.getFlightNumber());
        flight.setAirline(flightInput.getAirline());
        flight.setOrigin(flightInput.getOrigin());
        flight.setDestination(flightInput.getDestination());
        flight.setDepartureTime(convertDate(flightInput.getDepartureTime()));
        flight.setArrivalTime(convertDate(flightInput.getArrivalTime()));
        flight.setAvailableSeats(flightInput.getTotalSeats());
        flight.setTotalSeats(flightInput.getTotalSeats());

        // Save the flight
        return flightRepository.save(flight)
                .flatMap(savedFlight -> {
                    if (flightInput.getClasses() != null) {
                        List<Mono<AirlineClass>> airlineClassMonos = flightInput.getClasses().stream()
                                .map(classInput -> {
                                    AirlineClass airlineClass = new AirlineClass();
                                    airlineClass.setId(generateId());
                                    airlineClass.setFlightId(savedFlight.getId());
                                    airlineClass.setClassName(classInput.getClassName());
                                    airlineClass.setPrice(classInput.getPrice());
                                    airlineClass.setAvailableSeats(classInput.getTotalSeats());
                                    airlineClass.setTotalSeats(classInput.getTotalSeats());

                                    // Save airline classes
                                    return classRepository.save(airlineClass)
                                            .flatMap(savedAirlineClass -> {
                                                if (classInput.getSeats() != null) {
                                                    return Flux.fromIterable(classInput.getSeats())
                                                            .flatMap(seatInput -> {
                                                                Seat seat = new Seat();
                                                                seat.setId(generateId());
                                                                seat.setSeatNumber(seatInput.getSeatNumber());
                                                                seat.setSeatStatus(SeatStatus.AVAILABLE);
                                                                seat.setAirlineClassId(savedAirlineClass.getId());
                                                                // Save seats
                                                                return seatRepository.save(seat);
                                                            })
                                                            .collectList()
                                                            .map(seats -> {
                                                                savedAirlineClass.setSeats(seats);
                                                                return savedAirlineClass;
                                                            })
                                                            .then(Mono.just(savedAirlineClass));
                                                }
                                                return Mono.just(savedAirlineClass);
                                            });
                                })
                                .toList();

                        return Flux.concat(Flux.fromIterable(airlineClassMonos))
                                .collectList()
                                .doOnNext(savedFlight::setClasses)
                                .then(Mono.just(savedFlight));
                    }
                    return Mono.just(savedFlight);
                });
    }


    private Long generateId() {
        Random random = new Random();
        Long randomFactor = random.nextLong(1,1000);
        Long timeNow = System.currentTimeMillis();

        return randomFactor + timeNow;
    }

    private ZonedDateTime convertDate(String inputDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);

        return ZonedDateTime.parse(inputDate, formatter);
    }

    private boolean validateInput(Object obj) throws EmptyInputException {
        if (obj == null) {
            return false;
        }

        Class<?> clazz = obj.getClass();

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);

                if (value == null) {
                    throw new NullPointerException("Null value detected: %s".formatted(field.getName()));
                }

                if (value instanceof String && ((String) value).isEmpty()) {
                    throw new EmptyInputException("Empty value detected: %s".formatted(field.getName()));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
