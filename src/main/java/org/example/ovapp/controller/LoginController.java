package org.example.ovapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.ovapp.handler.LoginHandler;
import org.example.ovapp.handler.ScreenHandler;

public class LoginController {
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorLabel;

    @FXML
    public void initialize() {
        errorLabel.setVisible(false);
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
    }

    @FXML
    protected void onLoginButtonClicked() {

        if (usernameField.getText().isEmpty() && passwordField.getText().equals("")) {
            errorLabel.setVisible(true);
            usernameField.requestFocus();
            errorLabel.setText("Please enter all the fields");
        } else if (usernameField.getText().isEmpty()) {
            errorLabel.setVisible(true);
            usernameField.requestFocus();
            errorLabel.setText("Username is empty");
        } else if (passwordField.getText().isEmpty()) {
            errorLabel.setVisible(true);
            passwordField.requestFocus();
            errorLabel.setText("Password is empty");
        } else {
            if (LoginHandler.login(usernameField.getText(), passwordField.getText())) {
                ScreenHandler.changeScreen("chose-menu.fxml", ScreenHandler.currentStage, 600, 400);
            } else {
                errorLabel.setVisible(true);
                errorLabel.setText("Wrong username or password");
            }
        }

    }

    public void handleRegisterButtonClicked(ActionEvent actionEvent) {
        ScreenHandler.changeScreen("register-page.fxml", ScreenHandler.currentStage);
    }
}