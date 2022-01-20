package com.project.tubespbokel4.view.toko;

import com.project.tubespbokel4.controller.toko.DashboardTokoController;
import com.project.tubespbokel4.model.toko.Product;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

import static com.project.tubespbokel4.MainApplication.screenController;

public class DashboardTokoFrame implements Initializable {
    public static String screenURL = "/toko/dashboard-toko.fxml";
    private DashboardTokoController controller;

    @FXML
    private Button btnTambahBarang;

    @FXML
    private ListView<Product> lvItems;

    @FXML
    private Label txtNamaToko;

    @FXML
    private Label txtStoreDesc;

    public DashboardTokoFrame(){
        this.controller = new DashboardTokoController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller.setup();
    }

    @FXML
    void onBtnTambahBarangClicked(MouseEvent event) {
        screenController.changeToNextFrame(ItemDetailsAddEditFrame.screenURL, new ItemDetailsAddEditFrame(null));
    }

    public ListView<Product> getLvItems() {
        return lvItems;
    }

    public Label getTxtNamaToko() {
        return txtNamaToko;
    }

    public Label getTxtStoreDesc() {
        return txtStoreDesc;
    }
}
