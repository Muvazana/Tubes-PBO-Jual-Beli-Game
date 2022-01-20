package com.project.tubespbokel4.view.auth;

import com.project.tubespbokel4.controller.auth.RegisterMemberController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class RegisterMemberFrame {
    public static String screenURL = "/auth/register-member.fxml";
    private RegisterMemberController controller;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnRegisterMember;

    @FXML
    private Button btnRegisterStore;

    @FXML
    private TextField etName;

    @FXML
    private PasswordField etPassword;

    @FXML
    private TextField etUsername;

    @FXML
    private Label txtErrorMsg;

    @FXML
    private Label txtLoginNow;

    public RegisterMemberFrame(){
        this.controller = new RegisterMemberController(this);
    }

    @FXML
    void onBtnRegisterMemberClicked(ActionEvent event) {}

    @FXML
    void onBtnRegisterNowClicked(ActionEvent event) {
        this.controller.onBtnRegisterNowClicked();
    }

    @FXML
    void onBtnRegisterStoreClicked(ActionEvent event) {
        this.controller.onBtnRegisterStoreClicked();
    }

    @FXML
    void onLoginNowClicked(MouseEvent event) {
        this.controller.onLoginNowClicked();
    }

    public TextField getEtName() {
        return etName;
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
