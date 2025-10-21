package com.edo.fares.model;

import java.time.LocalDate;

public class SearchCriteria {
    private final String origin;
    private final String destination;
    private final LocalDate date;

    public SearchCriteria(String origin, String destination, LocalDate date) {
        this.origin = origin;
        this.destination = destination;
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", date=" + date +
                '}';
    }
}
