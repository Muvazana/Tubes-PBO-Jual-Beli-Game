package com.project.tubespbokel4.controller.auth;

import com.project.tubespbokel4.model.auth.*;
import com.project.tubespbokel4.view.auth.LoginFrame;
import com.project.tubespbokel4.view.auth.RegisterMemberFrame;
import com.project.tubespbokel4.view.auth.RegisterStoreFrame;

import java.sql.SQLException;

import static com.project.tubespbokel4.MainApplication.dialogController;
import static com.project.tubespbokel4.MainApplication.screenController;

public class RegisterStoreController {

    private RegisterStoreFrame frame;
    private UserStoreDAO userStoreDAO;
    public RegisterStoreController(RegisterStoreFrame frame) {
        this.frame = frame;
        this.userStoreDAO = new UserStoreDAO();
    }

    public void onBtnRegisterNowClicked(){
        String msg = "";

        if(this.frame.getEtName().getText().isBlank()) msg = "Kolom 'Name' tidak boleh kosong!";
        else if(this.frame.getEtUsername().getText().isBlank()) msg = "Kolom 'Username' tidak boleh kosong!";
        else if(this.frame.getEtUsername().getText().isBlank()) msg = "Kolom 'Password' tidak boleh kosong!";
        else if(this.frame.getEtStoreName().getText().isBlank()) msg = "Kolom 'Store Name' tidak boleh kosong!";
        else{
            UserStore userStore = new UserStore(
                    new User(
                            0,
                            this.frame.getEtName().getText().trim(),
                            Roles.seller,
                            0,
                            this.frame.getEtUsername().getText().trim(),
                            this.frame.getEtUsername().getText().trim()
                    ),
                    this.frame.getEtStoreName().getText(),
                    this.frame.getEtAreaDescription().getText()
            );
            try {
                int id = this.userStoreDAO.insert(userStore);
                if (id != -1){
                    // TODO
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                msg = e.getMessage();
            }
        }
        if(msg.isBlank()){
            this.frame.getTxtErrorMsg().setVisible(false);
//            System.out.println("Berhasil Terdaftar");
            this.frame.getEtName().clear();
            this.frame.getEtUsername().clear();
            this.frame.getEtPassword().clear();
            dialogController.dialogInfomation("Berhasil Terdaftar");
        }else{
            this.frame.getTxtErrorMsg().setVisible(true);
            this.frame.getTxtErrorMsg().setText(msg);
            dialogController.dialogError("Register Error!", msg);
        }
    }


    public void onBtnRegisterMemberClicked(){
        this.userStoreDAO.closeConnection();
        screenController.changeScreen(RegisterMemberFrame.screenURL);
    }

    public void onLoginNowClicked(){
        this.userStoreDAO.closeConnection();
        screenController.changeScreen(LoginFrame.screenURL);
    }
}
