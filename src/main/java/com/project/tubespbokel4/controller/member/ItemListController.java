package com.project.tubespbokel4.controller.member;

import com.project.tubespbokel4.model.auth.UserStoreDAO;
import com.project.tubespbokel4.model.toko.Product;
import com.project.tubespbokel4.model.toko.ProductDAO;
import com.project.tubespbokel4.model.toko.ProductType;
import com.project.tubespbokel4.view.member.ItemListFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoItem;
import com.project.tubespbokel4.view.toko.ItemDetailsFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.project.tubespbokel4.MainApplication.dialogController;
import static com.project.tubespbokel4.MainApplication.screenController;

public class ItemListController {
    private ItemListFrame frame;

    private ProductDAO productDAO;
    private ProductType productType;
    private ObservableList<Product> productObservableList;

    public ItemListController(ItemListFrame frame, ProductType productType){
        this.frame = frame;
        this.productDAO = new ProductDAO();
        this.productType = productType;
        try{
            ArrayList<Product> products = this.productDAO.getProductsByType(productType);
            this.productObservableList = FXCollections.observableList(products.stream().toList());
        } catch (SQLException e) {
            dialogController.dialogError("SQL Error!", e.getMessage());
        }
    }

    public void setup(){
        String title = switch (this.productType){
            case wallet_code -> "Voucher Wallet";
            case game_code -> "Games";
            case game_item -> "Game Items";
            default -> "Error";
        };

        this.frame.getTxtTitle().setText(title);
        this.frame.getLvItems().setItems(this.productObservableList);
        this.frame.getLvItems().setCellFactory(productListView -> new DashboardTokoItem());
        this.frame.getLvItems().getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observableValue, Product product, Product t1) {
                screenController.changeToNextFrame(ItemDetailsFrame.screenURL, new ItemDetailsFrame(observableValue.getValue()));
            }
        });
    }
}
