package com.project.tubespbokel4.controller.admin;

import com.project.tubespbokel4.listener.AdminListViewItemListener;
import com.project.tubespbokel4.model.auth.*;
import com.project.tubespbokel4.view.admin.DashboardAdminFrame;
import com.project.tubespbokel4.view.admin.DashboardAdminItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Predicate;

import static com.project.tubespbokel4.MainApplication.dialogController;

public class DashboardAdminController implements AdminListViewItemListener {
    private DashboardAdminFrame frame;
    private WalletLogDAO walletLogDAO;
    private UserDAO userDAO;

    private ArrayList<WalletLog> walletLogs;
    private ObservableList<WalletLog> walletLogObservableList;

    private Predicate<WalletLog> filterWalletByStatus = walletLog -> walletLog.getStatus() == WalletLogStatus.request;

    public DashboardAdminController(DashboardAdminFrame frame){
        this.frame = frame;
        this.walletLogDAO = new WalletLogDAO();
        this.userDAO = new UserDAO();
        try {
            this.walletLogs = this.walletLogDAO.getAllWalletLogs();
            this.walletLogObservableList = FXCollections.observableList(this.walletLogs.stream().filter(filterWalletByStatus).toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setup(){
        this.frame.getLvWalletRequest().setItems(this.walletLogObservableList);
        this.frame.getLvWalletRequest().setCellFactory(walletLogListView -> new DashboardAdminItem(this));
    }

    @Override
    public void onApprovaClicked(WalletLog walletLog) {
        if (dialogController.dialogConfirm("Approve permintaan menambahkan wallet dengan akun-"+walletLog.getMember().getName()+" ?").get() == ButtonType.OK){
            boolean isSuccess;
            String msg = null;
            try {
                User tempUser = userDAO.getUserFromId(walletLog.getUser_req_id());
                tempUser.setWallet(tempUser.getWallet()+walletLog.getWallet());
                walletLog.setStatus(WalletLogStatus.approved);

                userDAO.getConnection().setAutoCommit(false);
                walletLogDAO.getConnection().setAutoCommit(false);

                userDAO.update(tempUser);
                walletLogDAO.update(walletLog);

                userDAO.getConnection().commit();
                walletLogDAO.getConnection().commit();
                isSuccess = true;
            } catch (SQLException e) {
                try {
                    userDAO.getConnection().rollback();
                    walletLogDAO.getConnection().rollback();
                } catch (SQLException ex) {}
                msg = e.getMessage();
                isSuccess = false;
            }
            if(isSuccess){
                walletLogObservableList = FXCollections.observableList(this.walletLogs.stream().filter(filterWalletByStatus).toList());
                this.setup();
                dialogController.dialogInfomation("Berhasil menambahkan Wallet dengan nominal "+walletLog.getWallet()+" ke akun-"+walletLog.getMember().getName());
            }else{
                dialogController.dialogError("Wallet gagal ditambahkan!", msg);
            }
        }
    }

    @Override
    public void onRejectClicked(WalletLog walletLog) {
        if (dialogController.dialogConfirm("Reject permintaan menambahkan wallet dengan akun-"+walletLog.getMember().getName()+" ?").get() == ButtonType.OK){
            boolean isSuccess;
            String msg = null;
            try {
                walletLog.setStatus(WalletLogStatus.rejected);
                walletLogDAO.getConnection().setAutoCommit(false);
                walletLogDAO.update(walletLog);
                walletLogDAO.getConnection().commit();
                isSuccess = true;
            } catch (SQLException e) {
                try {
                    walletLogDAO.getConnection().rollback();
                } catch (SQLException ex) {}
                msg = e.getMessage();
                isSuccess = false;
            }
            if(isSuccess){
                walletLogObservableList = FXCollections.observableList(this.walletLogs.stream().filter(filterWalletByStatus).toList());
                this.setup();
            }else{
                dialogController.dialogError("Wallet gagal ditambahkan!", msg);
            }
        }
    }

}
