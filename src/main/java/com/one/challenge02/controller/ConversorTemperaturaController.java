package com.one.challenge02.controller;

import com.one.challenge02.model.datosCbxTemperatura;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ConversorTemperaturaController implements Initializable {
    // Interfaz de TEMPERATURAS ------ INICIO --------
    @FXML
    private ComboBox<String> cbxTemperaturaUno;
    @FXML
    private ComboBox<String> cbxTemperaturaDos;
    @FXML
    private TextField txtTemperaturaUno;
    @FXML
    private TextField txtTemperaturaDos;
    // Interfaz de TEMPERATURAS ------ FIN -----------
    // Variables para guardar cambios en texto editado TEMPERATURA
    private UnaryOperator<TextFormatter.Change> num1TempFiltro;
    private UnaryOperator<TextFormatter.Change> num2TempFiltro;

    datosCbxTemperatura ListaComboBox = new datosCbxTemperatura();

    navegacionController navegacion = new navegacionController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializacion();
    }

    @FXML
    public void inicializacion(){
        // Llenamos los combBox de TEMPERATURAS
        Collections.sort(ListaComboBox.ListaTemperatura);
        ObservableList<String> OpTemp1 = FXCollections.observableArrayList(ListaComboBox.ListaTemperatura);
        cbxTemperaturaUno.setItems(OpTemp1);
        cbxTemperaturaUno.setValue("CELCIUS");
        ObservableList<String> OpTemp2 = FXCollections.observableArrayList(ListaComboBox.ListaTemperatura);
        cbxTemperaturaDos.setItems(OpTemp2);
        cbxTemperaturaDos.setValue("KELVIN");


        // Crear los filtros para los textFields de DIVISAS
        num1TempFiltro = CrearFiltro("[0-9\\.-]*");
        num2TempFiltro = CrearFiltro("[0-9\\.-]*");

        // Capturando los datos ingresado en los textField de DIVISAS
        txtTemperaturaUno.setTextFormatter(new TextFormatter<>(num1TempFiltro));
        txtTemperaturaDos.setTextFormatter(new TextFormatter<>(num2TempFiltro));

        // Aplicamos el filtro a los textfield
        txtTemperaturaUno.setTextFormatter(new TextFormatter<>(num1TempFiltro));
        txtTemperaturaDos.setTextFormatter(new TextFormatter<>(num2TempFiltro));

        // Carga el Aplicativo y digitamos algun dato en el textField TEMPERATURAS
        txtTemperaturaUno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtTemperaturaUno.isFocused() && !newValue.equals("-"))
                CalculoTempUNO();
        });
        txtTemperaturaDos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtTemperaturaDos.isFocused() && !newValue.equals("-"))
                CalculoTempDOS();
        });
        // Cuando Seleccionamos un nuevo valor del comboBox1 de TEMPERATURA
        cbxTemperaturaUno.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String NuevoValor = txtTemperaturaUno.getText();
                txtTemperaturaUno.setText(NuevoValor);
                CalculoTempDOS();
            }
        });

        // Cuando Seleccionamos un nuevo valor del comboBox2 de TEMPERATURA
        cbxTemperaturaUno.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String NuevoValor = txtTemperaturaDos.getText();
                txtTemperaturaDos.setText(NuevoValor);
                CalculoTempUNO();
            }
        });
    }
    private void CalculoTempUNO() {
        String DatoCBX1 = cbxTemperaturaUno.getValue(); // Capturando el valor actual del combobox1
        String DatoCBX2 = cbxTemperaturaDos.getValue(); // Capturando el valor actual del combobox2

        try {
            double numero1 = txtTemperaturaUno.getText().isEmpty() ? 0 : Double.parseDouble(txtTemperaturaUno.getText());

            CalcularTemperatura formulaTemperatura = new CalcularTemperatura(numero1, DatoCBX1, DatoCBX2);
            double Resultado = formulaTemperatura.CalcularTemperatura();
            DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
            symbols.setDecimalSeparator('.');
            DecimalFormat TresDecimales = new DecimalFormat("#.###",symbols); // Formato para que solo tenga 3 digitos

            double ResultadoFinal = Double.parseDouble(TresDecimales.format(Resultado));
            if (Resultado == 0) {
                txtTemperaturaDos.setText(""); // establecer el número como entero en el
            } else {
                txtTemperaturaDos.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
            }
        } catch (NumberFormatException e) {
            AlertaError();
            e.printStackTrace();
        }
    }

    private void CalculoTempDOS() {
        String DatoCBX1 = cbxTemperaturaUno.getValue(); // Capturando el valor actual del combobox1
        String DatoCBX2 = cbxTemperaturaDos.getValue(); // Capturando el valor actual del combobox2

        try {
            double numero2 = txtTemperaturaDos.getText().isEmpty() ? 0 : Double.parseDouble(txtTemperaturaDos.getText());
            CalcularTemperatura formulaTemperatura = new CalcularTemperatura(numero2, DatoCBX2, DatoCBX1);
            double Resultado = formulaTemperatura.CalcularTemperatura();
            DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
            double ResultadoFinal = Double.parseDouble(TresDecimales.format(Resultado));
            if (Resultado == 0) {
                txtTemperaturaUno.setText(""); // establecer el número como entero en el
            } else {
                txtTemperaturaUno.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
            }
        } catch (NumberFormatException e) {
            AlertaError();
            e.printStackTrace();
        }
    }
    private void AlertaError() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso de Error");
        alert.setHeaderText(null);
        alert.setContentText("Verifique si ingreso correctamente el numero");
        alert.showAndWait();
    }
    // Creando el Filtro al ingresar datos al textField
    private UnaryOperator<TextFormatter.Change> CrearFiltro(String captura) {
        Pattern pattern = Pattern.compile(captura);
        return change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
    }

    public void cambiarMenu(ActionEvent event) throws IOException {
        navegacion.cambiarMenu(event);
    }
    public void cambiarConversorDivisa(ActionEvent event) throws IOException {
        navegacion.cambiarConversorDivisa(event);
    }






}
