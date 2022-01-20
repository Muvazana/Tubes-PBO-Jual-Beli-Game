package com.project.tubespbokel4.view.auth;

import com.project.tubespbokel4.controller.auth.EditProfileController;
import com.project.tubespbokel4.model.auth.Roles;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.dialogController;

public class EditProfileFrame implements Initializable {
    public static String screenURL = "/auth/edit-profile.fxml";
    private EditProfileController controller;

    @FXML
    private Button btnEditProfile;

    @FXML
    private Button btnSave;

    @FXML
    private TextField etAccountName;

    @FXML
    private PasswordField etPassword;

    @FXML
    private TextField etRole;

    @FXML
    private TextArea etStoreDescription;

    @FXML
    private TextField etStoreName;

    @FXML
    private TextField etUsername;

    @FXML
    private TextField etWallet;

    @FXML
    private Group paneStoreDetails;

    @FXML
    private Label txtErrorMsg1;

    @FXML
    private Label txtErrorMsg2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.txtErrorMsg1.setVisible(false);
        this.txtErrorMsg2.setVisible(false);
        controller = new EditProfileController(this);
    }

    @FXML
    void onBtnEditProfileClicked(MouseEvent event) {
        if(this.btnSave.isDisable())this.enable();
        else {
            if(dialogController.dialogConfirm("Menghapus semua perubahan yang dilakukan?").get() == ButtonType.OK){
                controller.setDataToFrame();
                this.disable();
                this.setErrorMsg(null);
            }
        }
    }

    @FXML
    void onBtnSaveClicked(MouseEvent event) {
        this.controller.onBtnSaveClicked();
    }

    public void setErrorMsg(String msg){
        if(msg == null || msg.isBlank()){
            this.txtErrorMsg1.setVisible(false);
            this.txtErrorMsg2.setVisible(false);
        }else{
            this.txtErrorMsg1.setVisible(true);
            this.txtErrorMsg1.setText(msg);
            if(authentication.getSession().getRole() == Roles.seller){
                this.txtErrorMsg2.setVisible(true);
                this.txtErrorMsg2.setText(msg);
            }
            dialogController.dialogError("Edit Profile Error!", msg);
        }
    }

    public void disable(){
        this.etAccountName.setDisable(true);
        this.etRole.setDisable(true);
        this.etWallet.setDisable(true);
        this.etUsername.setDisable(true);
        this.etPassword.setDisable(true);
        this.etStoreName.setDisable(true);
        this.etStoreDescription.setDisable(true);
        this.btnSave.setDisable(true);
    }
    public void enable(){
        this.etAccountName.setDisable(false);
        this.etPassword.setDisable(false);
        this.etStoreName.setDisable(false);
        this.etStoreDescription.setDisable(false);
        this.btnSave.setDisable(false);
    }

    public Button getBtnEditProfile() {
        return btnEditProfile;
    }

    public Button getBtnSave() {
        return btnSave;
    }

    public TextField getEtAccountName() {
        return etAccountName;
    }

    public PasswordField getEtPassword() {
        return etPassword;
    }

    public TextField getEtRole() {
        return etRole;
    }

    public TextArea getEtStoreDescription() {
        return etStoreDescription;
    }

    public TextField getEtStoreName() {
        return etStoreName;
    }

    public TextField getEtUsername() {
        return etUsername;
    }

    public TextField getEtWallet() {
        return etWallet;
    }

    public Group getPaneStoreDetails() {
        return paneStoreDetails;
    }

}
