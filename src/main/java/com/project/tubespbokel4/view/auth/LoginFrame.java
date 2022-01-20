package com.project.tubespbokel4.view.auth;

import com.project.tubespbokel4.controller.auth.LoginController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginFrame {
    public static String screenURL = "/auth/login.fxml";
    private LoginController loginController;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField etPassword;

    @FXML
    private TextField etUsername;

    @FXML
    private Label txtErrorMsg;

    @FXML
    private Label txtRegisterNow;

    public LoginFrame(){
        this.loginController = new LoginController(this);
    }

    @FXML
    void onBtnLoginNowClicked(ActionEvent event) {
        this.loginController.onBtnLoginNowClicked();
    }

    @FXML
    void onRegisterNowClicked(MouseEvent event) {
        this.loginController.onRegisterNowClicked();
    }

    public PasswordField getEtPassword() {
        return etPassword;
    }

    public TextField getEtUsername() {
        return etUsername;
    }

    public Label getTxtErrorMsg() {
        return txtErrorMsg;
    }
}
