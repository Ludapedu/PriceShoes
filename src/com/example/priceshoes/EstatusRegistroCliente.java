package com.example.priceshoes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luis.perez on 22/09/2016.
 */

public class EstatusRegistroCliente extends AsyncTask<String, Void, String> {

    public String respuesta;
    @Override
    protected void onPostExecute(String Datos) {
        super.onPostExecute(Datos);
        
    }

    @Override
    protected String doInBackground(String... IMEI) {
        String imei = IMEI[0];
        ArrayList<String> result = new ArrayList<>();

        String Stringurl = "http://wcfzapatos.azurewebsites.net/ServicioZapatos.svc/ObtenerDatosRegistro?imei=" + imei;
        try {
            URL url = new URL(Stringurl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");


            int codigoEstado = connection.getResponseCode();
            if (codigoEstado != 200)
                throw new Exception("Error al procesar el registro el codigo http es: " + codigoEstado);
            InputStream inputstream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));

            respuesta = "";
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                respuesta += linea;
            }
            bufferedReader.close();
            inputstream.close();


            Log.v("Registro en servidor: ", respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return respuesta;
    }
}
