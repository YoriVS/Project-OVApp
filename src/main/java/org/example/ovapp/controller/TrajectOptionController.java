package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.ovapp.OVApp;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.Route;
import org.example.ovapp.traject.Traject;

import java.util.ArrayList;

public class TrajectOptionController {

    public Button logoutButton;
    public Button swapLocationsButton;
    public TextField currentLocationField;
    public TextField endLocationField;
    public Button saveRouteVisualButton;
    public Button manageRoutesButton;
    public TextField timeField;
    public Button searchButton;
    public Button menuButton;
    public Button settingsButton;
    public Button restartButton;
    public ComboBox chooseTransportComboBox;
    public ComboBox timeTypeComboBox;

    public static Route selectedRoute;
    public static ArrayList<Traject> trajects = new ArrayList<>();

    @FXML
    public void initialize() {
        timeField.setText(TimeHandler.getCurrentRFC3339Time().format(TimeHandler.HH_MM_FORMATTER));
        currentLocationField.setText("Amsterdam Centraal");
        endLocationField.setText("Groningen");
    }

    public void handleLogoutButtonAction() {
        ScreenHandler.changeScreen("login-page.fxml", OVApp.currentStage);
    }

    public void handleSwapLocationsButtonAction() {
        String tempLocation = currentLocationField.getText();
        String tempEndLocation = endLocationField.getText();
        currentLocationField.setText(tempEndLocation);
        endLocationField.setText(tempLocation);
    }

    public void handleSaveRouteButtonAction(ActionEvent actionEvent) {
        // #TODO Save route
    }

    public void handleManageRoutesButtonAction(ActionEvent actionEvent) {
        // #TODO Go to saved route
    }

    public void searchTrajectButtonClicked() {
        if (currentLocationField.getText().isEmpty() || endLocationField.getText().isEmpty() || timeField.getText().isEmpty() || timeTypeComboBox.getSelectionModel().getSelectedItem() == null) {
            if (currentLocationField.getText().isEmpty()) {
                currentLocationField.setPromptText("Please enter start point");
            }
            if (endLocationField.getText().isEmpty()) {
                endLocationField.setPromptText("Please enter end point");
            }
            if (timeField.getText().isEmpty()) {
                timeField.setPromptText("Please enter time");
            }
        } else {
            String baseDateTime = timeField.getText();
            String startPoint = currentLocationField.getText();
            String endPoint = endLocationField.getText();
            boolean departureOrNot = timeTypeComboBox.getSelectionModel().getSelectedItem().equals("Arrival");
            selectedRoute = new Route(startPoint, endPoint, departureOrNot, baseDateTime);
            ScreenHandler.changeScreen("traject-list.fxml", OVApp.currentStage);
        }
    }

    public void handleMenuButtonAction(ActionEvent actionEvent) {
        // #TODO Handle Menu Button
    }

    public void handleSettingsButtonAction(ActionEvent actionEvent) {
        // #TODO Handle SettingsButton
    }

    public void handleRestartButtonAction() {
        timeField.setText(TimeHandler.getCurrentRFC3339Time().format(TimeHandler.HH_MM_FORMATTER));
        currentLocationField.setText("Amsterdam Centraal");
        endLocationField.setText("Groningen");
    }
}
