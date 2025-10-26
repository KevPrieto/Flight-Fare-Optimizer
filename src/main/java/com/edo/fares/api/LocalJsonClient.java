package com.edo.fares.api;

import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads flight data from embedded JSON inside the JAR/EXE.
 * Works in both development and production builds.
 */
public class LocalJsonClient {

    private static final Logger LOGGER = Logger.getLogger(LocalJsonClient.class.getName());

    public List<Flight> loadFlights(String resourcePath) throws DataLoadException {
        LOGGER.info("Attempting to load flight data: " + resourcePath);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try (InputStream is = getClass().getResourceAsStream("/" + resourcePath)) {
            if (is == null) {
                LOGGER.severe("Resource not found inside classpath: /" + resourcePath);
                throw new DataLoadException("Could not load local JSON data (missing resource)");
            }

            LOGGER.info("Successfully loaded " + resourcePath + " from classpath.");
            return mapper.readValue(is, new TypeReference<List<Flight>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading JSON file: " + resourcePath, e);
            throw new DataLoadException("Could not load local JSON data", e);
        }
    }
}

