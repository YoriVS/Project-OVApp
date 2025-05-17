package org.example.ovapp.handler;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ovapp.OVApp;

import java.io.IOException;

public class ScreenHandler {

    /// Change screen with input (file.fxml, Title of screen, getStage(actionEvent), Constant.screenWith, Constant.screenHeight)
    public static void changeScreen(String screenName, String title, Stage stage, int screenWith, int screenHeight) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(OVApp.class.getResource(screenName + ".fxml"));
        Scene scene = new Scene(fxmlLoader.load(), screenWith, screenHeight);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /// Used for the changeScreen to get the stage
    public static Stage getStage(ActionEvent actionEvent) {
        return (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
    }

}
