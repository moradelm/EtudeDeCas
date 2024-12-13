package org.example.hotelreservationcas.controller;

import org.example.hotelreservationcas.entity.Reservation;
import org.example.hotelreservationcas.services.ReservationService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ReservationGraphQL {

    private final ReservationService reservationService;

    public ReservationGraphQL(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @QueryMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @QueryMapping
    public Reservation getReservationById(@Argument Long id) {
        return reservationService.getReservationById(id);
    }


    @MutationMapping
    public Reservation createReservation(
            @Argument String customerName,
            @Argument String description,
            @Argument String checkIn,
            @Argument String checkOut
    ) {
        Reservation reservation = new Reservation();
        reservation.setCustomerName(customerName);
        reservation.setDescription(description);
        reservation.setCheckIn(java.sql.Date.valueOf(checkIn));
        reservation.setCheckOut(java.sql.Date.valueOf(checkOut));
        return reservationService.createReservation(reservation);
    }

    @MutationMapping
    public Reservation updateReservation(
            @Argument Long id,
            @Argument String customerName,
            @Argument String description,
            @Argument String checkIn,
            @Argument String checkOut
    ) {
        Reservation updatedReservation = new Reservation();
        updatedReservation.setCustomerName(customerName);
        updatedReservation.setDescription(description);
        updatedReservation.setCheckIn(java.sql.Date.valueOf(checkIn));
        updatedReservation.setCheckOut(java.sql.Date.valueOf(checkOut));
        return reservationService.updateReservation(id, updatedReservation);
    }

    @MutationMapping
    public String deleteReservation(@Argument Long id) {
        return reservationService.deleteReservation(id);
    }

}
