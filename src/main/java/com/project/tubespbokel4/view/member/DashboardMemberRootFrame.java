package com.project.tubespbokel4.view.member;

import com.project.tubespbokel4.model.auth.WalletLog;
import com.project.tubespbokel4.model.auth.WalletLogDAO;
import com.project.tubespbokel4.model.auth.WalletLogStatus;
import com.project.tubespbokel4.view.auth.AddWalletDialogFrame;
import com.project.tubespbokel4.view.auth.EditProfileFrame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.screenController;

public class DashboardMemberRootFrame implements Initializable {
    public static String screenURL = "/member/dashboard-member-root.fxml";
//    private String states[] = {DashboardMemberFrame.screenURL, EditProfileFrame.screenURL} ;

    @FXML
    private AnchorPane apRootFrame;

    @FXML
    private ImageView btnAddWallet;

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

    public DashboardMemberRootFrame(){

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

        screenController.changeTab(this.apRootFrame, DashboardMemberFrame.screenURL);
        btnDashboard.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, DashboardMemberFrame.screenURL);
        });
        btnProfile.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, EditProfileFrame.screenURL);
        });
        btnInbox.setOnMouseClicked(mouseEvent -> {
            screenController.changeTab(this.apRootFrame, InboxFrame.screenURL);
        });
        btnAddWallet.setOnMouseClicked(mouseEvent -> {
            WalletLogDAO walletLogDAO = new WalletLogDAO();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/auth/add-wallet-dialog.fxml"));
                DialogPane parent = fxmlLoader.load();
                parent.setExpandableContent(null);
                AddWalletDialogFrame frame = fxmlLoader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(parent);
                dialog.setTitle("Add Wallet");

                Optional<ButtonType> button = dialog.showAndWait();
                if(button.get() == ButtonType.APPLY){
                    walletLogDAO.insert(new WalletLog(
                            0,
                            authentication.getSession().getId(),
                            frame.getCbWallet().getSelectionModel().getSelectedItem(),
                            WalletLogStatus.request
                    ));
//                    System.out.println("Apply Wallet : "+ frame.getCbWallet().getSelectionModel().getSelectedItem());
                    dialog.close();
                }else{
                    dialog.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                walletLogDAO.closeConnection();
            }
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
}