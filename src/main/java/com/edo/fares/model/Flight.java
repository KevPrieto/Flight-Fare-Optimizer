package com.edo.fares.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.Objects;

public class Flight {

    // Acepta "code" y también "flightNumber" (y algunos alias comunes)
    @JsonProperty("code")
    @JsonAlias({"flightNumber", "flight_code", "id"})
    private String code;

    private String airline;
    private String origin;
    private String destination;

    // Asegura parseo de "yyyy-MM-dd"
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    private double price;

    // Jackson necesita ctor vacío
    public Flight() { }

    public Flight(String code, String airline, String origin, String destination, LocalDate date, double price) {
        this.code = code;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
    }

    // === Getters/Setters ===
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Conveniencia: por si en algún sitio usas flightNumber
    public String getFlightNumber() {
        return code;
    }

    public void setFlightNumber(String flightNumber) {
        this.code = flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // === equals/hashCode/toString opcionalmente útiles ===
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return Objects.equals(code, flight.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "code='" + code + '\'' +
                ", airline='" + airline + '\'' +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
