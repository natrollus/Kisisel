package com.natrollus.kisisel.servis.guncelleme;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;

import com.natrollus.kisisel.Kisisel;

import static com.natrollus.kisisel.araclar.Ortak.alarmKapaAc;
import static com.natrollus.kisisel.araclar.Ortak.logla;

public class Guncelle extends Service {

    private static BroadcastReceiver ekranKapandiginda;
    private static BroadcastReceiver ekranAcildiginda;
    private static BroadcastReceiver kullaniciAktifken;


    private void setEkranKapandiginda () {
        ekranKapandiginda = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                alarmKapaAc(context,false);
            }
        };
        registerReceiver(ekranKapandiginda,new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private void setEkranAcildiginda () {
        ekranAcildiginda = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                alarmKapaAc(context,false);
            }
        };
        registerReceiver(ekranAcildiginda,new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

    private void setKullaniciAktifken () {
        kullaniciAktifken = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                alarmKapaAc(context,true);
            }
        };
        registerReceiver(kullaniciAktifken,new IntentFilter(Intent.ACTION_USER_PRESENT));

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(ekranAcildiginda);
        unregisterReceiver(ekranKapandiginda);
        unregisterReceiver(kullaniciAktifken);

    }

    @Override
    public IBinder onBind(Intent intent) {
        logla("bindi..");
        return null;
    }
}

