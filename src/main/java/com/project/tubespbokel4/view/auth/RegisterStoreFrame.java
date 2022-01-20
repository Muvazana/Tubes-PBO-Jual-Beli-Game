package com.project.tubespbokel4.view.auth;

import com.project.tubespbokel4.controller.auth.RegisterStoreController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterStoreFrame {
    public static String screenURL = "/auth/register-store.fxml";
    private RegisterStoreController controller;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnRegisterMember;

    @FXML
    private Button btnRegisterStore;

    @FXML
    private TextArea etAreaDescription;

    @FXML
    private TextField etName;

    @FXML
    private PasswordField etPassword;

    @FXML
    private TextField etStoreName;

    @FXML
    private TextField etUsername;

    @FXML
    private Label txtErrorMsg;

    @FXML
    private Label txtLoginNow;

    public RegisterStoreFrame() {
        this.controller = new RegisterStoreController(this);
    }

    @FXML
    void onBtnRegisterMemberClicked(ActionEvent event) {
        this.controller.onBtnRegisterMemberClicked();
    }

    @FXML
    void onBtnRegisterNowClicked(ActionEvent event) {
        this.controller.onBtnRegisterNowClicked();
    }

    @FXML
    void onBtnRegisterStoreClicked(ActionEvent event) {

    }

    @FXML
    void onLoginNowClicked(MouseEvent event) {
        this.controller.onLoginNowClicked();
    }

    public TextArea getEtAreaDescription() {
        return etAreaDescription;
    }

    public TextField getEtName() {
        return etName;
    }

    public PasswordField getEtPassword() {
        return etPassword;
    }

    public TextField getEtStoreName() {
        return etStoreName;
    }

    public TextField getEtUsername() {
        return etUsername;
    }

    public Label getTxtErrorMsg() {
        return txtErrorMsg;
    }
}
