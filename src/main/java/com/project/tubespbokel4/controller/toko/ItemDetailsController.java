package com.project.tubespbokel4.controller.toko;

import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserDAO;
import com.project.tubespbokel4.model.toko.*;
import com.project.tubespbokel4.view.member.TransactionNoteDialogFrame;
import com.project.tubespbokel4.view.toko.ApprovedRedeemCodeDialog;
import com.project.tubespbokel4.view.toko.ItemDetailsAddEditFrame;
import com.project.tubespbokel4.view.toko.ItemDetailsFrame;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static com.project.tubespbokel4.MainApplication.*;

public class ItemDetailsController {
    private ItemDetailsFrame frame;

    private TransactionDAO transactionDAO;

    private Product product;

    public ItemDetailsController(ItemDetailsFrame frame, Product product){
        this.frame = frame;
        this.product = product;
        transactionDAO = new TransactionDAO();
    }

    public void setup(){
        this.frame.getIvItemImage().setImage(this.product.getProductImage());
        this.frame.getTxtItemName().setText(this.product.getName());
        this.frame.getTxtItemType().setText("Type : "+this.product.getSimpleNameType());
        this.frame.getTxtItemPrice().setText("Rp. "+this.product.getPrice());
        this.frame.getTxtItemSold().setText("Sold : "+this.transactionDAO.getCountProductSold(this.product.getId()));
        this.frame.getTxtItemStock().setText("Stock : "+this.product.getStock());
        this.frame.getTxtItemRate().setText(this.product.getRate());
        this.frame.getTxtItemDescription().setText(this.product.getDescription());
        if(authentication.getSession().getRole() != Roles.seller){
            this.frame.getTxtSubPrice().setText(this.product.getPrice()+"");
            int subTax = (int)(this.product.getPrice()*0.1);
            int subTotal = this.product.getPrice()+subTax;
            this.frame.getTxtSubTax().setText(subTax+"");
            this.frame.getTxtSubTotal().setText(subTotal+"");
            this.frame.getTxtStoreName().setText("Seller : "+this.product.getSeller().getStoreName());
            this.frame.getBtnBuyNow().setOnMouseClicked(mouseEvent -> {
                UserDAO userDAO = new UserDAO();
                ProductDAO productDAO = new ProductDAO();
                Transaction transaction = new Transaction(
                        0,
                        authentication.getSession().getId(),
                        this.product.getId(),
                        subTotal,
                        null,
                        null,
                        0,
                        TransactionStatus.member_request
                );
                try {
                    User user = userDAO.getUserFromId(authentication.getSession().getId());
                    if(user.getWallet() >= subTotal){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/member/transaction-note-dialog.fxml"));
                        DialogPane parent = fxmlLoader.load();
                        parent.setExpandableContent(null);
                        TransactionNoteDialogFrame frame = fxmlLoader.getController();

                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(parent);
                        dialog.setTitle("Approve Redeem Code");

                        Optional<ButtonType> button = dialog.showAndWait();
                        if(button.get() == ButtonType.APPLY){
                            transaction.setNote(frame.getEtNote().getText().isBlank() ? null : frame.getEtNote().getText().trim());
                            transaction.setStatus(TransactionStatus.seller_approved);
                            int id = this.transactionDAO.insert(transaction);
                            if (id != -1){
                                Product product = productDAO.getProductById(this.product.getId());
                                product.setStock(product.getStock()-1);
                                user.setWallet(user.getWallet()-subTotal);
                                boolean isSuccess = userDAO.update(user) && productDAO.update(product);
                                if (isSuccess){
                                    authentication.updateSession(user);
                                    dialogController.dialogInfomation("Berhasil Membeli Product-"+product.getName());
                                }
                            }
                            dialog.close();
                        }else{
                            dialog.close();
                        }
                    }else{
                        dialogController.dialogWarning("Wallet tidak Cukup!");
                    }
                } catch (SQLException e) {
                    dialogController.dialogError(null, e.getMessage());
                } catch (IOException e) {
                    dialogController.dialogError(null, e.getMessage());
                } finally {
                    productDAO.closeConnection();
                    userDAO.closeConnection();
                }
            });
        }
        this.frame.getBtnEditItemDetails().setOnMouseClicked(mouseEvent -> {
            screenController.changeToNextFrame(ItemDetailsAddEditFrame.screenURL, new ItemDetailsAddEditFrame(this.product));
        });
    }

    public Product getProduct() {
        return product;
    }
}
