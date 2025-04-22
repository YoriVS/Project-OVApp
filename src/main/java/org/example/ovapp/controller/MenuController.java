package org.example.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.Objects;

public class MenuController {
    public ListView trajectList;

    @FXML
    public void chooseButtonClicked(ActionEvent actionEvent) throws IOException {
        assert trajectList != null;
        for (Traject traject: RouteList.trajects) {
            if (Objects.equals(traject.toString(), trajectList.getSelectionModel().getSelectedItem().toString())) {
                OVApp.currentTraject = traject;
            }
        }

        Screen.changeScreen("traject-page", "Traject", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);
    }

    @FXML
    public void initialize() {
        trajectList.getItems().clear();
        trajectList.getItems().addAll(RouteList.getTrajectList());
    }
}
