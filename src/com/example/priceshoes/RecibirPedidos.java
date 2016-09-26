package com.example.priceshoes;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luis.perez on 26/09/2016.
 */

public class RecibirPedidos extends AsyncTask<Integer,Context,Boolean> {
    private Context context;

    public RecibirPedidos(Context context)
    {
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Integer... strings) {
        int IDProveedor = strings[0];
        String Stringurl =  "http://wcfzapatos.azurewebsites.net/ServicioZapatos.svc/RecibirPedido?IDProveedor=" + IDProveedor;
        try {
            URL url = new URL(Stringurl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");

            BaseDatosVentas BDVentas = new BaseDatosVentas(context, "Ventas", null, 1);
            SQLiteDatabase Ventas = BDVentas.getWritableDatabase();

            int codigoEstado = connection.getResponseCode();
            if (codigoEstado != 200)
                throw new Exception("Error al procesar el registro el codigo http es: " + codigoEstado);
            InputStream inputstream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream, "UTF-8"));

            String respuesta = "";
            String linea;
            while ((linea = bufferedReader.readLine()) != null) {
                respuesta += linea;
            }
            bufferedReader.close();
            inputstream.close();

            String Entregado, IdCliente, IdZapato, Pedido;

            JSONArray array = (JSONArray) new JSONArray(respuesta);
            for (int x=0; x< array.length(); x++)
            {
                String item = array.getString(x);
                Entregado = new JSONObject(item).getString("Entregado");
                if(Entregado.equals("false")) {
                    Entregado = "Sin Entregar";
                }else {
                    Entregado = "Entregado";
                }
                IdCliente = new JSONObject(item).getString("IdCliente");
                IdZapato = new JSONObject(item).getString("IdZapato");
                Ventas.execSQL("INSERT INTO Ventas(Cliente,Catalogo,Pagina,Marca,ID,Numero,Costo,Precio,Entregado,IDREG) "
                        + "VALUES ('"+ IdCliente + "','" + "" + "','" + "" + "','" + "" + "'," + IdZapato + "," + 0 + "," + 0 + "," + 0 + ",'" +Entregado + "',NULL)");
            }





            Log.v("Registro en servidor: ", respuesta);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
