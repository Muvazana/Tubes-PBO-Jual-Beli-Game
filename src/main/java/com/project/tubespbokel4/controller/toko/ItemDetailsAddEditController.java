package com.project.tubespbokel4.controller.toko;

import com.project.tubespbokel4.MainApplication;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.auth.User;
import com.project.tubespbokel4.model.auth.UserStore;
import com.project.tubespbokel4.model.toko.Product;
import com.project.tubespbokel4.model.toko.ProductDAO;
import com.project.tubespbokel4.model.toko.ProductType;
import com.project.tubespbokel4.view.toko.DashboardTokoFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoRootFrame;
import com.project.tubespbokel4.view.toko.ItemDetailsAddEditFrame;
import com.project.tubespbokel4.view.toko.ItemDetailsFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.sql.SQLException;

import static com.project.tubespbokel4.MainApplication.*;

public class ItemDetailsAddEditController {
    private ItemDetailsAddEditFrame frame;
    private ProductDAO productDAO;
    private Product product;

    public ItemDetailsAddEditController(ItemDetailsAddEditFrame frame, Product product){
        this.frame = frame;
        this.productDAO = new ProductDAO();
        this.product = product;
    }

    public void setup(){
        this.frame.getEtItemPrice().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    frame.getEtItemPrice().setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        this.frame.getEtItemStock().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    frame.getEtItemStock().setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        this.frame.getCbItemType().getItems().addAll(
                "Wallet",
                "Game Item",
                "Game"
        );

        if (this.product != null) {
            this.frame.getTxtTittle().setText("Edit Item Details");
            this.frame.getEtItemName().setText(this.product.getName());
            this.frame.getCbItemType().setValue(this.product.getSimpleNameType());
            this.frame.getEtItemPrice().setText(""+this.product.getPrice());
            this.frame.getEtItemStock().setText(""+this.product.getStock());
            this.frame.getEtItemDescription().setText(this.product.getDescription());
            this.frame.getBtnAddEdit().setText("Save");
            this.frame.getBtnDelete().setVisible(true);
        }else{
            this.frame.getTxtTittle().setText("Add Item Details");
            this.frame.getBtnAddEdit().setText("Add Item");
            this.frame.getBtnDelete().setVisible(false);
        }
    }

    public void onBtnAddEditClicked(){
        String msg = "";
        if(this.frame.getEtItemName().getText().isBlank()) msg = "Kolom 'Item Name' tidak boleh kosong!";
        else if(this.frame.getCbItemType().getSelectionModel().getSelectedItem() == null) msg = "Kolom 'Item Type' Belum dipilih!";
        else if(this.frame.getEtItemPrice().getText().isBlank()) msg = "Kolom 'Item Price' tidak boleh kosong!";
        else if(this.frame.getEtItemStock().getText().isBlank()) msg = "Kolom 'Item Stock' tidak boleh kosong!";
        else{
            Product tempProduct = this.product;
            if (this.product == null){
                tempProduct = new Product();
                tempProduct.setStore_id(authentication.getSession().getId());
                tempProduct.setRate("0");
            }

            tempProduct.setName(this.frame.getEtItemName().getText().trim());
            String itemType = this.frame.cbItems.get(this.frame.getCbItemType().getSelectionModel().getSelectedItem());
            tempProduct.setType(ProductType.valueOf(itemType));
            tempProduct.setPrice(Integer.parseInt(this.frame.getEtItemPrice().getText().trim()));
            tempProduct.setStock(Integer.parseInt(this.frame.getEtItemStock().getText().trim()));
            tempProduct.setDescription(this.frame.getEtItemDescription().getText().trim());
            try {
                if(this.product == null){
                    int id = this.productDAO.insert(tempProduct);
                    if (id != -1){
                        dialogController.dialogInfomation("Berhasil menambahkan Product-"+tempProduct.getName());
                        screenController.changeToBackFrame(new ItemDetailsFrame(this.getProduct()));
                    }else{
                        msg = "Terjadi Kesalahan! Gagal menambahkan Item Produk";
                    }
                }else{
                    boolean isSuccess = this.productDAO.update(tempProduct);
                    if (isSuccess){
                        this.product = tempProduct;
                        dialogController.dialogInfomation("Edit Detail Product-"+tempProduct.getName()+" Berhasil");
                        screenController.changeToBackFrame(new ItemDetailsFrame(this.getProduct()));
                    }else{
                        msg = "Terjadi Kesalahan!";
                    }
                }
            } catch (SQLException e) {
                msg = e.getMessage();
            }
        }
        this.frame.setErrorMsg(msg);
    }

    public void onBtnDeleteClicked(){
        if(this.product != null){
            try {
                boolean isSuccess = this.productDAO.delete(this.product.getId());
                if (isSuccess){
                    dialogController.dialogInfomation("Berhasil menghapus data Product-"+this.product.getName());
                    screenController.changeToFirstFrame();
                }
            } catch (SQLException e) {
                dialogController.dialogError("Gagal menghapus Product", e.getMessage());
            }
        }
    }

    public Product getProduct() {
        return product;
    }
}
