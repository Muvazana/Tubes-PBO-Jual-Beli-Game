package com.project.tubespbokel4.view.auth;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWalletDialogFrame implements Initializable {

    @FXML
    private ComboBox<Integer> cbWallet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cbWallet.getItems().addAll(
                5000,
                10000,
                25000,
                50000,
                100000,
                500000,
                1000000
        );
        this.cbWallet.setValue(5000);
    }

    public ComboBox<Integer> getCbWallet() {
        return cbWallet;
    }
}
