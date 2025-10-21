package com.edo.fares.service;

import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FareServiceTest {

    private FareService service;
    private List<Flight> flights;

    @BeforeEach
    void setUp() {
        service = new FareService();

        flights = Arrays.asList(
                createFlight("UX1023", "Air Europa", "MAD", "BCN", "2025-11-15", 49.99),
                createFlight("IB2710", "Iberia", "MAD", "BCN", "2025-11-15", 59.00),
                createFlight("VY101", "Vueling", "MAD", "BCN", "2025-11-15", 44.50),
                createFlight("VY540", "Vueling", "MAD", "PMI", "2025-11-15", 39.99)
        );
    }

    private Flight createFlight(String number, String airline, String origin, String dest, String date, double price) {
        Flight f = new Flight();
        try {
            java.lang.reflect.Field flightNumber = Flight.class.getDeclaredField("flightNumber");
            java.lang.reflect.Field airlineField = Flight.class.getDeclaredField("airline");
            java.lang.reflect.Field originField = Flight.class.getDeclaredField("origin");
            java.lang.reflect.Field destField = Flight.class.getDeclaredField("destination");
            java.lang.reflect.Field dateField = Flight.class.getDeclaredField("date");
            java.lang.reflect.Field priceField = Flight.class.getDeclaredField("price");

            flightNumber.setAccessible(true);
            airlineField.setAccessible(true);
            originField.setAccessible(true);
            destField.setAccessible(true);
            dateField.setAccessible(true);
            priceField.setAccessible(true);

            flightNumber.set(f, number);
            airlineField.set(f, airline);
            originField.set(f, origin);
            destField.set(f, dest);
            dateField.set(f, LocalDate.parse(date));
            priceField.set(f, price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return f;
    }

    @Test
    void testFilterFlights_ReturnsCorrectResults() {
        SearchCriteria criteria = new SearchCriteria("MAD", "BCN", LocalDate.parse("2025-11-15"));
        List<Flight> filtered = service.filterFlights(flights, criteria);

        assertEquals(3, filtered.size(), "Debe devolver 3 vuelos MAD→BCN");
        assertTrue(filtered.get(0).getPrice() <= filtered.get(1).getPrice(), "Los vuelos deben estar ordenados por precio");
    }

    @Test
    void testFindCheapest_ReturnsLowestPriceFlight() {
        SearchCriteria criteria = new SearchCriteria("MAD", "BCN", LocalDate.parse("2025-11-15"));
        Optional<Flight> cheapest = service.findCheapest(flights, criteria);

        assertTrue(cheapest.isPresent(), "Debe existir un vuelo más barato");
        assertEquals(44.5, cheapest.get().getPrice(), 0.001, "El vuelo más barato cuesta 44.5");
    }

    @Test
    void testFindCheapest_NoResults() {
        SearchCriteria criteria = new SearchCriteria("PMI", "BCN", LocalDate.parse("2025-11-15"));
        Optional<Flight> cheapest = service.findCheapest(flights, criteria);

        assertTrue(cheapest.isEmpty(), "No debe encontrar vuelos para PMI→BCN");
    }
}

