package org.example.ovapp.controller.TrajectOption;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.Route;

public class SearchTrajectComponent {
    public Button swapLocationsButton;
    public TextField currentLocationField;
    public TextField endLocationField;
    public Button saveRouteVisualButton;
    public Button manageRoutesButton;
    public TextField timeField;
    public Button searchButton;
    public ComboBox chooseTransportComboBox;
    public ComboBox timeTypeComboBox;

    SearchTrajectComponent(Button swapLocationsButton, TextField currentLocationField, TextField endLocationField, Button saveRouteVisualButton, Button manageRoutesButton, TextField timeField, Button searchButton, ComboBox chooseTransportComboBox, ComboBox timeTypeComboBox) {
        this.swapLocationsButton = swapLocationsButton;
        this.currentLocationField = currentLocationField;
        this.endLocationField = endLocationField;
        this.saveRouteVisualButton = saveRouteVisualButton;
        this.manageRoutesButton = manageRoutesButton;
        this.timeField = timeField;
        this.searchButton = searchButton;
        this.chooseTransportComboBox = chooseTransportComboBox;
        this.timeTypeComboBox = timeTypeComboBox;
    }


    public void swapLocations() {
        String tempLocation = currentLocationField.getText();
        String tempEndLocation = endLocationField.getText();
        currentLocationField.setText(tempEndLocation);
        endLocationField.setText(tempLocation);
    }

    public Route getRoute() {
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
            return new Route(startPoint, endPoint, departureOrNot, baseDateTime);
        }
        return null;
    }

    public void initialize() {
        timeField.setText(TimeHandler.getCurrentRFC3339Time().format(TimeHandler.HH_MM_FORMATTER));
        currentLocationField.setText("Amsterdam Centraal");
        endLocationField.setText("Groningen");
    }
}
