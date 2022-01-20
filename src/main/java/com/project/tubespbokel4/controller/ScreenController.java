package com.project.tubespbokel4.controller;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Stack;

public class ScreenController {
    private Stage stage;

    // variable ChangeTab
    private AnchorPane tempRoot;
    private Stack<String> stateStackUrl = new Stack<>();

    public ScreenController(Stage stage){
        this.stage = stage;
    }

    public void changeScreen(String newScreen){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(newScreen));
            Scene scene = new Scene(fxmlLoader.load());
            this.stage.setScene(scene);
            this.stage.centerOnScreen();
            this.stage.show();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
            this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTab(AnchorPane root, String url){
        if(this.stateStackUrl.isEmpty() || this.stateStackUrl.firstElement() != url || this.stateStackUrl.size() >= 2){
            this.stateStackUrl.clear();
            this.stateStackUrl.push(url);
            this.tempRoot = root;
            try {
                root.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource(url)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeToFirstFrame(){
        if(!this.stateStackUrl.isEmpty()){
            String fisrtFrame = this.stateStackUrl.firstElement();
            this.stateStackUrl.clear();
            this.stateStackUrl.push(fisrtFrame);
            try {
                this.tempRoot.getChildren().setAll((AnchorPane) FXMLLoader.load(getClass().getResource(fisrtFrame)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeToNextFrame(String url, Object controller){
        if(this.tempRoot != null && this.stateStackUrl.lastElement() != url){
            this.stateStackUrl.push(url);
            try {
                FXMLLoader child = new FXMLLoader(getClass().getResource(url));
                child.setController(controller);
                Parent parent = child.load();
                tempRoot.getChildren().setAll(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeToBackFrame(Object controller){
        if(this.tempRoot != null && this.stateStackUrl.size() >= 2){
            this.stateStackUrl.pop();
            FXMLLoader child = null;
            Parent parent = null;
            try {
                child = new FXMLLoader(getClass().getResource(this.stateStackUrl.lastElement()));
                if(controller != null) child.setController(controller);
                parent = child.load();
            }catch (LoadException e){
                try {
                    child = new FXMLLoader(getClass().getResource(this.stateStackUrl.lastElement()));
                    parent = child.load();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            tempRoot.getChildren().setAll(parent);
        }
    }

    public void clearScreenTabCache(){
        this.tempRoot = null;
        this.stateStackUrl.clear();
    }
}
