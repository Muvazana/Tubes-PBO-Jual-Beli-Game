package com.project.tubespbokel4.view.member;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RatingProductDialogFrame implements Initializable {

    @FXML
    private ComboBox<Integer> cbRate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cbRate.getItems().addAll(
                1,2,3,4,5
        );
        this.cbRate.setValue(1);
    }

    public ComboBox<Integer> getCbRate() {
        return cbRate;
    }
}
