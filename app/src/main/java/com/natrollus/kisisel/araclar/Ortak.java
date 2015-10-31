package com.natrollus.kisisel.araclar;

import android.util.Log;

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

}