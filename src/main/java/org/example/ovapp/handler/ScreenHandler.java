package org.example.ovapp.handler;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ovapp.OVApp;

import java.io.IOException;
import java.util.Objects;


public class ScreenHandler {
    public static Stage currentStage;


    public static void changeScreen(String screenName, Stage stage) {
        try {
            FXMLLoader fxmlLoader = screenName.contains(".fxml") ? new FXMLLoader(OVApp.class.getResource(screenName)) : new FXMLLoader(OVApp.class.getResource(screenName + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("OVApp");

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            ScreenHandler.openPopup("Failed to open screen");
            System.out.println(e.getMessage());
        }
    }

    public static void changeScreen(String screenName, Stage stage, int width, int height) {
        try {
            FXMLLoader fxmlLoader = screenName.contains(".fxml") ? new FXMLLoader(OVApp.class.getResource(screenName)) : new FXMLLoader(OVApp.class.getResource(screenName + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            stage.setTitle("OVApp");

            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        } catch (IOException e) {
            ScreenHandler.openPopup("Failed to open screen");
            System.out.println(e.getMessage());
        }
    }

    public static void openNewScene(String screenName, int height, int width, Stage oldStage) {
        try {
            if (oldStage != null) oldStage.close();

            FXMLLoader fxmlLoader = screenName.contains(".fxml") ? new FXMLLoader(OVApp.class.getResource(screenName)) : new FXMLLoader(OVApp.class.getResource(screenName + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load(), width, height);
            Stage stage = new Stage();

            currentStage = stage;

            stage.setTitle("Bazaar Tracker");

            currentStage.setTitle("OVApp");
            currentStage.setScene(scene);
            currentStage.show();
            currentStage.setResizable(false);
            currentStage.setWidth(width);
            currentStage.setHeight(height);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            ScreenHandler.openPopup("Failed to open window");
        }
    }

    public static void openPopup(String message) {
        Stage popup = new Stage();
        popup.setTitle("Error");

        Label messageLabel = new Label(message);

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popup.close());

        VBox popupLayout = new VBox(20);
        popupLayout.setAlignment(Pos.CENTER);
        popupLayout.setPadding(new Insets(20, 0, 0, 0));

        popupLayout.getChildren().addAll(messageLabel, closeButton);

        Scene popupScene = new Scene(popupLayout, 300, 200);
        popup.setScene(popupScene);
        popup.show();
    }


}
