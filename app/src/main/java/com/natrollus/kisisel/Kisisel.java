package com.natrollus.kisisel;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

import com.natrollus.kisisel.araclar.Baglanti;
import com.natrollus.kisisel.araclar.Tasiyici;
import com.natrollus.kisisel.gorunum.UzakGorunum;
import com.natrollus.kisisel.gorunum.UzakGorunumListe;

import java.util.concurrent.ExecutionException;

import static com.natrollus.kisisel.araclar.Tasiyici.setBilgi;
import static com.natrollus.kisisel.araclar.Ortak.logla;
import static com.natrollus.kisisel.araclar.Ortak.tarihGetir;
import static com.natrollus.kisisel.araclar.Tasiyici.setSag;

public class Kisisel extends AppWidgetProvider {
    public static final String ACTION_SAG = "kisisel.action.SAG";
	public static final String ACTION_URL = "kisisel.action.URL";
	public static final String ACTION_NOT = "kisisel.action.SOL";
    public static final String ACTION_AKTIVITE = "kisisel.action.AKTIVITE";
    public static final String ACTION_UZAK_SAG = "kisisel.action.UZAK_SAG_SECIM";
    public static final String ACTION_UZAK_SOL = "kisisel.action.UZAK_SOL_CERCEVE";
	public static final String ACTION_RESIZE = "com.sec.android.widgetapp.APPWIDGET_RESIZE";
    SharedPreferences kayitlar;
    Context context;
	AppWidgetManager awm;
	RemoteViews rv;
	ComponentName cn;
	String s="",o="ana";

	public void init(Context context){
		awm = AppWidgetManager.getInstance(context);
		cn = new ComponentName(context,getClass());
		rv = new RemoteViews(context.getPackageName(),R.layout.ana_alet_zemini);
		this.context = context;
		setBilgi(s);
		setSag(o);
        ayarla();
	}
    @Override
    public void onReceive(Context context,Intent intent) {
        kayitlar = context.getSharedPreferences("notlar", Context.MODE_PRIVATE);
		String aksiyon = intent.getAction();
		switch (aksiyon) {
			case AppWidgetManager.ACTION_APPWIDGET_ENABLED:
				s = "kuruldu";
				break;
            case AppWidgetManager.ACTION_APPWIDGET_UPDATE:
                s = tarihGetir("HH:mm:ss") + " de guncellendi..";
                break;
            case AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED:
                s = "opt";
                break;
            case Kisisel.ACTION_SAG:
				s = "sag";
				break;
			case Kisisel.ACTION_URL:
				baglan("http://natrollus.com","GET");
				break;
			case Kisisel.ACTION_NOT:
				s = "sol";
				break;
			case Kisisel.ACTION_RESIZE:
                s = "yerinden cikti..";
				break;
            case Kisisel.ACTION_UZAK_SAG:
                o = intent.getStringExtra(Tasiyici.SAGDAN);
                break;
            case Kisisel.ACTION_UZAK_SOL:
                logla("burda solda sonlandi..");
                break;
            case Kisisel.ACTION_AKTIVITE:
                String islem = intent.getStringExtra("islem");
                if (islem.equals("not")){
                    s = kayitlar.getString("not",null);
                }
                break;
			default:
				logla("aks:"+aksiyon);
                break;
		}
		logla("o:"+o);
        init(context);
    }

	private void baglan(String url, String metod) {
		Baglanti baglanti = new Baglanti(url,metod);
		boolean baglandi = false;
		try {
			baglandi = baglanti.execute().get();
		} catch (InterruptedException | ExecutionException e) {
			s = e.toString();
		}
		if (baglandi){
			s = baglanti.sonucYaz();
		}
	}

	public void ayarla(){
		listeayarla();
	}

	public void listeayarla(){
        Intent uzak_sag_secim = new Intent(Kisisel.ACTION_UZAK_SAG,null,context, UzakGorunum.class);
		Intent uzak_sol_cerceve = new Intent(Kisisel.ACTION_UZAK_SOL,null,context, UzakGorunum.class);

		awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(cn), R.id.sol_cerceve);

		rv.setRemoteAdapter(R.id.sag_secim_bolgesi, uzak_sag_secim);
        rv.setRemoteAdapter(R.id.sol_cerceve, uzak_sol_cerceve);
        Intent tasiyici = new Intent(context, Tasiyici.class);
        PendingIntent bekleyenTasiyici = PendingIntent.getBroadcast(context,0,tasiyici,PendingIntent.FLAG_UPDATE_CURRENT);
        //rv.setPendingIntentTemplate(R.id.sol_cerceve,bekleyenTasiyici);
        rv.setPendingIntentTemplate(R.id.sag_secim_bolgesi,bekleyenTasiyici);
		tazele();
	}

	public void tazele(){
		awm.updateAppWidget(cn, rv);
	}
	
}
