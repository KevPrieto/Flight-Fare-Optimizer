package com.edo.fares.service;

import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FareServiceTest {

    private FareService service;
    private List<Flight> flights;

    @BeforeEach
    void setUp() {
        service = new FareService();

        flights = Arrays.asList(
                new Flight("UX1023", "Air Europa", "MAD", "BCN", LocalDate.parse("2025-11-15"), 49.99),
                new Flight("IB2710", "Iberia", "MAD", "BCN", LocalDate.parse("2025-11-15"), 59.00),
                new Flight("VY101", "Vueling", "MAD", "BCN", LocalDate.parse("2025-11-15"), 44.50),
                new Flight("VY540", "Vueling", "MAD", "PMI", LocalDate.parse("2025-11-15"), 39.99)
        );
    }

    @Test
    void testFilterFlights_ReturnsCorrectResults() {
        SearchCriteria criteria = new SearchCriteria("MAD", "BCN", LocalDate.parse("2025-11-15"));
        List<Flight> filtered = service.filterFlights(flights, criteria);

        assertEquals(3, filtered.size(), "Debe devolver 3 vuelos MAD→BCN");
        assertTrue(filtered.stream().allMatch(f -> f.getDestination().equals("BCN")), "Todos deben ir a BCN");
    }

    @Test
    void testFindCheapest_ReturnsLowestPriceFlight() {
        SearchCriteria criteria = new SearchCriteria("MAD", "BCN", LocalDate.parse("2025-11-15"));

        List<Flight> filtered = service.filterFlights(flights, criteria);
        Flight cheapest = service.findCheapestFlight(filtered);

        assertNotNull(cheapest, "Debe existir un vuelo más barato");
        assertEquals(44.5, cheapest.getPrice(), 0.001, "El vuelo más barato cuesta 44.5");
    }

    @Test
    void testFindCheapest_NoResults() {
        SearchCriteria criteria = new SearchCriteria("PMI", "BCN", LocalDate.parse("2025-11-15"));
        List<Flight> filtered = service.filterFlights(flights, criteria);
        Flight cheapest = service.findCheapestFlight(filtered);

        assertNull(cheapest, "No debe encontrar vuelos para PMI→BCN");
    }
}
