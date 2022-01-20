package com.project.tubespbokel4.view.admin;

import com.project.tubespbokel4.controller.admin.DashboardAdminController;
import com.project.tubespbokel4.model.auth.WalletLog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;

public class DashboardAdminFrame implements Initializable {
    public static String screenURL = "/admin/dashboard-admin.fxml";
    private DashboardAdminController controller;

    @FXML
    private ListView<WalletLog> lvWalletRequest;

    @FXML
    private Label txtWelcomeUserName;

    public DashboardAdminFrame(){
        controller = new DashboardAdminController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtWelcomeUserName.setText("Welcom back, "+authentication.getSession().getName());
        controller.setup();
    }

    public ListView<WalletLog> getLvWalletRequest() {
        return lvWalletRequest;
    }
}
