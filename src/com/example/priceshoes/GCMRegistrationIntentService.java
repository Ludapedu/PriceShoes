package com.example.priceshoes;

import android.app.IntentService;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.webkit.WebResourceRequest;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by luis.perez on 21/09/2016.
 */

public class GCMRegistrationIntentService extends IntentService {
    public static final String REGISTRATION_SUCCESS = "RegistrationSuccess";
    public static final String REGISTRATION_ERROR = "RegistrationError";

    public GCMRegistrationIntentService() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        registerGCM();
    }

    private void registerGCM()
    {
        Intent registrationComplete = null;
        String token = null;

        try
        {
            InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
            token = instanceID.getToken("137134302808", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.v("GCMRegistrationService", "token: " + token);
            registrationComplete = new Intent(REGISTRATION_SUCCESS);
            registrationComplete.putExtra("token", token);

            TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            String IMEI = tm.getDeviceId();

            String Stringurl = "http://wcfzapatos.azurewebsites.net/ServicioZapatos.svc/RegistroGcm?imei=" + IMEI +"&registrationId=" + token;

            URL url = new URL(Stringurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.setRequestMethod("GET");

            int codigoEstado = connection.getResponseCode();
            if(codigoEstado != 200)
                throw new Exception("Error al procesar el registro el codigo http es: " + codigoEstado);
            InputStream inputstream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputstream,"UTF-8"));

            String respuesta = "",linea;
            while((linea = bufferedReader.readLine()) != null)
            {
                respuesta += linea;
            }
            bufferedReader.close();
            inputstream.close();

            Log.v("Registro en servidor: ", respuesta);
        }
        catch (Exception e)
        {
            Log.w("GCMRegistrationService", "Registration error" + e.toString());
            registrationComplete = new Intent(REGISTRATION_ERROR);
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
