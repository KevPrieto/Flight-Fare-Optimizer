package com.edo.fares.api;

import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads flight data from embedded JSON inside the JAR/EXE.
 * Works in both development and production builds.
 * Ensures UTF-8 decoding to prevent malformed characters (€, etc.)
 */
public class LocalJsonClient {

    private static final Logger LOGGER = Logger.getLogger(LocalJsonClient.class.getName());

    public List<Flight> loadFlights(String resourcePath) throws DataLoadException {
        LOGGER.info("Attempting to load flight data: " + resourcePath);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // ✅ Mejoras de compatibilidad y codificación
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
        mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);

        try (InputStream is = getClass().getResourceAsStream("/" + resourcePath)) {
            if (is == null) {
                LOGGER.severe("Resource not found inside classpath: /" + resourcePath);
                throw new DataLoadException("Could not load local JSON data (missing resource)");
            }

            // ✅ Leer con UTF-8 para evitar caracteres raros (â‚¬, etc.)
            try (InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                List<Flight> flights = mapper.readValue(reader, new TypeReference<List<Flight>>() {});
                LOGGER.info("Successfully loaded " + flights.size() + " flights from " + resourcePath);
                return flights;
            }

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading JSON file: " + resourcePath, e);
            throw new DataLoadException("Could not load local JSON data", e);
        }
    }
}

