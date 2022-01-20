package com.project.tubespbokel4.view.admin;

import com.project.tubespbokel4.listener.AdminListViewItemListener;
import com.project.tubespbokel4.model.auth.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;

public class DashboardAdminItem extends ListCell<WalletLog> {
    private AdminListViewItemListener listener;

    @FXML
    private AnchorPane apFrame;

    @FXML
    private Button btnApprove;

    @FXML
    private Button btnReject;

    @FXML
    private Label txtName;

    @FXML
    private Label txtStatus;

    @FXML
    private Label txtWallet;

    private FXMLLoader mLLoader;
    {
        setStyle("-fx-padding: 0px 0px 6px 0px");
    }

    public DashboardAdminItem(AdminListViewItemListener listener){
        this.listener = listener;
    }

    @Override
    protected void updateItem(WalletLog walletLog, boolean b) {
        super.updateItem(walletLog, b);

        if(b || walletLog == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (this.mLLoader == null) {
                this.mLLoader = new FXMLLoader(getClass().getResource("/admin/dashboard-admin-item.fxml"));
                this.mLLoader.setController(this);

                try {
                    this.mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            this.txtName.setText(walletLog.getMember().getName());
            this.txtWallet.setText(walletLog.getWallet()+"");
            this.txtStatus.setText(walletLog.getStatus().toString());

            this.btnApprove.setDisable(walletLog.getStatus() != WalletLogStatus.request);
            this.btnReject.setDisable(walletLog.getStatus() != WalletLogStatus.request);
            this.btnApprove.setOnMouseClicked(mouseEvent -> {
                this.listener.onApprovaClicked(walletLog);
            });
            this.btnReject.setOnMouseClicked(mouseEvent -> {
                this.listener.onRejectClicked(walletLog);
            });

            setText(null);
            setGraphic(apFrame);
        }
    }

//    public boolean onBtnApproveClicked(WalletLog walletLog){
//        UserDAO userDAO = new UserDAO();
//        WalletLogDAO walletLogDAO = new WalletLogDAO();
//        boolean isSuccess;
//        try {
//            User tempUser = userDAO.getUserFromId(walletLog.getUser_req_id());
//            tempUser.setWallet(tempUser.getWallet()+walletLog.getWallet());
//            walletLog.setStatus(WalletLogStatus.approved);
//
//            userDAO.getConnection().setAutoCommit(false);
//            walletLogDAO.getConnection().setAutoCommit(false);
//
//            userDAO.update(tempUser);
//            walletLogDAO.update(walletLog);
//
//            userDAO.getConnection().commit();
//            walletLogDAO.getConnection().commit();
//            isSuccess = true;
//        } catch (SQLException e) {
//            try {
//                userDAO.getConnection().rollback();
//                walletLogDAO.getConnection().rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//            isSuccess = false;
//        }
//        userDAO.closeConnection();
//        walletLogDAO.closeConnection();
//        return isSuccess;
//    }
//    public boolean onBtnRejectClicked(WalletLog walletLog){
//        WalletLogDAO walletLogDAO = new WalletLogDAO();
//        boolean isSuccess;
//        try {
//            walletLog.setStatus(WalletLogStatus.rejected);
//            walletLogDAO.getConnection().setAutoCommit(false);
//            walletLogDAO.update(walletLog);
//            walletLogDAO.getConnection().commit();
//            isSuccess = true;
//        } catch (SQLException e) {
//            try {
//                walletLogDAO.getConnection().rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            e.printStackTrace();
//            isSuccess = false;
//        }
//        walletLogDAO.closeConnection();
//        return isSuccess;
//    }
}
