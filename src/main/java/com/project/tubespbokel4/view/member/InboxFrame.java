package com.project.tubespbokel4.view.member;

import com.project.tubespbokel4.controller.member.InboxController;
import com.project.tubespbokel4.model.toko.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class InboxFrame implements Initializable {
    public static String screenURL = "/member/inbox.fxml";
    public Map<String, String> cbStatusItems = new HashMap<>(){{
        put("Member Request", "member_request");
        put("Member Cancel", "member_cancel");
        put("Seller Approved", "seller_approved");
        put("Seller Rejected", "seller_rejected");
        put("Done", "done");
    }};
    private InboxController controller;

    @FXML
    private ComboBox<String> cbStatus;

    @FXML
    private ListView<Transaction> lvInbox;

    public InboxFrame(){
        this.controller = new InboxController(this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.controller.setup();
    }

    public ComboBox<String> getCbStatus() {
        return cbStatus;
    }

    public ListView<Transaction> getLvInbox() {
        return lvInbox;
    }
}
