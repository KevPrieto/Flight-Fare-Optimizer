package com.edo.fares.gui;

import com.edo.fares.api.LocalJsonClient;
import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;
import com.edo.fares.service.FareService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FlightSearchController {

    // UI controls (must match fx:id in FXML)
    @FXML private TextField txtOrigin;
    @FXML private TextField txtDestination;
    @FXML private DatePicker dpDate;
    @FXML private TableView<Flight> tblFlights;
    @FXML private TableColumn<Flight, String> colCode;
    @FXML private TableColumn<Flight, String> colAirline;
    @FXML private TableColumn<Flight, String> colOrigin;
    @FXML private TableColumn<Flight, String> colDestination;
    @FXML private TableColumn<Flight, LocalDate> colDate;
    @FXML private TableColumn<Flight, Double> colPrice;
    @FXML private Label lblStatus;

    private final ObservableList<Flight> backingData = FXCollections.observableArrayList();
    private final FareService fareService = new FareService();

    // Keep all flights loaded (used for filtering)
    private List<Flight> allFlights = new ArrayList<>();

    // === Called automatically by FXMLLoader after @FXML fields are injected ===
    @FXML
    private void initialize() {
        // Make sure Locale uses dot as decimal separator consistently in logs/format (optional)
        Locale.setDefault(Locale.US);

        // Table column bindings — names MUST match your Flight getters (getCode, getAirline, ...)
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set table data source
        tblFlights.setItems(backingData);

        // Load initial dataset from classpath
        // Make sure you have: src/main/resources/sample-flights.json
        // (Maven copies it to the classpath as /sample-flights.json)
        try {
            LocalJsonClient local = new LocalJsonClient();
            allFlights = local.loadFlights("sample-flights.json"); // classpath resource
            backingData.setAll(allFlights);
            lblStatus.setText("Loaded " + allFlights.size() + " flights from sample-flights.json");
        } catch (DataLoadException e) {
            showError("Failed to load local sample data", e.getMessage());
            lblStatus.setText("Load error. Check logs.");
        }

        // Optional: sort by price ascending by default
        colPrice.setSortType(TableColumn.SortType.ASCENDING);
        tblFlights.getSortOrder().setAll(colPrice);
    }

    // === Event handlers referenced in FXML ===
    @FXML
    private void handleSearch() {
        String origin = safeUpper(txtOrigin.getText());
        String destination = safeUpper(txtDestination.getText());
        LocalDate date = dpDate.getValue();

        // Build search criteria (nulls are allowed and treated as "no filter")
        SearchCriteria criteria = new SearchCriteria(origin, destination, date);

        // Filter using business service
        List<Flight> filtered = fareService.filterFlights(allFlights, criteria);
        backingData.setAll(filtered);

        // Find cheapest in the filtered list
        Flight cheapest = fareService.findCheapestFlight(filtered);
        if (cheapest != null) {
            lblStatus.setText(
                "Found " + filtered.size() + " flights. Cheapest: " +
                cheapest.getCode() + " (" + cheapest.getAirline() + ") €" + String.format("%.2f", cheapest.getPrice())
            );
        } else {
            lblStatus.setText("No flights match your criteria.");
        }
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    // === Helpers ===
    private String safeUpper(String s) {
        return (s == null || s.isBlank()) ? null : s.trim().toUpperCase(Locale.ROOT);
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content == null ? "" : content);
        alert.showAndWait();
    }
}
