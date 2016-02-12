package com.natrollus.kisisel.servis.guncelleme;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import static com.natrollus.kisisel.araclar.Ortak.logla;

public class Guncelle extends Service {

    private static BroadcastReceiver ekranKapandiginda;
    private static BroadcastReceiver ekranAcildiginda;
    private static BroadcastReceiver kullaniciAktifken;
    private static int PORT = 3939;
    String soketURL = "http://natrollus.com:" + PORT + "/";
    Socket soket = null;

    private void setEkranAcildiginda () {
        ekranAcildiginda = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                logla("ekran acildi..");
            }
        };
        registerReceiver(ekranAcildiginda,new IntentFilter(Intent.ACTION_SCREEN_ON));
    }

    private void setEkranKapandiginda () {
        ekranKapandiginda = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                logla("ekran kapandi.."+soketCoz());
            }
        };
        registerReceiver(ekranKapandiginda,new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    private void setKullaniciAktifken () {
        kullaniciAktifken = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                logla("kullanici aktif.. "+soketBagla());
            }
        };
        registerReceiver(kullaniciAktifken, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }

    private boolean soketBagla() {
        try {
            soket = IO.socket(soketURL);
            if (!soket.connected()){
                soket.connect();
                Thread.sleep(1000);
            }
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        if (soket.connected()){
            soket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    soket.emit("android", "{olay:android,veri:baglandi}");
                }
            }).on("alici", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    //gelen veri ...
                    logla("gelen:" + args[0]);
                }
            });
            return true;
        } else {
            return false;
        }
    }

    private boolean soketCoz() {
        try {
            Thread.sleep(3000);
            if (soket != null) {
                soket.disconnect();
                soket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        soket.emit("android", "{olay:android,veri:koptu}");
                    }
                });
            }

        } catch (Exception e) {
            logla("e:" + e);
            return false;
        }
        return soket != null && soket.connected();
    }

    @Override
    public void onCreate() {
        setEkranAcildiginda();
        setEkranKapandiginda();
        setKullaniciAktifken();
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

