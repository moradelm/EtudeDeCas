package org.example.hotelreservationcas.config;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.example.hotelreservationcas.services.ReservationSoapImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class CxfConfig {

    private final Bus bus;
    private final ReservationSoapImpl reservationSoap;


    public CxfConfig(Bus bus, ReservationSoapImpl reservationSoap) {
        this.bus = bus;
        this.reservationSoap = reservationSoap;
    }

    @Bean
    public Endpoint reservationEndpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoap);
        endpoint.publish("/reservation");
        return endpoint;
    }




}
