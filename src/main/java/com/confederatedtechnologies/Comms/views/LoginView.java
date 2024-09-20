package com.confederatedtechnologies.Comms.views;

import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class LoginView {
    public View getView() {
        try {
            return FXMLLoader.load(Objects.requireNonNull(LoginView.class.getResource("login.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}