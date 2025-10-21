package com.edo.fares.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // ✅ Correct and universal way to load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edo/fares/gui/view/flight_search.fxml"));
        if (loader.getLocation() == null) {
            throw new IllegalStateException("FXML file not found! Check your resources path.");
        }

        Scene scene = new Scene(loader.load());
        stage.setTitle("Flight Fare Optimizer");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
