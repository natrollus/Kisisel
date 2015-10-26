package com.natrollus.kisisel;

import android.app.*;
import android.os.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;
import java.util.*;
import android.hardware.*;

public class Kisisel extends AppWidgetProvider {
    public static String ACTION_BUTON = "kisisel.action.DUGME";
    public static String ACTION_BILGI = "kisisel.action.BILGI";
	AppWidgetManager awm;
	RemoteViews rv;
	ComponentName cn;
	String s="",t="",a;

	public void init(Context context){
		awm = AppWidgetManager.getInstance(context);
		cn = new ComponentName(context,getClass());
		rv = new RemoteViews(context.getPackageName(),R.layout.kisisel);
	}
    @Override
    public void onReceive(Context context,Intent intent) {
		init(context);
		ayarla(context);
		a = intent.getAction();
		if(a.equals(AppWidgetManager.ACTION_APPWIDGET_ENABLED)){
			s = "kuruldu";
		} else if (a.equals(Kisisel.ACTION_BUTON)){
			//sensorListe(context);
			s = "buton"+sonucGetir();
		} else if (a.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
			s = "guncelle";
		}
		tazele();
	}
	
	public void ayarla(Context context){
		Intent intent = new Intent(Kisisel.ACTION_BUTON,null,context,getClass());
		PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
		rv.setOnClickPendingIntent(R.id.sagla,pi);
	}
	
	public void sensorListe(Context context){
        Intent intent = new Intent(Kisisel.ACTION_BILGI,null,context,Bilgilendirme.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
	
	public void tazele(){
		rv.setTextViewText(R.id.yazi,s);
		awm.updateAppWidget(cn,rv);
	}
	
	private String sonucGetir () {
		String s ="";
		return s;
	}
}
