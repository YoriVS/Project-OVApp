package org.example.ovapp;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.traject.Traject;

public class OVApp extends Application {
    public static Traject currentTraject;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ScreenHandler.openNewScene("login-page", 300, 500, primaryStage);
    }

    public static void main(String[] args) {
        launch();

    }
}
