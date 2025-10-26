package com.edo.fares.model;

import java.time.LocalDate;

public class Flight {

    private String code;          // usado por GUI y ReportGenerator
    private String airline;
    private String origin;
    private String destination;
    private LocalDate date;
    private double price;

    public Flight() {
    }

    public Flight(String code, String airline, String origin, String destination, LocalDate date, double price) {
        this.code = code;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.date = date;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    @Override
    public String toString() {
        return String.format("%s | %s | %s → %s | %s | %.2f€",
                code, airline, origin, destination, date, price);
    }
}
