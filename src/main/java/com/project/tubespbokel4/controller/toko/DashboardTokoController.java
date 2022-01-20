package com.project.tubespbokel4.controller.toko;

import com.project.tubespbokel4.model.auth.UserStore;
import com.project.tubespbokel4.model.auth.UserStoreDAO;
import com.project.tubespbokel4.model.toko.Product;
import com.project.tubespbokel4.model.toko.ProductDAO;
import com.project.tubespbokel4.view.toko.DashboardTokoFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoItem;
import com.project.tubespbokel4.view.toko.ItemDetailsFrame;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.screenController;

public class DashboardTokoController {
    private DashboardTokoFrame frame;

    private UserStore userStore;
    private UserStoreDAO userStoreDAO;

    private ArrayList<Product> products;
    private ObservableList<Product> productObservableList;
    private ProductDAO productDAO;

    public DashboardTokoController(DashboardTokoFrame frame){
        this.frame = frame;
        this.userStoreDAO = new UserStoreDAO();
        this.productDAO = new ProductDAO();
        try {
            this.userStore = this.userStoreDAO.getUserFromId(authentication.getSession().getId());
            this.products = this.productDAO.getProductsByStoreId(this.userStore.getId());
            this.productObservableList = FXCollections.observableList(this.products.stream().toList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setup(){
        this.frame.getTxtNamaToko().setText(this.userStore.getStoreName());
        this.frame.getTxtStoreDesc().setText(this.userStore.getDescription());
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
