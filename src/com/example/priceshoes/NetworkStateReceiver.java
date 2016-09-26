package com.example.priceshoes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * Created by luis.perez on 26/09/2016.
 */

public class NetworkStateReceiver extends BroadcastReceiver {
    boolean isConnected = false;
    boolean isWiFi = false;
    boolean isMobile = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("PriceShoes","Network connectivity changed");
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null) {
            isConnected = activeNetwork.isConnected();
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            Log.d("Internet","Conecado a internet por red " + (isWiFi ? "Wifi" : "Móvil"));
            Toast.makeText(context, "Conecado a internet por red " + (isWiFi ? "Wifi" : "Mobil"), Toast.LENGTH_SHORT).show();
            try {
                new RecibirPedidos(context).execute(111111).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return;
        }

        Log.d("Internet","Sin conexión a internet");
        Toast.makeText(context, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
    }
}
