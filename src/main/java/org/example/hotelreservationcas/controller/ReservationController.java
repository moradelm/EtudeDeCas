package org.example.hotelreservationcas.controller;


import org.example.hotelreservationcas.entity.Reservation;
import org.example.hotelreservationcas.repository.ReservationRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    public final ReservationRepository reservationRepository;

    public ReservationController(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @PostMapping
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id , @RequestBody Reservation reservation) {
        Reservation oldReservation = reservationRepository.findById(id).orElse(null);

        if (oldReservation == null) {
            throw new RuntimeException("Reservation with ID " + reservation.getId() + " not found.");
        }

        if (reservation.getCustomerName() != null) {
            oldReservation.setCustomerName(reservation.getCustomerName());
        }
        if (reservation.getDescription() != null) {
            oldReservation.setDescription(reservation.getDescription());
        }
        if (reservation.getCheckIn() != null) {
            oldReservation.setCheckIn(reservation.getCheckIn());
        }
        if (reservation.getCheckOut() != null) {
            oldReservation.setCheckOut(reservation.getCheckOut());
        }

        return reservationRepository.save(oldReservation);
    }

    @DeleteMapping
    public void deleteReservation(@RequestBody Reservation reservation) {
        reservationRepository.delete(reservation);
    }
}
