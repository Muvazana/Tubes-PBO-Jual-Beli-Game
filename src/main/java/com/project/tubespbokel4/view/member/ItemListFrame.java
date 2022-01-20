package com.project.tubespbokel4.view.member;

import com.project.tubespbokel4.controller.member.ItemListController;
import com.project.tubespbokel4.model.toko.Product;
import com.project.tubespbokel4.model.toko.ProductType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.screenController;

public class ItemListFrame implements Initializable {
    public static String screenURL = "/member/item-list.fxml";
    private ItemListController controller;

    @FXML
    private Button btnBack;

    @FXML
    private ListView<Product> lvItems;

    @FXML
    private Label txtTitle;

    public ItemListFrame(ProductType productType){
        this.controller = new ItemListController(this, productType);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller.setup();
    }

    @FXML
    void onBtnBackClicked(MouseEvent event) {
        screenController.changeToBackFrame(null);
    }

    public ListView<Product> getLvItems() {
        return lvItems;
    }

    public Label getTxtTitle() {
        return txtTitle;
    }
}
