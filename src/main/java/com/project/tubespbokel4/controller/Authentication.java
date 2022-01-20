package com.project.tubespbokel4.controller;

import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.view.admin.DashboardAdminRootFrame;
import com.project.tubespbokel4.view.auth.LoginFrame;
import com.project.tubespbokel4.view.member.DashboardMemberRootFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoRootFrame;
import javafx.scene.control.ButtonType;

import java.io.*;
import java.util.Optional;

import static com.project.tubespbokel4.MainApplication.dialogController;
import static com.project.tubespbokel4.MainApplication.screenController;

public class Authentication {
    private DashboardAdminRootFrame dashboardAdminRootFrame;
    private DashboardMemberRootFrame dashboardMemberRootFrame;
    private DashboardTokoRootFrame dashboardTokoRootFrame;

    private User user;

    private File userSessionFile;

    public Authentication() {
        userSessionFile = new File("UserSession.dat");
        this.user = null;
    }
    public void setSession(User user){
        try {
            this.user = user;
            this.saveSessionToFile(user);
        } catch (IOException e) {
            this.user = null;
            System.out.println("Gagal menyimpan User Session ke File UserSession.dat!");
            System.out.println("Error message : "+ e.getMessage());
        }
    }
    public void updateSession(User user){
        try {
            this.user = user;
            this.saveSessionToFile(user);
            if(dashboardAdminRootFrame != null){
                dashboardAdminRootFrame.getTxtUserName().setText(user.getName());
            }else if(dashboardMemberRootFrame != null){
                dashboardMemberRootFrame.getTxtUserName().setText(user.getName());
                dashboardMemberRootFrame.getTxtWallet().setText("Wallet : Rp. "+user.getWallet());
            }else if(dashboardTokoRootFrame != null){
                dashboardTokoRootFrame.getTxtUserName().setText(user.getName());
                dashboardTokoRootFrame.getTxtWallet().setText("Wallet : Rp. "+user.getWallet());
            }
        } catch (IOException e) {
            this.user = null;
            System.out.println("Gagal menyimpan User Session ke File UserSession.dat!");
            System.out.println("Error message : "+ e.getMessage());
        }
    }
    public boolean isUserExist(){
        return this.user != null;
    }
    public User getSession(){
        return this.user;
    }
    public void clearSession(){
        Optional<ButtonType> option = dialogController.dialogConfirm("Logout?");
        if(option.get() == ButtonType.OK){
            try {
                this.saveSessionToFile(null);
            }catch (IOException e) {
                System.out.println("Gagal menghapus User Session di File UserSession.dat!");
                System.out.println("Error message : "+ e.getMessage());
            }finally {
                this.user = null;
                screenController.clearScreenTabCache();
                screenController.changeScreen(LoginFrame.screenURL);
            }
        }
    }

    private void saveSessionToFile(User user) throws IOException{
        FileOutputStream fos = new FileOutputStream(this.userSessionFile);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        out.writeObject(user);
        out.close();
        fos.close();
    }

    public void loadSessionFromFile(){
        try {
            FileInputStream fis = new FileInputStream(this.userSessionFile);
            ObjectInputStream input = new ObjectInputStream(fis);
            User user = (User) input.readObject();
            input.close();
            fis.close();

            this.setSession(user);
        }catch (IOException | ClassNotFoundException e) {
            if(e.getClass().getSimpleName().equals("FileNotFoundException")){
                System.out.println("Tidak menemukan File UserSession.dat!");
                System.out.println("Mencoba membuat File UserSession.dat ...");
                try {
                    this.saveSessionToFile(null);
                } catch (IOException ex) {
                    System.out.println("File UserSession.dat Gagal dibuat!");
                }finally {
                    System.out.println("File UserSession.dat berhasil dibuat!");
                }
            }else{
                System.out.println("Gagal mengambil User Session dari File UserSession.dat!");
                System.out.println("Error message : "+ e.getMessage());
            }
        }
    }

    public void setRoot(DashboardAdminRootFrame dashboardAdminRootFrame){
        this.dashboardAdminRootFrame = dashboardAdminRootFrame;
    }
    public void setRoot(DashboardMemberRootFrame dashboardMemberRootFrame){
        this.dashboardMemberRootFrame = dashboardMemberRootFrame;
    }
    public void setRoot(DashboardTokoRootFrame dashboardTokoRootFrame){
        this.dashboardTokoRootFrame = dashboardTokoRootFrame;
    }
}
