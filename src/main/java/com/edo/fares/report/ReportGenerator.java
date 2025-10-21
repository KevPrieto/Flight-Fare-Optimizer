package com.edo.fares.report;

import com.edo.fares.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportGenerator {

    private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);

    /**
     * Generates a text report with the filtered flights and cheapest flight.
     *
     * @param flights List of flights matching search criteria
     * @param cheapest The cheapest flight found
     */
    public void generateReport(List<Flight> flights, Flight cheapest) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = LocalDateTime.now().format(formatter);
        String reportDirPath = "target/reports";
        String reportPath = reportDirPath + "/flight_report_" + timestamp + ".txt";

        try {
            File reportDir = new File(reportDirPath);
            if (!reportDir.exists()) {
                boolean created = reportDir.mkdirs();
                if (!created) {
                    logger.warn("Could not create reports directory at {}", reportDirPath);
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(reportPath))) {
                writer.write("=== FLIGHT FARE OPTIMIZER REPORT ===\n");
                writer.write("Generated at: " + LocalDateTime.now() + "\n\n");

                writer.write(">> FILTERED FLIGHTS (" + flights.size() + " found):\n");
                for (Flight flight : flights) {
                    writer.write(flight.toString() + "\n");
                }

                writer.write("\n>> CHEAPEST FLIGHT:\n");
                writer.write(cheapest.toString() + "\n");

                logger.info("Report successfully saved at {}", reportPath);
                System.out.println("\n📄 Report saved successfully at: " + reportPath);
            }

        } catch (IOException e) {
            logger.error("Error writing report: {}", e.getMessage());
            System.out.println("⚠️  Could not save report. Check logs for details.");
        }
    }
}
