package org.example.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class LoginController {
    @FXML
    protected void onLoginButtonClicked(ActionEvent actionEvent) throws IOException {
        Screen.changeScreen("menu-page", "OVApp", Screen.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);

    }

}