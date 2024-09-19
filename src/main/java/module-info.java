module commsgluon {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gluonhq.charm.glisten;
    requires javafx.base;
    requires javafx.graphics;
    requires com.gluonhq.attach.display;
    requires com.gluonhq.attach.lifecycle;
    requires com.gluonhq.attach.util;
    requires com.gluonhq.attach.storage;
    requires java.sql;

    opens com.confederatedtechnologies.Comms to javafx.fxml;
    exports com.confederatedtechnologies.Comms;
}