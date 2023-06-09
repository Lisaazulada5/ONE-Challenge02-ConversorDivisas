package com.one.challenge02.model;

import java.util.ArrayList;
import java.util.List;
//Clase que contendra el modelo de los datos para los Combobox en la interfaz de Temperatura
public class datosCbxTemperatura {
    public List<String> ListaTemperatura= new ArrayList<>();
    public datosCbxTemperatura(){
        // Escalas de Temperaturas
        ListaTemperatura.add("CELCIUS");
        ListaTemperatura.add("KELVIN");
        ListaTemperatura.add("FAHRENHEIT");
    }
}
