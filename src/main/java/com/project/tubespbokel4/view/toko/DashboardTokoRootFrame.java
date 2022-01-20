package com.project.tubespbokel4.view.toko;

import com.project.tubespbokel4.view.auth.EditProfileFrame;
import com.project.tubespbokel4.view.member.InboxFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.screenController;

public class DashboardTokoRootFrame implements Initializable {
    public static String screenURL = "/toko/dashboard-toko-root.fxml";
//    private String states[] = {DashboardTokoFrame.screenURL, EditProfileFrame.screenURL} ;

    @FXML
    private AnchorPane apRootFrame;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnInbox;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnProfile;

    @FXML
    private Label txtUserName;

    @FXML
    private Label txtUserType;

    @FXML
    private Label txtWallet;

    public DashboardTokoRootFrame(){

    }

    @FXML
    void onBtnLogoutclicked(MouseEvent event) {
        authentication.clearSession();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authentication.setRoot(this);
        txtUserName.setText(authentication.getSession().getName());
        txtUserType.setText(authentication.getSession().getSimpleNameRole());
        txtWallet.setText("Wallet : Rp. "+authentication.getSession().getWallet());

        screenController.changeTab(this.apRootFrame, DashboardTokoFrame.screenURL);
        btnDashboard.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, DashboardTokoFrame.screenURL);
        });
        btnProfile.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, EditProfileFrame.screenURL);
        });
        btnInbox.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, InboxFrame.screenURL);
        });
    }

    public Label getTxtUserName() {
        return txtUserName;
    }

    public Label getTxtUserType() {
        return txtUserType;
    }

    public Label getTxtWallet() {
        return txtWallet;
    }

    public AnchorPane getApRootFrame() {
        return apRootFrame;
    }
}
