package org.example.ovapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.ovapp.handler.LoginHandler;
import org.example.ovapp.handler.ScreenHandler;

public class SignupController {
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField repeatPasswordField;
    public Label statusLabel;

    @FXML
    public void initialize() {
        statusLabel.setVisible(false);
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        repeatPasswordField.setPromptText("Repeat password");
    }

    public void handleRegister(ActionEvent actionEvent) {
        if (usernameField.getText().equals("") && passwordField.getText().equals("") && repeatPasswordField.getText().equals("")) {
            statusLabel.setVisible(true);
            statusLabel.setText("Please fill all the fields");
            usernameField.requestFocus();
        } else if (usernameField.getText().equals("")) {
            statusLabel.setVisible(true);
            statusLabel.setText("Please fill username field");
            usernameField.requestFocus();
        } else if (passwordField.getText().equals("")) {
            statusLabel.setVisible(true);
            statusLabel.setText("Please fill password field");
            passwordField.requestFocus();
        } else if (repeatPasswordField.getText().equals("")) {
            statusLabel.setVisible(true);
            statusLabel.setText("Please fill repeat your password field");
            repeatPasswordField.requestFocus();
        } else {
            if (!passwordField.getText().equals(repeatPasswordField.getText())) {
                statusLabel.setVisible(true);
                statusLabel.setText("Passwords don't match");
                return;
            }
            LoginHandler.register(usernameField.getText(), passwordField.getText());
        }
    }

    public void handleBackToLogin(ActionEvent actionEvent) {
        ScreenHandler.changeScreen("login-page.fxml", ScreenHandler.currentStage, 500, 300);
//        ScreenHandler.changeScreen("login-page.fxml", ScreenHandler.currentStage);
    }
}
