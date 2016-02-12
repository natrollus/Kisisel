package com.natrollus.kisisel.araclar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import com.natrollus.kisisel.Kisisel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by byram on 10/26/15.
 */
public class Ortak {
    public static void logla(String s){
        Log.v("bilgi",""+s);
    }
    public static String tarihGetir(String format){
        return new SimpleDateFormat(format).format(new Date());
    }
    public static String isOku(InputStream is){
        try {
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void alarmKapaAc(Context context, boolean ackapa) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Kisisel.ACTION_GUNCELLE);
        PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);

        if (ackapa) {
            am.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000, (long) (1f * 1000), pi);
            logla("alarm acik..");
        } else {
            am.cancel(pi);
            logla("alarm kapali..");
        }

    }

}