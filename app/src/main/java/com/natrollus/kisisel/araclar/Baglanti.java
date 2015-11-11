package com.natrollus.kisisel.araclar;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import static com.natrollus.kisisel.araclar.Ortak.isOku;
import static com.natrollus.kisisel.araclar.Ortak.logla;


public class Baglanti extends AsyncTask<String,String,Boolean>{

    HttpURLConnection baglanti;
    URL url;
    String sonuc="";
    String metod;
    boolean baglandi = false;
    boolean https = false;

    public Baglanti(String url,String metod){
        try {
            this.url = new URL(url);
            this.metod = metod;
        } catch (MalformedURLException e) {
            sonuc += "url hata:"+e.toString()+"\n";
            baglandi=false;
        }
        if (url.startsWith("https://")) {https = true;} else if (url.startsWith("http://")){https=false;}
    }

    public String sonucYaz(){
        return sonuc;
    }

    public boolean sonuc() {
        return baglandi;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        if (url!=null){
            try {
                baglanti = https ? (HttpsURLConnection) url.openConnection() : (HttpURLConnection) url.openConnection();
                baglanti.setAllowUserInteraction(true);
                baglanti.setInstanceFollowRedirects(true);
                baglanti.setRequestMethod(metod);
                baglanti.setDoInput(true);
                baglanti.connect();
                int cevap = baglanti.getResponseCode();
                InputStream is = baglanti.getInputStream();
                logla("is:"+is.read());
                logla("url:"+url+" cevap:"+cevap);
                sonuc = isOku(is);
                if (sonuc!=null){
                    baglandi = true;
                }
                logla("baglandi:"+baglandi);
            } catch (IOException e) {
                sonuc += "bag. hata:"+e.toString()+"\n";
                baglandi =false;
            }

        }
        return baglandi;
    }
}
