package com.project.tubespbokel4.controller.auth;

import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserDAO;
import com.project.tubespbokel4.view.auth.LoginFrame;
import com.project.tubespbokel4.view.auth.RegisterMemberFrame;
import com.project.tubespbokel4.view.auth.RegisterStoreFrame;

import java.sql.SQLException;

import static com.project.tubespbokel4.MainApplication.dialogController;
import static com.project.tubespbokel4.MainApplication.screenController;

public class RegisterMemberController {

    private RegisterMemberFrame frame;
    private UserDAO userDAO;
    public RegisterMemberController(RegisterMemberFrame frame) {
        this.frame = frame;
        this.userDAO = new UserDAO();
    }

    public void onBtnRegisterNowClicked(){
        String msg = "";

        if(this.frame.getEtName().getText().isBlank()) msg = "Kolom 'Name' tidak boleh kosong!";
        else if(this.frame.getEtUsername().getText().isBlank()) msg = "Kolom 'Username' tidak boleh kosong!";
        else if(this.frame.getEtUsername().getText().isBlank()) msg = "Kolom 'Password' tidak boleh kosong!";
        else{
            User user = new User(
                    0,
                    this.frame.getEtName().getText().trim(),
                    Roles.member,
                    0,
                    this.frame.getEtUsername().getText().trim(),
                    this.frame.getEtUsername().getText().trim()
            );
            try {
                int id = this.userDAO.insert(user);
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


    public void onBtnRegisterStoreClicked(){
        this.userDAO.closeConnection();
        screenController.changeScreen(RegisterStoreFrame.screenURL);
    }

    public void onLoginNowClicked(){
        this.userDAO.closeConnection();
        screenController.changeScreen(LoginFrame.screenURL);
    }
}
