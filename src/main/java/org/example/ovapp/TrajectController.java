package org.example.ovapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TrajectController {

    public Label trajectLabel;

    @FXML
    public void initialize() {
        if (OVApp.currentTraject != null) {
            trajectLabel.setText(OVApp.currentTraject.toString());
        }
    }
}
