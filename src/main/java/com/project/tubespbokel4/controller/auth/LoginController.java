package com.project.tubespbokel4.controller.auth;

import com.project.tubespbokel4.controller.DialogController;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserDAO;
import com.project.tubespbokel4.view.admin.DashboardAdminRootFrame;
import com.project.tubespbokel4.view.auth.LoginFrame;
import com.project.tubespbokel4.view.auth.RegisterMemberFrame;
import com.project.tubespbokel4.view.member.DashboardMemberRootFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoRootFrame;

import java.sql.SQLException;

import static com.project.tubespbokel4.MainApplication.*;

public class LoginController {
    private LoginFrame frame;
    private UserDAO userDAO;
    public LoginController(LoginFrame frame){
        this.frame = frame;
        this.userDAO = new UserDAO();
    }

    public void onBtnLoginNowClicked(){
        String msg = "";

        if(this.frame.getEtUsername().getText().isBlank()) msg = "Kolom 'Username' tidak boleh kosong!";
        else if(this.frame.getEtPassword().getText().isBlank()) msg = "Kolom 'Password' tidak boleh kosong!";
        else{
            try {
                User user = this.userDAO.login(this.frame.getEtUsername().getText().trim(), this.frame.getEtPassword().getText().trim());
                if (user != null){
                    authentication.setSession(user);
                    directToDashboard(user.getRole());
                }else{
                    msg = "Username atau Password Salah!";
                }
            } catch (SQLException e) {
                msg = e.getMessage();
            }
        }
        if(msg.isBlank()){
            this.frame.getTxtErrorMsg().setVisible(false);
//            System.out.println("Login Berhasil");
            this.frame.getEtUsername().clear();
            this.frame.getEtPassword().clear();
            dialogController.dialogInfomation("Login Berhasil");
        }else{
            this.frame.getTxtErrorMsg().setVisible(true);
            this.frame.getTxtErrorMsg().setText(msg);
            dialogController.dialogError("Login Error!", msg);
        }

    }

    public void onRegisterNowClicked(){
        this.userDAO.closeConnection();
        screenController.changeScreen(RegisterMemberFrame.screenURL);
    }

}
