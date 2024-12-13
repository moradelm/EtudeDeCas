package org.example.hotelreservationcas.services;


import org.example.hotelreservationcas.entity.Reservation;
import org.example.hotelreservationcas.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            existingReservation.setCustomerName(updatedReservation.getCustomerName());
            existingReservation.setDescription(updatedReservation.getDescription());
            existingReservation.setCheckIn(updatedReservation.getCheckIn());
            existingReservation.setCheckOut(updatedReservation.getCheckOut());
            return reservationRepository.save(existingReservation);
        }
        return null;
    }

    public String deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return "Reservation deleted";
        }
        return "Reservation not found!";
    }
}

