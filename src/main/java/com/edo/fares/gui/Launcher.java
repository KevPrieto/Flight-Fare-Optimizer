package com.edo.fares.gui;

/**
 * JavaFX-compatible launcher for packaged builds.
 * This exists because jpackage sometimes fails when the main class directly
 * extends javafx.application.Application.
 */
public class Launcher {
    public static void main(String[] args) {
        MainApp.main(args);
    }
}
