package com.project.tubespbokel4.view.admin;

import com.project.tubespbokel4.view.auth.EditProfileFrame;
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

public class DashboardAdminRootFrame implements Initializable{
    public static String screenURL = "/admin/dashboard-admin-root.fxml";
//    private String states[] = {DashboardAdminFrame.screenURL, EditProfileFrame.screenURL} ;

    @FXML
    private AnchorPane apRootFrame;

    @FXML
    private Button btnDashboard;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnProfile;

    @FXML
    private Label txtUserName;

    @FXML
    private Label txtUserType;

    @FXML
    void onBtnLogoutclicked(MouseEvent event) {
        authentication.clearSession();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authentication.setRoot(this);
        txtUserName.setText(authentication.getSession().getName());
        txtUserType.setText(authentication.getSession().getSimpleNameRole());

        screenController.changeTab(this.apRootFrame, DashboardAdminFrame.screenURL);
        btnDashboard.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, DashboardAdminFrame.screenURL);
        });
        btnProfile.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, EditProfileFrame.screenURL);
        });
    }

    public Label getTxtUserName() {
        return txtUserName;
    }

    public Label getTxtUserType() {
        return txtUserType;
    }
}