package org.example.ovapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ovapp.traject.Traject;

public class OVApp extends Application {
    public static Traject currentTraject;
    public static Stage currentStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        currentStage = primaryStage;
        currentStage.setTitle("Login");
        currentStage.setScene(scene);
        currentStage.show();
        currentStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();

    }
}
