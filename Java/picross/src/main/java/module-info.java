module com.picross.collab {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;

    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires json.simple;
    requires annotations;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires jdk.xml.dom;
    requires jdk.compiler;
    exports com.picross.collab.client.controller;
    exports com.picross.collab.client.model;
    exports com.picross.collab.client;
    exports com.picross.collab.client.view;
    opens com.picross.collab.client.view to javafx.fxml;
    exports com.picross.collab.shared;
    exports com.picross.collab.play;
    opens com.picross.collab.play to javafx.fxml;
}
