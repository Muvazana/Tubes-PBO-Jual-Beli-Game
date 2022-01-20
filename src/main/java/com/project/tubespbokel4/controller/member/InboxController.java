package com.project.tubespbokel4.controller.member;

import com.project.tubespbokel4.listener.InboxListViewItemListener;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserDAO;
import com.project.tubespbokel4.model.toko.*;
import com.project.tubespbokel4.view.member.InboxFrame;
import com.project.tubespbokel4.view.member.InboxItem;
import com.project.tubespbokel4.view.member.RatingProductDialogFrame;
import com.project.tubespbokel4.view.toko.ApprovedRedeemCodeDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.dialogController;

public class InboxController implements InboxListViewItemListener {
    private InboxFrame frame;

    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    private ProductDAO productDAO;

    private ArrayList<Transaction> transactions;
    private ObservableList<Transaction> transactionObservableList;

    private TransactionStatus stateStatus;

    public InboxController(InboxFrame frame){
        this.frame = frame;
        this.transactionDAO = new TransactionDAO();
        this.userDAO = new UserDAO();
        this.productDAO = new ProductDAO();
        this.updateTransaction();
        stateStatus = TransactionStatus.member_request;
    }

    public void setup(){
        this.frame.getCbStatus().getItems().addAll(
                "Member Request",
                "Member Cancel",
                "Seller Approved",
                "Seller Rejected",
                "Done"
        );
        this.frame.getCbStatus().setValue("Member Request");
        this.updateListView();
        this.frame.getCbStatus().valueProperty().addListener((observableValue, s, t1) -> {
//            System.out.println(this.frame.cbStatusItems.get(observableValue.getValue()));
            stateStatus = TransactionStatus.valueOf(this.frame.cbStatusItems.get(observableValue.getValue()));
            this.updateListView();
        });
    }
    private void updateTransaction(){
        try {
            if (authentication.getSession().getRole() == Roles.member){
                this.transactions = this.transactionDAO.getTransactionByMemberId(authentication.getSession().getId());
            }else{
                this.transactions = this.transactionDAO.getTransactionByStoreId(authentication.getSession().getId());
            }
        } catch (SQLException e) {
            dialogController.dialogError("SQLException", e.getMessage());
        }
    }
    private void updateListView(){
        this.transactionObservableList = FXCollections.observableList(
                this.transactions.stream().filter(
                        this.getFilterByStatus(stateStatus)
                ).toList()
        );
        this.frame.getLvInbox().setItems(this.transactionObservableList);
        this.frame.getLvInbox().setCellFactory(transactionListView -> new InboxItem(this));
    }

    private Predicate<Transaction> getFilterByStatus(TransactionStatus status){
        return transaction -> transaction.getStatus() == status;
    }

    @Override
    public void onBtnApproveDoneClicked(Transaction transaction) {
        try{
            if (authentication.getSession().getRole() == Roles.seller){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/toko/approved-redeem-code-dialog.fxml"));
                DialogPane parent = fxmlLoader.load();
                parent.setExpandableContent(null);
                ApprovedRedeemCodeDialog frame = fxmlLoader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(parent);
                dialog.setTitle("Approve Redeem Code");

                Optional<ButtonType> button = dialog.showAndWait();
                if(button.get() == ButtonType.APPLY){
                    transaction.setRedeeom_code(frame.getEtRedeemCode().getText().isBlank() ? null : frame.getEtRedeemCode().getText().trim());
                    transaction.setStatus(TransactionStatus.seller_approved);
                    boolean isSuccess = transactionDAO.update(transaction);
                    if (isSuccess){
                        dialogController.dialogInfomation("Approve Berhasil!");
                    }
                    dialog.close();
                }else{
                    dialog.close();
                }
            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/rating-product-dialog.fxml"));
                DialogPane parent = fxmlLoader.load();
                parent.setExpandableContent(null);
                RatingProductDialogFrame frame = fxmlLoader.getController();

                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(parent);
                dialog.setTitle("Rating Product");

                Optional<ButtonType> button = dialog.showAndWait();
                if(button.get() == ButtonType.OK){
                    transaction.setProduct_rate(frame.getCbRate().getSelectionModel().getSelectedItem());
                    transaction.setStatus(TransactionStatus.done);
                    boolean isSuccess = transactionDAO.update(transaction);
                    if (isSuccess){
                        User seller = userDAO.getUserFromId(transaction.getSeller().getId());
                        Product product = productDAO.getProductById(transaction.getProduct_id());
                        seller.setWallet(seller.getWallet()+product.getPrice());
                        product.setRate(transactionDAO.getFinalProductRate(transaction.getProduct_id())+"");
                        if (productDAO.update(product) && userDAO.update(seller)){
                            dialogController.dialogInfomation("Transaksi Berhasil!");
                        }
                    }
                    dialog.close();
                }else{
                    dialog.close();
                }
            }
        } catch (IOException e) {
            dialogController.dialogError("IOException", e.getMessage());
        } catch (SQLException e) {
            dialogController.dialogError("SQLException", e.getMessage());
        }
        this.updateTransaction();
        this.updateListView();
    }

    @Override
    public void onBtnRejectCancelClicked(Transaction transaction) {
        if (authentication.getSession().getRole() == Roles.seller){
            transaction.setStatus(TransactionStatus.seller_rejected);
        }else{
            transaction.setStatus(TransactionStatus.member_cancel);
        }

        try {
            boolean isSuccess = transactionDAO.update(transaction);
            if (isSuccess){
                User member = userDAO.getUserFromId(transaction.getUser_member_id());
                member.setWallet(member.getWallet()+transaction.getTotal_price());
                Product product = productDAO.getProductById(transaction.getProduct_id());
                product.setStock(product.getStock()+1);
                if (productDAO.update(product) && userDAO.update(member)){
                    if (authentication.getSession().getRole() == Roles.member)
                        authentication.updateSession(member);
                }
            }
        } catch (SQLException e) {
            dialogController.dialogError("SQLException", e.getMessage());
        }
        this.updateTransaction();
        this.updateListView();
    }
}
