package com.natrollus.kisisel.araclar;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.natrollus.kisisel.Kisisel;

import static com.natrollus.kisisel.araclar.Ortak.logla;

public class Tasiyici extends BroadcastReceiver {

    public static final String[] SAG_SECIM_LISTE_BASLIKLARI = {"ana","kamera","btc-e","acil","nedir","yaz","kapa"};
    public static final String SAGDAN = "com.kisisel.extra.SAGDAN";
    public static final String SOLDAN = "com.kisisel.extra.SOLDAN";

    private static String bilgi = "";
    private static String sag = "";

    public static void setBilgi(String bilgi) {
        Tasiyici.bilgi = bilgi;
    }
    public static void setSag(String sag) {
        Tasiyici.sag = sag;
    }

    public static String getBilgi() {
        return bilgi;
    }
    public static String getSag() {
        return sag;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent gecis = new Intent(intent.getAction());
        if (intent.getAction().equals(Kisisel.ACTION_UZAK_SAG)){
            gecis.putExtra(SAGDAN,intent.getStringExtra(SAGDAN));
        } else if (intent.getAction().equals(Kisisel.ACTION_UZAK_SOL)){
            gecis.putExtra(SOLDAN, intent.getStringExtra(SOLDAN));
        }
        PendingIntent bekleyenGecis = PendingIntent.getBroadcast(context,0,gecis,0);
        try {
            bekleyenGecis.send();
        } catch (PendingIntent.CanceledException e) {
            logla("hata:"+e.toString()+ " clas:"+Tasiyici.class);
        } finally {
            bekleyenGecis.cancel();
        }
    }
}
