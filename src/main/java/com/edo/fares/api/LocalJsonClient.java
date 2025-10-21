package com.edo.fares.api;

import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalJsonClient {

    private static final Logger LOGGER = Logger.getLogger(LocalJsonClient.class.getName());

    public List<Flight> loadFlights(String resourcePath) throws DataLoadException {
        LOGGER.info("Attempting to load flight data from resource: " + resourcePath);

        ObjectMapper mapper = new ObjectMapper();
        // ✅ Register support for LocalDate, LocalDateTime, etc.
        mapper.registerModule(new JavaTimeModule());

        // Try classpath first
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is != null) {
                LOGGER.info("Loaded " + resourcePath + " from classpath successfully.");
                return mapper.readValue(is, new TypeReference<List<Flight>>() {});
            } else {
                LOGGER.warning("Could not find resource on classpath: " + resourcePath);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Failed to load from classpath: " + resourcePath, e);
        }

        // Fallback: absolute path
        try {
            String fullPath = Paths.get("src", "main", "resources", resourcePath).toString();
            LOGGER.info("Fallback: loading directly from " + fullPath);
            byte[] bytes = Files.readAllBytes(Paths.get(fullPath));
            return mapper.readValue(bytes, new TypeReference<List<Flight>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Completely failed to load local data file: " + resourcePath, e);
            throw new DataLoadException("Could not load local JSON data", e);
        }
    }
}
