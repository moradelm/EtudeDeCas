package org.example.hotelreservationcas.services;

import com.app.grpc.stubs.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.hotelreservationcas.entity.Reservation;
import org.example.hotelreservationcas.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.List;

@GrpcService
public class ReservationGrpcServiceImpl extends ReservationGrpcServiceGrpc.ReservationGrpcServiceImplBase {

    @Autowired
    private ReservationRepository reservationRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void getAllReservations(GetAllReservationsRequest request,
                                   StreamObserver<GetAllReservationsResponse> responseObserver) {
        List<Reservation> reservations = reservationRepository.findAll();

        GetAllReservationsResponse.Builder responseBuilder = GetAllReservationsResponse.newBuilder();
        for (Reservation reservation : reservations) {
            responseBuilder.addReservations(mapToGrpcReservation(reservation));
        }

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void getReservationById(GetReservationByIdRequest request,
                                   StreamObserver<GetReservationByIdResponse> responseObserver) {
        Reservation reservation = reservationRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Reservation not found"));

        GetReservationByIdResponse response = GetReservationByIdResponse.newBuilder()
                .setReservation(mapToGrpcReservation(reservation))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveReservation(SaveReservationRequest request,
                                StreamObserver<SaveReservationResponse> responseObserver) {
        try {
            com.app.grpc.stubs.Reservation grpcReservation = request.getReservation();

            Reservation reservation = new Reservation();
            reservation.setCustomerName(grpcReservation.getCustomerName());
            reservation.setDescription(grpcReservation.getDescription());
            reservation.setCheckIn(dateFormat.parse(grpcReservation.getCheckIn()));
            reservation.setCheckOut(dateFormat.parse(grpcReservation.getCheckOut()));

            Reservation savedReservation = reservationRepository.save(reservation);

            SaveReservationResponse response = SaveReservationResponse.newBuilder()
                    .setReservation(mapToGrpcReservation(savedReservation))
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (ParseException e) {
            responseObserver.onError(e);
        }
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request,
                                  StreamObserver<DeleteReservationResponse> responseObserver) {
        try {
            reservationRepository.deleteById(request.getId());
            DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Reservation deleted successfully")
                    .build();
            responseObserver.onNext(response);
        } catch (Exception e) {
            DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Failed to delete reservation: " + e.getMessage())
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    private com.app.grpc.stubs.Reservation mapToGrpcReservation(Reservation reservation) {
        return com.app.grpc.stubs.Reservation.newBuilder()
                .setId(reservation.getId())
                .setCustomerName(reservation.getCustomerName())
                .setDescription(reservation.getDescription())
                .setCheckIn(dateFormat.format(reservation.getCheckIn()))
                .setCheckOut(dateFormat.format(reservation.getCheckOut()))
                .build();
    }
}