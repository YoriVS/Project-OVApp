package org.example.ovapp;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MenuController {
    public ListView trajectList;
    public Button reloadButton;

    public void reloadTraject(ActionEvent actionEvent) {
        trajectList.getItems().clear();
        trajectList.getItems().addAll(RouteList.getTrajectList());
    }
}
