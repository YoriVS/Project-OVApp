package org.example.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Screen {
    public static void changeScreen(String screenName, String title, Stage stage, int screenWith, int screenHeight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OVApp.class.getResource(screenName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWith, screenHeight);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getStage(ActionEvent actionEvent) {
        return (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
    }

}
