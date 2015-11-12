package com.natrollus.kisisel;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

import com.natrollus.kisisel.araclar.Baglanti;
import com.natrollus.kisisel.gorunum.UzakGorunum;
import com.natrollus.kisisel.gorunum.UzakStatic;
import com.natrollus.kisisel.aktivite.Not;

import java.util.concurrent.ExecutionException;

import static com.natrollus.kisisel.araclar.Ortak.logla;
import static com.natrollus.kisisel.araclar.Ortak.tarihGetir;
import static com.natrollus.kisisel.gorunum.UzakStatic.setDegisken;

public class Kisisel extends AppWidgetProvider {
    public static final String ACTION_SAG = "kisisel.action.SAG";
	public static final String ACTION_ORTA = "kisisel.action.ORTA";
	public static final String ACTION_SOL = "kisisel.action.SOL";
	public static final String ACTION_AKTIVITE = "kisisel.action.AKTIVITE";
	public static final String ACTION_RESIZE = "com.sec.android.widgetapp.APPWIDGET_RESIZE";
	SharedPreferences kayitlar;
	Context context;
	AppWidgetManager awm;
	RemoteViews rv;
	ComponentName cn;
	String s="";

	public void init(Context context){
		awm = AppWidgetManager.getInstance(context);
		cn = new ComponentName(context,getClass());
		rv = new RemoteViews(context.getPackageName(),R.layout.kisisel);
		this.context = context;
		setDegisken(s);
		ayarla();
	}
    @Override
    public void onReceive(Context context,Intent intent) {
		kayitlar = context.getSharedPreferences("notlar",Context.MODE_PRIVATE);
		String action = intent.getAction();
		switch (action) {
			case AppWidgetManager.ACTION_APPWIDGET_ENABLED:
				s = "kuruldu";
				break;
			case AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED:
				s = "opt";
				break;
			case AppWidgetManager.ACTION_APPWIDGET_UPDATE:
				s = tarihGetir("HH:mm:ss") + " de guncellendi..";
				break;
			case Kisisel.ACTION_SAG:
				s = kayitlar.getString("not",null);
				break;
			case Kisisel.ACTION_ORTA:
				s = baglan("http://natrollus.com","GET");
				break;
			case Kisisel.ACTION_SOL:
				s = "kaydetmek icin bisiler yaz baam..";
				Intent i = new Intent(context,Not.class);
				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);
				break;
			case Kisisel.ACTION_RESIZE:
				s = "resize";
				break;
			case Kisisel.ACTION_AKTIVITE:
				String islem = intent.getStringExtra("islem");
				logla("islem:"+islem);
				logla("kayitlar:"+kayitlar);
				if (islem.equals("not")){
					s = kayitlar.getString("not",null);
				}
				break;
			default:
				s = action;
				break;
		}
		init(context);
	}

	private String baglan(String url, String metod) {
		Baglanti baglanti = new Baglanti(url,metod);
		boolean baglandi = false;
		String sonuc = "";
		try {
			baglandi = baglanti.execute().get();
		} catch (InterruptedException | ExecutionException e) {
			sonuc = e.toString();
		}
		if (baglandi){
			sonuc = baglanti.sonucYaz();
		}
		return sonuc;
	}

	public void ayarla(){
		int[] butonlar = {R.id.sagla,R.id.ortala,R.id.solla};
		String[] aksiyon = {Kisisel.ACTION_SAG,Kisisel.ACTION_ORTA,Kisisel.ACTION_SOL};
		for (int i = 0; i < 3; i++) {
			Intent intent = new Intent(aksiyon[i],null,context,getClass());
			PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
			rv.setOnClickPendingIntent(butonlar[i], pi);
		}
		listeyap();
	}

	public void listeyap(){
		Intent uzakservis = new Intent("uzak_aksiyon",null,context, UzakGorunum.class);
		awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(cn), R.id.liste_yazi);
		rv.setRemoteAdapter(R.id.liste_yazi, uzakservis);
		tazele();
	}

	public void tazele(){
		awm.updateAppWidget(cn, rv);
	}
	
}
