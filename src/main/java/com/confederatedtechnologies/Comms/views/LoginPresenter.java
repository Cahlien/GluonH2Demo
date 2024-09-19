package com.confederatedtechnologies.Comms.views;

import com.confederatedtechnologies.Comms.db.DatabaseHelper;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;

public class LoginPresenter {
    @FXML
    private View login;

    @FXML
    private Label stepOne;

    @FXML
    private Label stepTwo;

    @FXML
    private Label stepThree;

    public void initialize() {
        login.showingProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                AppBar appBar = AppManager.getInstance().getAppBar();
                appBar.setVisible(false);
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        AppManager.getInstance().getDrawer().open()));
                appBar.setTitleText("Login");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        System.out.println("Search")));
            }
        });
        var users = DatabaseHelper.initialize();
        stepOne.setText("Step 1: Initialize database");
        stepTwo.setText("Step 2: Get users from database");
        stepThree.setText("Step 3: Display users: " + users);
        DatabaseHelper.initialize();
    }
}
