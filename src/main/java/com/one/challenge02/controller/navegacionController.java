package com.one.challenge02.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
//Esta clase controla la navegacion entre las ventanas
public class navegacionController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void cambiarConversorDivisa(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Divisa.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("Divisas");
        stage.setScene(scene);
        stage.show();
    }
    public void cambiarMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MenuPrincipal.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("ONE - Challenge 02 Back End - Java");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void cambiarConversorTemperatura(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Temperatura.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Temperatura");
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




}
