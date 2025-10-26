package com.edo.fares.gui;

import com.edo.fares.api.LocalJsonClient;
import com.edo.fares.exception.DataLoadException;
import com.edo.fares.model.Flight;
import com.edo.fares.model.SearchCriteria;
import com.edo.fares.report.ReportGenerator;
import com.edo.fares.service.FareService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FlightSearchController {

    // --- Controles vinculados al FXML ---
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

    // --- Estado ---
    private final ObservableList<Flight> backingData = FXCollections.observableArrayList();
    private final FareService fareService = new FareService();
    private List<Flight> allFlights = new ArrayList<>();

    // --- Inicialización ---
    @FXML
    private void initialize() {
        // Asegura formato decimal consistente
        Locale.setDefault(Locale.US);

        // Configurar columnas
        if (colCode != null)        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        if (colAirline != null)     colAirline.setCellValueFactory(new PropertyValueFactory<>("airline"));
        if (colOrigin != null)      colOrigin.setCellValueFactory(new PropertyValueFactory<>("origin"));
        if (colDestination != null) colDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        if (colDate != null)        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        if (colPrice != null)       colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        if (tblFlights != null) {
            tblFlights.setItems(backingData);
        }

        // Cargar datos locales
        try {
            LocalJsonClient local = new LocalJsonClient();
            allFlights = local.loadFlights("sample-flights.json");
            backingData.setAll(allFlights);
            setStatus("Loaded " + allFlights.size() + " flights.");
        } catch (DataLoadException e) {
            showError("Load error", e.getMessage());
            setStatus("Failed to load data.");
        }

        // Orden por precio ascendente
        if (colPrice != null && tblFlights != null) {
            colPrice.setSortType(TableColumn.SortType.ASCENDING);
            tblFlights.getSortOrder().setAll(colPrice);
        }
    }

    // --- Acciones UI ---
    @FXML
    private void handleSearch() {
        String origin = toUpper(txtOrigin != null ? txtOrigin.getText() : null);
        String destination = toUpper(txtDestination != null ? txtDestination.getText() : null);
        LocalDate date = dpDate != null ? dpDate.getValue() : null;

        // ✅ Mostrar todo si no hay filtros
        if ((origin == null || origin.isBlank()) &&
            (destination == null || destination.isBlank()) &&
            date == null) {
            backingData.setAll(allFlights);
            setStatus("Showing all " + allFlights.size() + " flights.");
            return;
        }

        SearchCriteria criteria = new SearchCriteria(origin, destination, date);
        List<Flight> filtered = fareService.filterFlights(allFlights, criteria);
        backingData.setAll(filtered);

        Flight cheapest = fareService.findCheapestFlight(filtered);
        if (cheapest != null) {
            setStatus(
                "Found " + filtered.size() + " flights. Cheapest: " +
                cheapest.getCode() + " (" + cheapest.getAirline() + ") €" +
                String.format("%.2f", cheapest.getPrice())
            );
        } else {
            setStatus("No flights found.");
        }
    }

    /**
     * Handler pensado por el FXML (#handleExport).
     * Lo dejamos como alias para evitar errores si el FXML llama a este.
     */
    @FXML
    private void handleExport() {
        handleExportPdf();
    }

    /**
     * Handler alternativo (#handleExportPdf). Ambos hacen lo mismo.
     */
    @FXML
    private void handleExportPdf() {
        try {
            if (backingData.isEmpty()) {
                showError("No data to export", "Please load or search flights first.");
                setStatus("Nothing to export.");
                return;
            }

            // Crear carpeta en Documentos del usuario
            String userDocs = System.getProperty("user.home") + "\\Documents\\FlightFareReports";
            Path outDir = Path.of(userDocs);
            if (!Files.exists(outDir)) {
                Files.createDirectories(outDir);
            }

            // Generar reporte
            ReportGenerator reportGenerator = new ReportGenerator();
            String outPath = outDir.resolve("flight_report.pdf").toString();
            reportGenerator.generatePdfReport(backingData, outPath);

            setStatus("PDF exported successfully: " + outPath);

        } catch (Exception e) {
            showError("Export failed", e.getMessage());
            setStatus("PDF export error.");
        }
    }

    @FXML
    private void closeApp() {
        Platform.exit();
    }

    // --- Utils ---
    private String toUpper(String s) {
        return (s == null || s.isBlank()) ? null : s.trim().toUpperCase(Locale.ROOT);
    }

    private void setStatus(String msg) {
        if (lblStatus == null) return;
        if (Platform.isFxApplicationThread()) {
            lblStatus.setText(msg);
        } else {
            Platform.runLater(() -> lblStatus.setText(msg));
        }
    }

    private void showError(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content == null ? "" : content);
        alert.showAndWait();
    }
}
