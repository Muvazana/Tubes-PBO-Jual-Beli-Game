package com.project.tubespbokel4.view.member;

import com.project.tubespbokel4.listener.AdminListViewItemListener;
import com.project.tubespbokel4.listener.InboxListViewItemListener;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.WalletLog;
import com.project.tubespbokel4.model.auth.WalletLogStatus;
import com.project.tubespbokel4.model.toko.Transaction;
import com.project.tubespbokel4.model.toko.TransactionStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;

import static com.project.tubespbokel4.MainApplication.authentication;

public class InboxItem extends ListCell<Transaction> {
    private InboxListViewItemListener listener;

    @FXML
    private AnchorPane apFrame;

    @FXML
    private Button btnApproveDone;

    @FXML
    private Button btnCopyNote;

    @FXML
    private Button btnCopyRedeemCode;

    @FXML
    private Button btnRejectCancel;

    @FXML
    private Label txtLabelMemberSeller;

    @FXML
    private Label txtNote;

    @FXML
    private Label txtProductName;

    @FXML
    private Label txtRedeemCode;

    @FXML
    private Label txtStatus;

    @FXML
    private Label txtTotalPrice;

    @FXML
    private Label txtUserName;

    private FXMLLoader mLLoader;
    {
        setStyle("-fx-padding: 0px 0px 6px 0px");
    }

    public InboxItem(InboxListViewItemListener listener){
        this.listener = listener;
    }

    @Override
    protected void updateItem(Transaction transaction, boolean b) {
        super.updateItem(transaction, b);

        if(b || transaction == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (this.mLLoader == null) {
                this.mLLoader = new FXMLLoader(getClass().getResource("/member/inbox-item.fxml"));
                this.mLLoader.setController(this);

                try {
                    this.mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            this.txtProductName.setText(transaction.getProduct().getName());
            if(authentication.getSession().getRole() == Roles.seller){
                this.txtLabelMemberSeller.setText("Member");
                this.txtUserName.setText(transaction.getMember().getName());
                this.btnApproveDone.setText("Approve");
                this.btnRejectCancel.setText("Reject");
            }else{
                this.txtLabelMemberSeller.setText("Seller");
                this.txtUserName.setText(transaction.getSeller().getStoreName());
                this.btnApproveDone.setText("Done");
                this.btnRejectCancel.setText("Cancel");
            }
            this.txtTotalPrice.setText("Rp. "+transaction.getTotal_price());
            this.txtNote.setText((transaction.getNote() == null) ? "-" : transaction.getNote());
            this.txtRedeemCode.setText((transaction.getRedeeom_code() == null) ? "-" : transaction.getRedeeom_code());
            this.txtStatus.setText(transaction.getSimpleNameStatus());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            this.btnCopyNote.setOnMouseClicked(mouseEvent -> {
                StringSelection stringSelection = new StringSelection(this.txtNote.getText());
                clipboard.setContents(stringSelection, null);
            });
            this.btnCopyRedeemCode.setOnMouseClicked(mouseEvent -> {
                StringSelection stringSelection = new StringSelection(this.txtRedeemCode.getText());
                clipboard.setContents(stringSelection, null);
            });

            if (transaction.getStatus() == TransactionStatus.done ||
                    transaction.getStatus() == TransactionStatus.member_cancel ||
                    transaction.getStatus() == TransactionStatus.seller_rejected ||
                    (authentication.getSession().getRole() == Roles.seller && transaction.getStatus() == TransactionStatus.seller_approved)){
                this.btnApproveDone.setVisible(false);
                this.btnRejectCancel.setVisible(false);
            }else{
                if (authentication.getSession().getRole() == Roles.member ){
                    if(transaction.getStatus() == TransactionStatus.seller_approved){
                        this.btnApproveDone.setVisible(true);
                        this.btnRejectCancel.setVisible(false);
                    }else if(transaction.getStatus() == TransactionStatus.member_request){
                        this.btnApproveDone.setVisible(false);
                        this.btnRejectCancel.setVisible(true);
                    }
                }
                this.btnApproveDone.setOnMouseClicked(mouseEvent -> {
                    this.listener.onBtnApproveDoneClicked(transaction);
                });
                this.btnRejectCancel.setOnMouseClicked(mouseEvent -> {
                    this.listener.onBtnRejectCancelClicked(transaction);
                });
            }
            setText(null);
            setGraphic(apFrame);
        }
    }

}
