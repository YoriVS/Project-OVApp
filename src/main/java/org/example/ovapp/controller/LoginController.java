package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.example.ovapp.Constant;
import org.example.ovapp.handler.ScreenHandler;

import java.io.IOException;

public class LoginController {
    @FXML
    protected void onLoginButtonClicked(ActionEvent actionEvent) throws IOException {
        ScreenHandler.changeScreen("menu-page", "OVApp", ScreenHandler.getStage(actionEvent), Constant.screenWidth, Constant.screenHeight);

    }

}