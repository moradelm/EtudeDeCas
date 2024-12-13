package org.example.hotelreservationcas.services;


import jakarta.jws.WebService;
import org.example.hotelreservationcas.entity.Reservation;
import org.example.hotelreservationcas.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebService(endpointInterface = "org.example.hotelreservationcas.services.ReservationSoap")
@Service
public class ReservationSoapImpl implements ReservationSoap {


    private final ReservationRepository reservationRepository;

    public ReservationSoapImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public String createReservation(String customerName, String description, String checkIn, String checkOut) {
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Reservation reservation = new Reservation();
            reservation.setCustomerName(customerName);
            reservation.setDescription(description);
            reservation.setCheckIn(sdf.parse(checkIn));
            reservation.setCheckOut(sdf.parse(checkOut));


            reservationRepository.save(reservation);

            return "Reservation created for " + customerName;
        } catch (ParseException e) {
            return "Error parsing dates: " + e.getMessage();
        }
    }

    @Override
    public String getReservation(Long id) {
        return "Reservation details for ID " + id;
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}

