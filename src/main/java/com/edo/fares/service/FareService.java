package com.edo.fares.service;

import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FareService {

    /**
     * Filtra los vuelos según los criterios de búsqueda.
     */
    public List<Flight> filterFlights(List<Flight> flights, SearchCriteria criteria) {
        return flights.stream()
                .filter(f -> f.getOrigin().equalsIgnoreCase(criteria.getOrigin()))
                .filter(f -> f.getDestination().equalsIgnoreCase(criteria.getDestination()))
                .filter(f -> f.getDate().equals(criteria.getDate()))
                .collect(Collectors.toList());
    }

    /**
     * Encuentra el vuelo más barato en la lista filtrada.
     */
    public Flight findCheapestFlight(List<Flight> flights) {
        return flights.stream()
                .min(Comparator.comparingDouble(Flight::getPrice))
                .orElse(null);
    }
}
