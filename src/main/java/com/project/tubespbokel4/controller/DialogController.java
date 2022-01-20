package com.project.tubespbokel4.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javafx.stage.Stage;
import java.util.Optional;

public class DialogController {
    private Stage stage;

    public DialogController(Stage stage){
        this.stage = stage;
    }

    public void dialogError(String header, String content){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.initOwner(this.stage);
        dialog.setTitle("Error!");
        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait();
    }

    public Optional<ButtonType> dialogConfirm(String content){
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION);
        dialog.initOwner(this.stage);
        dialog.setTitle("Confirmation!");
//        dialog.setHeaderText(header);
        dialog.setContentText(content);

        return dialog.showAndWait();
    }

    public void dialogInfomation(String content){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.initOwner(this.stage);
        dialog.setTitle("Information!");
//        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait();
    }

    public void dialogWarning(String content){
        Alert dialog = new Alert(Alert.AlertType.WARNING);
        dialog.initOwner(this.stage);
        dialog.setTitle("Warning!");
//        dialog.setHeaderText(header);
        dialog.setContentText(content);

        dialog.showAndWait();
    }
}
