package com.example.vendeur.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//Brodcast Receive
public class BrodcastRecive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if   (   intent.getAction().equalsIgnoreCase("com.example.Broadcast")) {
            { NewMessageNotification notfilyme= new NewMessageNotification();
                notfilyme.notify(context,bundle.getString("msg"),223);}
        }
    }
}