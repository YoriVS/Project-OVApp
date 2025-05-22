package org.example.ovapp.controller.TrajectOption;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.ovapp.OVApp;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.handler.TimeHandler;
import org.example.ovapp.traject.Route;

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
    SearchTrajectComponent searchTrajectComponent;

    @FXML
    public void initialize() {
        searchTrajectComponent = new SearchTrajectComponent(swapLocationsButton, currentLocationField, endLocationField, saveRouteVisualButton, manageRoutesButton,timeField, searchButton, chooseTransportComboBox, timeTypeComboBox);
        searchTrajectComponent.initialize();
    }

    public void handleLogoutButtonAction() {
        ScreenHandler.changeScreen("login-page.fxml", OVApp.currentStage);
    }

    public void handleSwapLocationsButtonAction() {
        searchTrajectComponent.swapLocations();
    }

    public void handleSaveRouteButtonAction(ActionEvent actionEvent) {
        // #TODO Save route
    }

    public void handleManageRoutesButtonAction(ActionEvent actionEvent) {
        // #TODO Go to saved route
    }

    public void searchTrajectButtonClicked() {
        selectedRoute = searchTrajectComponent.getRoute();
        if (selectedRoute == null) return;
        ScreenHandler.changeScreen("traject-list.fxml", OVApp.currentStage);
    }

    public void handleMenuButtonAction(ActionEvent actionEvent) {
        // #TODO Handle Menu Button
    }

    public void handleSettingsButtonAction(ActionEvent actionEvent) {
        // #TODO Handle SettingsButton
    }

    public void handleRestartButtonAction() {
        searchTrajectComponent.initialize();
    }
}
