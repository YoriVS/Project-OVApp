package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.ovapp.*;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.handler.TimeHandler;

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
            departureTimeLabel.setText(TimeHandler.extractHourMinute(OVApp.currentTraject.getDepartureTime()));
            arrivalTimeLabel.setText(TimeHandler.extractHourMinute(OVApp.currentTraject.getArrivalTime()));
            inBetweenStation.getItems().clear();
            inBetweenStation.getItems().addAll(OVApp.currentTraject.getTrajectInfo());
        }
    }

    public void onBackButtonClicked(ActionEvent actionEvent) throws IOException {
        ScreenHandler.changeScreen("menu-page", "Menu", ScreenHandler.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
    }
}
