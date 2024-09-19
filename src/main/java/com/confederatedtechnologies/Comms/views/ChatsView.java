package com.confederatedtechnologies.Comms.views;


import com.gluonhq.charm.glisten.mvc.View;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.Objects;

public class ChatsView {
    public View getView() {
        try {
            return FXMLLoader.load(Objects.requireNonNull(ChatsView.class.getResource("chats.fxml")));
        } catch (IOException e) {
            System.out.println("IOException: " + e);
            return new View();
        }
    }
}