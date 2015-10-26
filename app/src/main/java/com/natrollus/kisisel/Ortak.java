package com.natrollus.kisisel;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

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
}