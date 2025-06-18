package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.example.ovapp.*;
import org.example.ovapp.controller.TrajectOption.TrajectOptionController;
import org.example.ovapp.nsApi.train.NSProxy;
import org.example.ovapp.handler.ScreenHandler;
import org.example.ovapp.traject.Route;
import org.example.ovapp.traject.Traject;
import org.example.ovapp.handler.TimeHandler;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

public class TrajectListController {
    /// Load FXML items
    @FXML
    public ListView<Traject> trajectList;
    private static List<Traject> trajects = new ArrayList<>();
    private static int moreButtonTimeClicked = 0;
    private Route selectedRoute;

    /// Initialize screen
    @FXML
    public void initialize() {
        trajectList.getItems().clear();
        trajects.clear();
        selectedRoute = TrajectOptionController.selectedRoute;
        trajects.addAll(NSProxy.getTrips(selectedRoute.getStartPoint(), selectedRoute.getEndPoint(), TimeHandler.convertTimeToRFC3339(selectedRoute.getTime()), selectedRoute.isDepartureOrNot(), selectedRoute.getVehicle()));
        trajectList.getItems().addAll(trajects);
    }

    /// Load new screen with the selected traject
    @FXML
    public void chooseButtonClicked() {
        Traject selectedTraject = trajectList.getSelectionModel().getSelectedItem();
        if (selectedTraject != null) {
            OVApp.currentTraject = selectedTraject;
            ScreenHandler.changeScreen("traject-page.fxml", ScreenHandler.currentStage);
        } else {
            ScreenHandler.openPopup("Please select Traject");
        }
    }

    /// Load more traject
    @FXML
    public void moreTrajectsButtonClicked() {
        moreButtonTimeClicked += 1;
        try {
            OffsetDateTime baseDateTime = OffsetDateTime.parse(TimeHandler.convertTimeToRFC3339(selectedRoute.getTime()));
            if (baseDateTime.plusHours(moreButtonTimeClicked).toLocalTime().getHour() < 5) {
                moreButtonTimeClicked += 5;
            }
            trajects.addAll(NSProxy.getTrips(selectedRoute.getStartPoint(), selectedRoute.getEndPoint(), baseDateTime.plusHours(moreButtonTimeClicked).toString(), selectedRoute.isDepartureOrNot(), selectedRoute.getVehicle()));

            trajects = trajects.stream().distinct().collect(Collectors.toList());

            trajectList.getItems().clear();
            trajectList.getItems().addAll(trajects);
        } catch (DateTimeParseException e) {
            System.err.println("Error searching for trajects: " + e.getMessage());
            trajectList.getItems().clear();
        }
    }

    /// Go back to Traject Option Selector
    @FXML
    public void handleBackButtonAction() {
        ScreenHandler.changeScreen("chose-menu.fxml", ScreenHandler.currentStage, 600, 400);
    }

    public void handleMenuButtonAction(ActionEvent actionEvent) {
        ScreenHandler.changeScreen("chose-menu.fxml", ScreenHandler.currentStage, 600, 400);
    }

    public void handleSettingsButtonAction(ActionEvent actionEvent) {
        // #TODO Setting
    }

    public void handleRestartButtonAction(ActionEvent actionEvent) {
        ScreenHandler.changeScreen("chose-menu.fxml", ScreenHandler.currentStage, 600, 400);
    }

    public void handleLogoutButtonAction(ActionEvent actionEvent) {
        ScreenHandler.changeScreen("login-page.fxml", ScreenHandler.currentStage, 500, 300);
//        ScreenHandler.changeScreen("login-page.fxml", ScreenHandler.currentStage);
    }

}
