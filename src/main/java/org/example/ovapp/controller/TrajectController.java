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
    @FXML
    public Label arrivalTimeLabel;
    @FXML
    public Label departureTimeLabel;
    @FXML
    public ListView station;

    @FXML
    public void initialize() {
        if (OVApp.currentTraject != null) {
            trajectLabel.setText(OVApp.currentTraject.getName());
            departureTimeLabel.setText(TimeHandler.extractHourMinute(OVApp.currentTraject.getDepartureTime()));
            arrivalTimeLabel.setText(TimeHandler.extractHourMinute(OVApp.currentTraject.getArrivalTime()));
            station.getItems().clear();
            station.getItems().addAll(OVApp.currentTraject.getTrajectInfo());
        } else {
            System.out.println("not good");
        }
    }

    /// Go back to
    @FXML
    public void onBackButtonClicked() {
        ScreenHandler.changeScreen("traject-list", OVApp.currentStage);
    }

    public void handleMenuButtonAction(ActionEvent actionEvent) {
    }

    public void handleSettingsButtonAction(ActionEvent actionEvent) {
    }

    public void handleRestartButtonAction(ActionEvent actionEvent) {
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) {
    }

    public void handleRefreshButtonAction(ActionEvent actionEvent) {
    }
}
