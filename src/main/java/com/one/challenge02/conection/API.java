package com.one.challenge02.conection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

public class API {
    // Clave para tener la API
    private static final String API_KEY = "4e6dba941c7b4f7aaa677ca64ef24e75";

    // Map para poner las divisas de la API
    Map<String, Double> divisas = new HashMap<>();

    private JSONObject DatosAPI() throws Exception {
        String url = "https://openexchangerates.org/api/latest.json?app_id=" + API_KEY;
        URL obj = new URL(url);
        HttpURLConnection conexion = (HttpURLConnection) obj.openConnection();
        conexion.setRequestMethod("GET");
        conexion.connect();

        // Leer la respuesta de la API
        BufferedReader in = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
        String inputLinea;
        StringBuilder Cadatexto = new StringBuilder();
        while ((inputLinea = in.readLine()) != null) {
            Cadatexto.append(inputLinea);
        }
        in.close();

        JSONObject json = new JSONObject(Cadatexto.toString());

        return json;
    }

    public Map<String, Double> ApiMap() {
        try {
            JSONObject json = DatosAPI();

            JSONObject rates = json.getJSONObject("rates");
            Iterator<String> keys = rates.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Double value = rates.getDouble(key);
                divisas.put(key, value);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return divisas;
    }

}