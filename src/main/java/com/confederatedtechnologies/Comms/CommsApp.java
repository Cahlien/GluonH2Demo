package com.confederatedtechnologies.Comms;

import com.confederatedtechnologies.Comms.db.DatabaseHelper;
import com.confederatedtechnologies.Comms.views.LoginView;
import com.confederatedtechnologies.Comms.views.ChatsView;
import com.gluonhq.attach.storage.StorageService;
import com.gluonhq.attach.util.Services;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.gluonhq.charm.glisten.visual.Swatch;
import com.gluonhq.charm.glisten.visual.Theme;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.gluonhq.charm.glisten.application.AppManager.HOME_VIEW;

public class CommsApp extends Application {
    public static Logger LOGGER = Logger.getLogger(CommsApp.class.getName());
    public static final String LOGIN_VIEW = HOME_VIEW;
    public static final String CHATS_VIEW = "Chats View";

    private final AppManager appManager = AppManager.initialize(this::postInit);

    @Override
    public void init() {
        // add tag for logcat to logger
        LOGGER.log(Level.INFO, "Initializing CommsApp");
        appManager.addViewFactory(LOGIN_VIEW, () -> (View) new LoginView().getView());
        appManager.addViewFactory(CHATS_VIEW, () -> (View) new ChatsView().getView());
        DrawerManager.buildDrawer(appManager);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        DatabaseHelper.initialize();
        Theme theme = Theme.DARK;
        theme.assignTo(scene);
        Swatch.TEAL.assignTo(scene);

        // load styles
        scene.getStylesheets().add(CommsApp.class.getResource("style.css").toExternalForm());

        appManager.getAppBar().setVisible(false);
        var users = DatabaseHelper.getUsers(DatabaseHelper.getConnection());
        LOGGER.log(Level.INFO, "Users: {0}", users);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
