package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.example.ovapp.Constant;
import org.example.ovapp.OVApp;
import org.example.ovapp.Screen;

import java.io.IOException;

public class TrajectController {

    public Label trajectLabel;

    @FXML
    public void initialize() {
        if (OVApp.currentTraject != null) {
            trajectLabel.setText(OVApp.currentTraject.toString());
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Screen.changeScreen("menu-page", "Menu", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
    }
}
