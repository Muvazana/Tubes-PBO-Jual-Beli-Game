package com.project.tubespbokel4.view.toko;

import com.project.tubespbokel4.controller.toko.ItemDetailsAddEditController;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.model.toko.Product;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.*;

public class ItemDetailsAddEditFrame implements Initializable {
    public static String screenURL = "/toko/item-details-add-edit.fxml";
    public Map<String, String> cbItems = new HashMap<>(){{
        put("Wallet", "wallet_code");
        put("Game Item", "game_item");
        put("Game", "game_code");
    }};

    private ItemDetailsAddEditController controller;


    @FXML
    private Button btnAddEdit;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnDelete;

    @FXML
    private ComboBox<String> cbItemType;

    @FXML
    private TextArea etItemDescription;

    @FXML
    private TextField etItemName;

    @FXML
    private TextField etItemPrice;

    @FXML
    private TextField etItemStock;

    @FXML
    private Label txtErrorMsg;

    @FXML
    private Label txtTittle;


    public ItemDetailsAddEditFrame(Product product){
        this.controller = new ItemDetailsAddEditController(this, product);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setErrorMsg(null);
        this.controller.setup();
    }

    @FXML
    void onBtnBackClicked(MouseEvent event) {
        screenController.changeToBackFrame(new ItemDetailsFrame(controller.getProduct()));
    }

    @FXML
    void onBtnAddEditClicked(MouseEvent event) {
//        System.out.println(cbItems.get(cbItemType.getSelectionModel().getSelectedItem()));
        this.controller.onBtnAddEditClicked();
    }

    @FXML
    void onBtnDeleteClicked(MouseEvent event) {
        this.controller.onBtnDeleteClicked();
    }


    public void setErrorMsg(String msg){
        if(msg == null || msg.isBlank()){
            this.txtErrorMsg.setVisible(false);
        }else{
            this.txtErrorMsg.setVisible(true);
            this.txtErrorMsg.setText(msg);
            if(this.controller.getProduct() == null){
                dialogController.dialogError("Add Product Error!", msg);
            }else{
                dialogController.dialogError("Edit Product Error!", msg);
            }
        }
    }

    public Button getBtnAddEdit() {
        return btnAddEdit;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public ComboBox<String> getCbItemType() {
        return cbItemType;
    }

    public TextArea getEtItemDescription() {
        return etItemDescription;
    }

    public TextField getEtItemName() {
        return etItemName;
    }

    public TextField getEtItemPrice() {
        return etItemPrice;
    }

    public TextField getEtItemStock() {
        return etItemStock;
    }

    public Label getTxtErrorMsg() {
        return txtErrorMsg;
    }

    public Label getTxtTittle() {
        return txtTittle;
    }
}
