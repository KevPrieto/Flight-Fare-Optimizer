package com.edo.fares;

import com.edo.fares.api.LocalJsonClient;
import com.edo.fares.api.RemoteJsonClient;
import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;
import com.edo.fares.service.FareService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Console version of the Flight Fare Optimizer.
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("=== Flight Fare Optimizer started ===");

        Scanner scanner = new Scanner(System.in);
        FareService fareService = new FareService();

        try {
            System.out.println("Select data source:");
            System.out.println("1. Local file (sample-flights.json)");
            System.out.println("2. Remote URL");
            System.out.print("Option: ");
            int option = Integer.parseInt(scanner.nextLine());

            List<Flight> flights;

            if (option == 1) {
                LocalJsonClient client = new LocalJsonClient();
                flights = client.loadFlights("sample-flights.json");
            } else if (option == 2) {
                System.out.print("Enter remote JSON URL: ");
                String url = scanner.nextLine();
                RemoteJsonClient client = new RemoteJsonClient();
                flights = client.loadFlights(url);
            } else {
                System.out.println("Invalid option. Exiting.");
                return;
            }

            System.out.print("Enter origin: ");
            String origin = scanner.nextLine().toUpperCase();

            System.out.print("Enter destination: ");
            String destination = scanner.nextLine().toUpperCase();

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());

            SearchCriteria criteria = new SearchCriteria(origin, destination, date);
            List<Flight> filtered = fareService.filterFlights(flights, criteria);

            if (filtered.isEmpty()) {
                System.out.println("No flights found for that search.");
            } else {
                Flight cheapest = fareService.findCheapestFlight(filtered);
                System.out.println("\n=== Search Results ===");
                filtered.forEach(System.out::println);
                System.out.println("\nCheapest flight: " + cheapest);
            }

        } catch (DataLoadException e) {
            logger.error("Error loading data: {}", e.getMessage(), e);
            System.out.println("Error loading flight data: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: ", e);
            System.out.println("Unexpected error: " + e.getMessage());
        } finally {
            logger.info("Shutting down Flight Fare Optimizer.");
            scanner.close();
        }
    }
}
