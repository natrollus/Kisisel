package com.natrollus.kisisel.araclar;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;


public class Baglanti{

    HttpURLConnection baglanti;
    URL url;
    String sonuc="";
    boolean baglandi = false;
    boolean https = false;

    public Baglanti(String url,String metod){
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            sonuc += "url hata:"+e.toString()+"\n";
            baglandi=false;
        }
        if (url.startsWith("https://")) {https = true;} else if (url.startsWith("http://")){https=false;}
    }

    public boolean baglan(){
        if (url!=null){
            try {
                baglanti = https ? (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();
                baglanti.setAllowUserInteraction(true);
                baglanti.setInstanceFollowRedirects(true);
            } catch (IOException e) {
                sonuc += "bag. hata:"+e.toString()+"\n";
                baglandi =false;
            }

        }
        return baglandi;
    }

    public String sonucYaz(){
        return sonuc;
    }



}
