package com.natrollus.kisisel;

import android.app.*;
import android.appwidget.*;
import android.content.*;
import android.widget.*;

import com.natrollus.kisisel.araclar.Baglanti;
import com.natrollus.kisisel.araclar.Tasiyici;
import com.natrollus.kisisel.gorunum.UzakGorunum;

import java.util.concurrent.ExecutionException;

import static com.natrollus.kisisel.araclar.Tasiyici.ORTADAN;
import static com.natrollus.kisisel.araclar.Tasiyici.setBilgi;
import static com.natrollus.kisisel.araclar.Ortak.logla;
import static com.natrollus.kisisel.araclar.Ortak.tarihGetir;

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
	String s="",o="";

	public void init(Context context){
		awm = AppWidgetManager.getInstance(context);
		cn = new ComponentName(context,getClass());
		rv = new RemoteViews(context.getPackageName(),R.layout.ana_alet_zemini);
		this.context = context;
        setBilgi(s);
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
                s = intent.getStringExtra(Tasiyici.SAGDAN);
                logla("sagdan son:"+s);
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
		//int[] butonlar = {R.id.sagla,R.id.ortala,R.id.solla};
		String[] aksiyon = {Kisisel.ACTION_SAG,Kisisel.ACTION_URL,Kisisel.ACTION_NOT};
		for (int i = 0; i < 3; i++) {
			Intent intent = new Intent(aksiyon[i],null,context,getClass());
			PendingIntent pi = PendingIntent.getBroadcast(context,0,intent,0);
			//rv.setOnClickPendingIntent(butonlar[i], pi);
		}
		listeayarla(o);
	}

	public void listeayarla(String data){
        Intent uzak_sol_cerceve = new Intent(Kisisel.ACTION_UZAK_SOL,null,context, UzakGorunum.class);
        Intent uzak_sag_secim = new Intent(Kisisel.ACTION_UZAK_SAG,null,context, UzakGorunum.class);
		//awm.notifyAppWidgetViewDataChanged(awm.getAppWidgetIds(cn), R.id.sol_cerceve);

        rv.setRemoteAdapter(R.id.sol_cerceve, uzak_sol_cerceve);
        rv.setRemoteAdapter(R.id.sag_secim_bolgesi, uzak_sag_secim);
        Intent tasiyici = new Intent(context, Tasiyici.class);
        tasiyici.putExtra(ORTADAN,data);
        PendingIntent bekleyenTasiyici = PendingIntent.getBroadcast(context,0,tasiyici,PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.sol_cerceve,bekleyenTasiyici);
        rv.setPendingIntentTemplate(R.id.sag_secim_bolgesi,bekleyenTasiyici);
		tazele();
	}

	public void tazele(){
		//rv.setTextViewText(R.id.yazi,s);
		awm.updateAppWidget(cn, rv);
	}
	
}
