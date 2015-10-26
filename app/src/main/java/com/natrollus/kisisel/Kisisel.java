package com.natrollus.kisisel;

import android.app.*;
import android.os.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.*;
import android.hardware.*;

import static com.natrollus.kisisel.Ortak.tarihGetir;

public class Kisisel extends AppWidgetProvider {
    public static final String ACTION_SAG = "kisisel.action.SAG";
	public static final String ACTION_ORTA = "kisisel.action.ORTA";
	public static final String ACTION_SOL = "kisisel.action.SOL";
	AppWidgetManager awm;
	RemoteViews rv;
	ComponentName cn;
	String s="";

	public void init(Context context){
		awm = AppWidgetManager.getInstance(context);
		cn = new ComponentName(context,getClass());
		rv = new RemoteViews(context.getPackageName(),R.layout.kisisel);
	}
    @Override
    public void onReceive(Context context,Intent intent) {
		init(context);
		ayarla(context);
		String action = intent.getAction();
		switch (action) {
			case AppWidgetManager.ACTION_APPWIDGET_ENABLED:
				s = "kuruldu";
				break;
			case Kisisel.ACTION_SAG:
				s = "sag";
				break;
			case Kisisel.ACTION_ORTA:
				s = "orta";
				break;
			case Kisisel.ACTION_SOL:
				s = "sol";
				break;
			case AppWidgetManager.ACTION_APPWIDGET_UPDATE:
				s = tarihGetir("HH:mm:ss") + "de guncellendi..";
				break;
			default:
				break;
		}
		tazele();
	}
	
	public void ayarla(Context context){
		int[] butonlar = {R.id.sagla,R.id.ortala,R.id.solla};
		String[] aksiyon = {Kisisel.ACTION_SAG,Kisisel.ACTION_ORTA,Kisisel.ACTION_SOL};
		for (int i = 0; i < 3; i++) {
			Intent intent = new Intent(aksiyon[i],null,context,getClass());
			PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
			rv.setOnClickPendingIntent(butonlar[i],pi);
		}

	}
	
	public void sensorListe(Context context){
        Intent intent = new Intent(Kisisel.ACTION_ORTA,null,context,Bilgilendirme.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
	
	public void tazele(){
		rv.setTextViewText(R.id.yazi,s);
		awm.updateAppWidget(cn,rv);
	}
	
	private String sonucGetir () {
		return s;
	}
}
