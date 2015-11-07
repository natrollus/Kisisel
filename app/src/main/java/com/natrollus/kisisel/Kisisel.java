package com.natrollus.kisisel;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

import com.natrollus.kisisel.araclar.Baglanti;
import com.natrollus.kisisel.gorunum.UzakGorunum;

import java.util.concurrent.ExecutionException;

import static com.natrollus.kisisel.araclar.Ortak.logla;
import static com.natrollus.kisisel.araclar.Ortak.tarihGetir;

public class Kisisel extends AppWidgetProvider {
    public static final String ACTION_SAG = "kisisel.action.SAG";
	public static final String ACTION_ORTA = "kisisel.action.ORTA";
	public static final String ACTION_SOL = "kisisel.action.SOL";
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
	}
    @Override
    public void onReceive(Context context,Intent intent) {
		init(context);
		String action = intent.getAction();
		switch (action) {
			case AppWidgetManager.ACTION_APPWIDGET_ENABLED:
				
				s = "kuruldu";
				break;
			case Kisisel.ACTION_SAG:
				s = "sag";
				break;
			case Kisisel.ACTION_ORTA:
				Baglanti baglanti = new Baglanti("http://natrollus.com","GET");
				boolean baglandi = false;
				try {
				 	baglandi = baglanti.execute().get();
				} catch (InterruptedException | ExecutionException e) {
					s = e.toString();
				}
				if (baglandi){
					s = baglanti.sonucYaz();
				}
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
		listetazele(s);
	}
	
	public void ayarla(){
		int[] butonlar = {R.id.sagla,R.id.ortala,R.id.solla};
		String[] aksiyon = {Kisisel.ACTION_SAG,Kisisel.ACTION_ORTA,Kisisel.ACTION_SOL};
		for (int i = 0; i < 3; i++) {
			Intent intent = new Intent(aksiyon[i],null,context,getClass());
			PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
			rv.setOnClickPendingIntent(butonlar[i],pi);
		}
		listetazele("ilk");
	}

	public void listetazele(String tasinacak){
		Intent uzakservis = new Intent("uzak_aksiyon",null,context, UzakGorunum.class);
		uzakservis.putExtra("tasiyici",tasinacak);
		rv.setRemoteAdapter(R.id.liste_yazi,uzakservis);
		awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(cn),R.id.liste_yazi);
		tazele();
	}
	
	public void sensorListe(Context context){
        Intent intent = new Intent(Kisisel.ACTION_ORTA,null,context,Bilgilendirme.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
	
	public void tazele(){
		//rv.setTextViewText(R.id.yazi,s);
		awm.updateAppWidget(cn,rv);
	}
}
