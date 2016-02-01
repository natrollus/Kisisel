package com.natrollus.kisisel.servis.guncelleme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.natrollus.kisisel.Kisisel;

public class Alici extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Kisisel.servisBaslat(context);
    }
}
