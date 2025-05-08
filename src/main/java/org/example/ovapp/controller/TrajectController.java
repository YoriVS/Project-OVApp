package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.ovapp.Constant;
import org.example.ovapp.OVApp;
import org.example.ovapp.Screen;

import java.io.IOException;

public class TrajectController {

    public Label trajectLabel;
    public Label arrivalTimeLabel;
    public Label departureTimeLabel;
    public ListView inBetweenStation;

    @FXML
    public void initialize() {
        if (OVApp.currentTraject != null) {
            trajectLabel.setText(OVApp.currentTraject.getName());
            departureTimeLabel.setText(MenuController.extractHourMinute(OVApp.currentTraject.getDepartureTime()));
            arrivalTimeLabel.setText(MenuController.extractHourMinute(OVApp.currentTraject.getArrivalTime()));
            inBetweenStation.getItems().clear();
            inBetweenStation.getItems().addAll(OVApp.currentTraject.getInBetweenStations());
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        Screen.changeScreen("menu-page", "Menu", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
    }
}
