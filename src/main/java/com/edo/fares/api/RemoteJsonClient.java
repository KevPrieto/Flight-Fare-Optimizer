package com.edo.fares.api;

import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Fetches flight data from a remote JSON endpoint (e.g. GitHub RAW URL).
 */
public class RemoteJsonClient {

    private static final Logger logger = LoggerFactory.getLogger(RemoteJsonClient.class);
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Loads flights from a remote JSON URL.
     *
     * @param url The full HTTP/HTTPS URL of the JSON file
     * @return List of Flight objects
     * @throws DataLoadException if the data could not be fetched or parsed
     */
    public List<Flight> loadFlights(String url) throws DataLoadException {
        logger.info("Downloading flight data from remote URL: {}", url);
        try (InputStream inputStream = new URL(url).openStream()) {
            return mapper.readValue(inputStream, new TypeReference<List<Flight>>() {});
        } catch (Exception e) {
            logger.error("Failed to load flights from remote URL: {}", url, e);
            throw new DataLoadException("Error fetching data from: " + url, e);
        }
    }
}
