package com.project.tubespbokel4.view.toko;

import com.project.tubespbokel4.controller.toko.ItemDetailsController;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.toko.Product;
import com.project.tubespbokel4.view.member.ItemListFrame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.authentication;
import static com.project.tubespbokel4.MainApplication.screenController;

public class ItemDetailsFrame implements Initializable {
    public static String screenURL = "/toko/item-details.fxml";
    private ItemDetailsController controller;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnBuyNow;

    @FXML
    private Button btnEditItemDetails;

    @FXML
    private ImageView ivItemImage;

    @FXML
    private Pane paneSubBuyNow;

    @FXML
    private Label txtItemDescription;

    @FXML
    private Label txtItemName;

    @FXML
    private Label txtItemPrice;

    @FXML
    private Label txtItemRate;

    @FXML
    private Label txtItemSold;

    @FXML
    private Label txtItemStock;

    @FXML
    private Label txtItemType;

    @FXML
    private Label txtStoreName;

    @FXML
    private Label txtSubPrice;

    @FXML
    private Label txtSubTax;

    @FXML
    private Label txtSubTotal;

    public ItemDetailsFrame(Product product){
        controller = new ItemDetailsController(this, product);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(authentication.getSession().getRole() == Roles.seller){
            this.txtStoreName.setVisible(false);
            this.paneSubBuyNow.setVisible(false);
        }else{
            this.btnEditItemDetails.setVisible(false);
        }
        this.controller.setup();
    }

    @FXML
    void onBtnBuyNowClicked(MouseEvent event) {
//        this.controller.();
    }

    @FXML
    void onBtnEditItemDetailsClicked(MouseEvent event) {

    }

    @FXML
    void onBtnBackClicked(MouseEvent event) {
        if(authentication.getSession().getRole() == Roles.seller){
            screenController.changeToBackFrame(null);
        }else{
            screenController.changeToBackFrame(new ItemListFrame(this.controller.getProduct().getType()));
        }
    }

    public Button getBtnBuyNow() {
        return btnBuyNow;
    }

    public Button getBtnEditItemDetails() {
        return btnEditItemDetails;
    }

    public ImageView getIvItemImage() {
        return ivItemImage;
    }

    public Pane getPaneSubBuyNow() {
        return paneSubBuyNow;
    }

    public Label getTxtItemDescription() {
        return txtItemDescription;
    }

    public Label getTxtItemName() {
        return txtItemName;
    }

    public Label getTxtItemPrice() {
        return txtItemPrice;
    }

    public Label getTxtItemRate() {
        return txtItemRate;
    }

    public Label getTxtItemSold() {
        return txtItemSold;
    }

    public Label getTxtItemStock() {
        return txtItemStock;
    }

    public Label getTxtItemType() {
        return txtItemType;
    }

    public Label getTxtSubPrice() {
        return txtSubPrice;
    }

    public Label getTxtSubTax() {
        return txtSubTax;
    }

    public Label getTxtSubTotal() {
        return txtSubTotal;
    }

    public Label getTxtStoreName() {
        return txtStoreName;
    }

}
