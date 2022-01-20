package com.project.tubespbokel4;

import com.project.tubespbokel4.controller.Authentication;
import com.project.tubespbokel4.controller.DialogController;
import com.project.tubespbokel4.controller.ScreenController;
import com.project.tubespbokel4.model.auth.Roles;
import com.project.tubespbokel4.view.admin.DashboardAdminRootFrame;
import com.project.tubespbokel4.view.member.DashboardMemberRootFrame;
import com.project.tubespbokel4.view.toko.DashboardTokoRootFrame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
/**
 *
 * @author Kelompok-4
 */
public class MainApplication extends Application {
    public static ScreenController screenController;
    public static Authentication authentication;
    public static DialogController dialogController;
    @Override
    public synchronized void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/auth/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setResizable(false);
        stage.setTitle("Tubes PBO Kelompok-4");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        screenController = new ScreenController(stage);
        dialogController = new DialogController(stage);
        if(authentication.isUserExist()){
            this.directToDashboard(authentication.getSession().getRole());
        }
    }

    public static void main(String[] args) {
        authentication = new Authentication();
        authentication.loadSessionFromFile();
        launch();
    }


    public static void directToDashboard(Roles role){
        if(role.equals(Roles.admin)){
            screenController.changeScreen(DashboardAdminRootFrame.screenURL);
        }else if(role.equals(Roles.seller)){
            screenController.changeScreen(DashboardTokoRootFrame.screenURL);
        }else{
            screenController.changeScreen(DashboardMemberRootFrame.screenURL);
        }
    }
}