package org.example.ovapp.controller;

import javafx.fxml.FXML;
import org.example.ovapp.OVApp;
import org.example.ovapp.handler.ScreenHandler;

public class LoginController {
    @FXML
    protected void onLoginButtonClicked() {
        ScreenHandler.changeScreen("chose-menu.fxml", ScreenHandler.currentStage);

    }

}