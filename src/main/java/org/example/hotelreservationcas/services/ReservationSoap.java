package org.example.hotelreservationcas.services;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.hotelreservationcas.entity.Reservation;

import java.util.List;


@WebService
public interface ReservationSoap {

    @WebMethod
    String createReservation(String customerName, String description, String checkIn, String checkOut);

    @WebMethod
    String getReservation(Long id);

    @WebMethod
    List<Reservation> getAllReservations();
}

