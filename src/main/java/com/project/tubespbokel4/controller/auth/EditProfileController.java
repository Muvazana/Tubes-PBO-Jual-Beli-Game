package com.project.tubespbokel4.controller.auth;

import com.project.tubespbokel4.model.auth.*;
import com.project.tubespbokel4.view.auth.EditProfileFrame;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.dialogController;

public class EditProfileController {
    private EditProfileFrame frame;
    private UserDAO userDAO;
    private UserStoreDAO userStoreDAO;

    private User user;

    public EditProfileController(EditProfileFrame frame){
        this.frame = frame;
        if(authentication.isUserExist() && authentication.getSession().getRole() != Roles.seller){
            this.frame.getPaneStoreDetails().setVisible(false);
            userDAO = new UserDAO();
        }else{
            this.frame.getPaneStoreDetails().setVisible(true);
            userStoreDAO = new UserStoreDAO();
        }
        this.setDataToFrame();
    }

    public void setDataToFrame(){
        if(authentication.isUserExist() && authentication.getSession().getRole() != Roles.seller){
            try {
                this.user = userDAO.getUserFromId(authentication.getSession().getId());
                this.frame.getEtAccountName().setText(this.user.getName());
                this.frame.getEtRole().setText(this.user.getSimpleNameRole());
                this.frame.getEtWallet().setText(""+this.user.getWallet());
                this.frame.getEtUsername().setText(this.user.getUsername());
                this.frame.getEtPassword().setText(this.user.getPassword());
                this.frame.disable();
            } catch (SQLException e) {
                this.frame.setErrorMsg(e.getMessage());
            }
        }else{
            try {
                this.user = userStoreDAO.getUserFromId(authentication.getSession().getId());
                this.frame.getEtAccountName().setText(this.user.getName());
                this.frame.getEtRole().setText(this.user.getSimpleNameRole());
                this.frame.getEtWallet().setText(""+this.user.getWallet());
                this.frame.getEtUsername().setText(this.user.getUsername());
                this.frame.getEtPassword().setText(this.user.getPassword());
                this.frame.getEtStoreName().setText(((UserStore)this.user).getStoreName());
                this.frame.getEtStoreDescription().setText(((UserStore)this.user).getDescription());
                this.frame.disable();
            } catch (SQLException e) {
                this.frame.setErrorMsg(e.getMessage());
            }
        }
    }

    public void onBtnSaveClicked(){
        if(dialogController.dialogConfirm("Save seluruh perubahan yang dilakukan?").get() == ButtonType.OK){
            String msg = "";

            if(authentication.getSession().getRole() != Roles.seller){
                if(this.frame.getEtAccountName().getText().isBlank()) msg = "Kolom 'Account Name' tidak boleh kosong!";
                else if(this.frame.getEtPassword().getText().isBlank()) msg = "Kolom 'Password' tidak boleh kosong!";
                else{
                    this.user.setName(this.frame.getEtAccountName().getText().trim());
                    this.user.setPassword(this.frame.getEtPassword().getText().trim());
                    try {
                        boolean isSuccess = this.userDAO.update(this.user);
                        if (isSuccess){
                            // TODO
                            User tempUser = authentication.getSession();
                            tempUser.setName(this.frame.getEtAccountName().getText().trim());
                            authentication.updateSession(tempUser);
                            dialogController.dialogInfomation("Edit Profile Berhasil!");
                            this.frame.disable();
                        }else{
                            msg = "Terjadi Kesalahan!";
                        }
                    } catch (SQLException e) {
                        msg = e.getMessage();
                    }
                }
            }else{
                if(this.frame.getEtAccountName().getText().isBlank()) msg = "Kolom 'Account Name' tidak boleh kosong!";
                else if(this.frame.getEtPassword().getText().isBlank()) msg = "Kolom 'Password' tidak boleh kosong!";
                else if(this.frame.getEtStoreName().getText().isBlank()) msg = "Kolom 'Store Name' tidak boleh kosong!";
                else{
                    this.user.setName(this.frame.getEtAccountName().getText().trim());
                    this.user.setPassword(this.frame.getEtPassword().getText().trim());
                    ((UserStore)this.user).setStoreName(this.frame.getEtStoreName().getText().trim());
                    ((UserStore)this.user).setDescription(this.frame.getEtStoreDescription().getText().trim());
                    try {
                        boolean isSuccess = this.userStoreDAO.update(((UserStore)this.user));
                        if (isSuccess){
                            // TODO
                            User tempUser = authentication.getSession();
                            tempUser.setName(this.frame.getEtAccountName().getText().trim());
                            authentication.updateSession(tempUser);
                            dialogController.dialogInfomation("Edit Profile Berhasil!");
                            this.frame.disable();
                        }else{
                            msg = "Terjadi Kesalahan!";
                        }
                    } catch (SQLException e) {
                        msg = e.getMessage();
                    }
                }
            }
            this.frame.setErrorMsg(msg);
        }
    }
}
