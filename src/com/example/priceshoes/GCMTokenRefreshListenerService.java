package com.example.priceshoes;

import android.content.Intent;

import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by luis.perez on 21/09/2016.
 */

public class GCMTokenRefreshListenerService extends InstanceIDListenerService{
    @Override
    public void onTokenRefresh()
    {
        Intent intent = new Intent(this, GCMRegistrationIntentService.class);
        startService(intent);
    }
}
