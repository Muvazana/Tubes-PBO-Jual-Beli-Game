package com.project.tubespbokel4.view.toko;

import com.project.tubespbokel4.model.toko.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class DashboardTokoItem extends ListCell<Product> {

    @FXML
    private AnchorPane apFrame;

    @FXML
    private ImageView ivItemImage;

    @FXML
    private Label txtDescription;

    @FXML
    private Label txtItemName;

    @FXML
    private Label txtItemType;

    @FXML
    private Label txtPrice;

    @FXML
    private Label txtRate;

    private FXMLLoader mLLoader;
    {
        setStyle("-fx-padding: 0px 0px 6px 0px");
    }

    @Override
    protected void updateItem(Product product, boolean b) {
        super.updateItem(product, b);

        if(b || product == null) {
            setText(null);
            setGraphic(null);
        } else {
            if (this.mLLoader == null) {
                this.mLLoader = new FXMLLoader(getClass().getResource("/toko/dashboard-toko-item.fxml"));
                this.mLLoader.setController(this);

                try {
                    this.mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            this.ivItemImage.setImage(product.getProductImage());
            this.txtItemName.setText(product.getName());
            this.txtItemType.setText(product.getSimpleNameType());
            this.txtPrice.setText("Rp. "+product.getPrice());
            this.txtDescription.setText(product.getDescription());
            this.txtRate.setText(product.getRate());

            setText(null);
            setGraphic(apFrame);
        }
    }
}
