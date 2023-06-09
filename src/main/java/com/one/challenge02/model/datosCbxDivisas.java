package com.one.challenge02.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class datosCbxDivisas {

    public List<String> ListaDivisas = new ArrayList<>();
    public Map<String, String> MapaDivisas;

    public datosCbxDivisas(){
        // Divisas
        ListaDivisas.add("USD"); // DOLAR AMERICANO
        ListaDivisas.add("EUR"); // EURO
        ListaDivisas.add("PEN"); // NUEVO SOL
        ListaDivisas.add("BOB"); // BOLIVIANO
        ListaDivisas.add("VES"); // BOLIVAR
        ListaDivisas.add("MXN"); // PESO MEXICANO
        ListaDivisas.add("COP"); // PESO COLOMBIANO
        ListaDivisas.add("CLP"); // PESO CHILENO
        ListaDivisas.add("ARS"); // PESO ARGENTINO

        MapaDivisas = new HashMap<>();
        MapaDivisas.put("USD", "Dólar americano");
        MapaDivisas.put("EUR", "Euro");
        MapaDivisas.put("PEN", "Nuevo sol");
        MapaDivisas.put("BOB", "Boliviano");
        MapaDivisas.put("VES", "Bolívar");
        MapaDivisas.put("MXN", "Peso mexicano");
        MapaDivisas.put("COP", "Peso colombiano");
        MapaDivisas.put("CLP", "Peso chileno");
        MapaDivisas.put("ARS", "Peso argentino");
    }
}
