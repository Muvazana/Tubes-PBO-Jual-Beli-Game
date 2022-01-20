package com.project.tubespbokel4.view.member;

import com.project.tubespbokel4.model.toko.ProductType;
import com.project.tubespbokel4.view.toko.ItemDetailsFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.screenController;

public class DashboardMemberFrame implements Initializable {
    public static String screenURL = "/member/dashboard-member.fxml";

    @FXML
    private Pane btnGameItems;

    @FXML
    private Pane btnGames;

    @FXML
    private Pane btnWallets;

    @FXML
    private Label txtWelcomeUserName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtWelcomeUserName.setText("Welcom back, "+authentication.getSession().getName());
    }

    @FXML
    void onBtnGameItemsClicked(MouseEvent event) {
        screenController.changeToNextFrame(ItemListFrame.screenURL, new ItemListFrame(ProductType.game_item));
    }

    @FXML
    void onBtnGamesClicked(MouseEvent event) {
        screenController.changeToNextFrame(ItemListFrame.screenURL, new ItemListFrame(ProductType.game_code));
    }

    @FXML
    void onBtnWalletsClicked(MouseEvent event) {
        screenController.changeToNextFrame(ItemListFrame.screenURL, new ItemListFrame(ProductType.wallet_code));
    }

}
