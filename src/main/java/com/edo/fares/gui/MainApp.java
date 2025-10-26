package com.edo.fares.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * Main JavaFX launcher for Flight Fare Optimizer.
 * Handles resource loading for both IDE and packaged EXE builds.
 */
public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // ✅ Ruta embebida dentro del JAR (para builds con jpackage)
            String fxmlPath = "/com/edo/fares/gui/view/flight_search.fxml";
            URL fxmlUrl = getClass().getResource(fxmlPath);

            if (fxmlUrl == null) {
                throw new IOException("FXML not found at " + fxmlPath);
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load());

            primaryStage.setTitle("Flight Fare Optimizer");
            primaryStage.getIcons().add(new Image(Objects.requireNonNull(
                    getClass().getResourceAsStream("/icon.ico"))));
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("UI Load Error", "Could not load interface:\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Unexpected Error", e.getMessage());
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
