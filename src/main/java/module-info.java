module com.one.challenge02 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires de.jensd.fx.glyphs.fontawesome;
    requires de.jensd.fx.glyphs.commons;
    requires org.json;

    opens com.one.challenge02.controller to javafx.fxml;
    exports com.one.challenge02;
    exports com.one.challenge02.controller;
    exports com.one.challenge02.conection;
    exports com.one.challenge02.model;

}