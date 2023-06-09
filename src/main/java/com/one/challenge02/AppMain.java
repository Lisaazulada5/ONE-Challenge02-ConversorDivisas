package com.one.challenge02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class AppMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader  = new FXMLLoader(getClass().getResource("/fxml/MenuPrincipal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        String css = Objects.requireNonNull(this.getClass().getResource("/css/MenuPrincipal.css")).toExternalForm();
        scene.getStylesheets().add(css);
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/怒.png")));
        stage.getIcons().add(icon);
        stage.setTitle("ONE - Challenge 02 Back End - Java");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}