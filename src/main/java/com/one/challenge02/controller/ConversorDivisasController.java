package com.one.challenge02.controller;

import com.one.challenge02.conection.API;
import com.one.challenge02.model.datosCbxDivisas;
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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class ConversorDivisasController implements Initializable {
    //Declaramos los componentes de nuestra Interfaz Divisas para poder manipular los datos
    // ============================== Inicio ==================================
    @FXML
    private ComboBox<String> cbxDivisaUno;
    @FXML
    private ComboBox<String> cbxDivisaDos;
    @FXML
    private TextField txtDivisaUno;
    @FXML
    private TextField txtDivisaDos;
    // =============================== Fin ====================================

    //Inicializamos nuestra API creando un objeto de tipo API
    API apiDivisas=new API();
    //Creamos un objeto que nos ayudara a guardar la taza de cambio actual
    Map<String,Double> cambioActual;
    //Inicializamos un Objeto de tipo DatosCbxDivisas para llenar los combobox
    datosCbxDivisas ListaCbx = new datosCbxDivisas();

    navegacionController navegacion = new navegacionController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inicializacion();
    }

    @FXML
    private void inicializacion(){
        ObservableList<String> ListaDivisas = FXCollections.observableArrayList();
        for (Map.Entry<String, String> entrada : ListaCbx.MapaDivisas.entrySet()) {
            String opcion = entrada.getKey() + " - " + entrada.getValue();
            String opcionvalue = entrada.getValue();
            ListaDivisas.add(opcion);
        }
        // Cargando la API y lo Almacenamos en un Map
        cambioActual = apiDivisas.ApiMap();

        // Llenamos los combBox de DIVISAS
        Collections.sort(ListaDivisas);
        ObservableList<String> OpDivisas1 = FXCollections.observableArrayList(ListaCbx.ListaDivisas);
        cbxDivisaUno.setItems(ListaDivisas);
        cbxDivisaUno.setValue("USD - Dólar americano");
        ObservableList<String> OpDivisas2 = FXCollections.observableArrayList(ListaCbx.ListaDivisas);
        cbxDivisaDos.setItems(ListaDivisas);
        cbxDivisaDos.setValue("MXN - Peso mexicano");

        // Crear los filtros para los TextFields de DIVISAS
        //Declaramos variables que nos ayudaran a guardar los cambios cada vez que se modifique.
        UnaryOperator<TextFormatter.Change> divisaUno = CrearFiltro("[0-9\\.]*");
        UnaryOperator<TextFormatter.Change> divisaDos = CrearFiltro("[0-9\\.]*");

        // Crear los filtros para los textFields de DIVISAS
        divisaUno = CrearFiltro("[0-9\\.-]*");
        divisaDos = CrearFiltro("[0-9\\.-]*");

        // Capturando los datos ingresado en los textField de DIVISAS
        txtDivisaUno.setTextFormatter(new TextFormatter<>(divisaUno));
        txtDivisaDos.setTextFormatter(new TextFormatter<>(divisaDos));

        // Carga el Aplicativo y digitamos algun dato en el textField DIVISAS
        txtDivisaUno.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtDivisaUno.isFocused())
                CalculoDivisasUNO();
        });
        txtDivisaDos.textProperty().addListener((observable, oldValue, newValue) -> {
            if (txtDivisaDos.isFocused())
                CalculoDivisasDOS();
        });
        // Cuando Seleccionamos un nuevo valor del comboBox1 de DIVISAS
        cbxDivisaUno.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String NuevoValor = txtDivisaUno.getText();
                txtDivisaUno.setText(NuevoValor);
                CalculoDivisasDOS();
            }
        });

        // Cuando Seleccionamos un nuevo valor del comboBox2 de DIVISAS
        cbxDivisaDos.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String NuevoValor = txtDivisaDos.getText();
                txtDivisaDos.setText(NuevoValor);
                CalculoDivisasUNO();
            }
        });

    }


    private void CalculoDivisasUNO() {
        String DatoCBX1 = "";
        String DatoCBX2 = "";
        String DatoCBX1Crudo = cbxDivisaUno.getValue(); // Capturando el valor actual del combobox1
        String DatoCBX2Crudo = cbxDivisaDos.getValue(); // Capturando el valor actual del combobox2

        int ExtraendoValor1 = DatoCBX1Crudo.indexOf(" ");
        int ExtraendoValor2 = DatoCBX2Crudo.indexOf(" ");

        if (ExtraendoValor1 != -1) { // Si hay un espacio
            DatoCBX1 = DatoCBX1Crudo.substring(0, ExtraendoValor1); // Extraer los caracteres hasta el espacio
            //System.out.println(DatoCBX1); // Imprimir la subcadena
        }

        if (ExtraendoValor2 != -1) { // Si hay un espacio
            DatoCBX2 = DatoCBX2Crudo.substring(0, ExtraendoValor2); // Extraer los caracteres hasta el espacio
            //System.out.println(DatoCBX2); // Imprimir la subcadena
        }

        Set<String> claves = cambioActual.keySet(); // Capturamos las Claves del Map en un Set
        // System.out.println(DatoCBX1);

        // Comparamos las claves que son tipo Set con el String DatoCBX1
        if (claves.contains(DatoCBX1)) {
            try {
                double numero1 = txtDivisaUno.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisaUno.getText());
                double MontoConversion = numero1 * cambioActual.get(DatoCBX2) / cambioActual.get(DatoCBX1);
                DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
                symbols.setDecimalSeparator('.');
                DecimalFormat TresDecimales = new DecimalFormat("#.###", symbols); // Formato para que solo tenga 3 digitos
                double ResultadoFinal = Double.parseDouble(TresDecimales.format(MontoConversion));
                if (MontoConversion == 0) {
                    txtDivisaDos.setText(""); // establecer el número como entero en el
                } else {
                    txtDivisaDos.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
                }

            } catch (NumberFormatException e) {
                AlertaError();
                e.printStackTrace();
            }
        }
    }

    private void CalculoDivisasDOS() {
        String DatoCBX1 = "";
        String DatoCBX2 = "";
        String DatoCBX1Crudo = cbxDivisaUno.getValue(); // Capturando el valor actual del combobox1
        String DatoCBX2Crudo = cbxDivisaDos.getValue(); // Capturando el valor actual del combobox2

        int ExtraendoValor1 = DatoCBX1Crudo.indexOf(" ");
        int ExtraendoValor2 = DatoCBX2Crudo.indexOf(" ");

        if (ExtraendoValor1 != -1) { // Si hay un espacio
            DatoCBX1 = DatoCBX1Crudo.substring(0, ExtraendoValor1); // Extraer los caracteres hasta el espacio
            //System.out.println(DatoCBX1); // Imprimir la subcadena
        }

        if (ExtraendoValor2 != -1) { // Si hay un espacio
            DatoCBX2 = DatoCBX2Crudo.substring(0, ExtraendoValor2); // Extraer los caracteres hasta el espacio
            //System.out.println(DatoCBX2); // Imprimir la subcadena
        }

        Set<String> claves = cambioActual.keySet(); // Capturamos las Claves del Map en un Set
        // System.out.println(DatoCBX1);

        // Comparamos las claves que son tipo Set con el String DatoCBX1
        if (claves.contains(DatoCBX2)) {
            try {
                double numero2 = txtDivisaDos.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisaDos.getText());
                double MontoConversion = numero2 * cambioActual.get(DatoCBX1) / cambioActual.get(DatoCBX2);
                DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
                double ResultadoFinal = Double.parseDouble(TresDecimales.format(MontoConversion));
                if (MontoConversion == 0) {
                    txtDivisaUno.setText(""); // establecer el número como entero en el
                } else {
                    txtDivisaUno.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
                }

            } catch (NumberFormatException e) {
                AlertaError();
                e.printStackTrace();
            }
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
    private UnaryOperator<TextFormatter.Change> CrearFiltro(String Captura) {
        Pattern pattern = Pattern.compile(Captura);
        return change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
    }

    public void cambiarMenu(ActionEvent event) throws IOException {
        navegacion.cambiarMenu(event);
    }
    public void cambiarConversorTemperatura(ActionEvent event) throws IOException {
        navegacion.cambiarConversorTemperatura(event);
    }


}
