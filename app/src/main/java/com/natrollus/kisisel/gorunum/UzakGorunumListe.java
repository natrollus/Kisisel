package com.natrollus.kisisel.gorunum;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.natrollus.kisisel.Kisisel;
import com.natrollus.kisisel.R;
import com.natrollus.kisisel.araclar.Tasiyici;

import static com.natrollus.kisisel.araclar.Ortak.logla;
import static com.natrollus.kisisel.araclar.Tasiyici.getBilgi;
import static com.natrollus.kisisel.araclar.Tasiyici.getSag;

public class UzakGorunumListe implements RemoteViewsFactory {

    Context context;
    Intent intent;
    RemoteViews satir;
    String paketIsmi = null;
    String aksiyon = null;
	

    public UzakGorunumListe(Context context, Intent intent) {
        this.context = context;
        this.paketIsmi = context.getPackageName();
        this.intent = intent;
        this.aksiyon = intent.getAction();
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
		if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)){
            logla("sag data degisti:" + getBilgi());
        } else if (aksiyon.equals(Kisisel.ACTION_UZAK_SOL)) {
            logla("sol data degisti:" + getSag());
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)) {
            return Tasiyici.SAG_SECIM_LISTE_BASLIKLARI.length;
        } else {
            return 1;
        }
    }

    @Override
    public RemoteViews getViewAt(int pos) {
        satir = null;
        if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)){
            satir = new RemoteViews(paketIsmi, R.layout.sag_li);
            satir.setTextViewText(R.id.sag_li_yazi, Tasiyici.SAG_SECIM_LISTE_BASLIKLARI[pos]);
            logla("aksiyon:"+intent.getAction());
            Intent doldur = new Intent(intent.getAction());
            doldur.putExtra(Tasiyici.SAGDAN, Tasiyici.SAG_SECIM_LISTE_BASLIKLARI[pos]);
            satir.setOnClickFillInIntent(R.id.sag_li_taban, doldur);
        } else if (aksiyon.equals(Kisisel.ACTION_UZAK_SOL)) {
            String sag = getSag();
            switch (sag) {
                case "ana":
                    satir = new RemoteViews(paketIsmi, R.layout.sol_li_normal);
                    satir.setTextViewText(R.id.sol_li_normal_yazi, getBilgi());
                    Intent ana = new Intent(intent.getAction());
                    ana.putExtra(Tasiyici.SOLDAN, "soldan..");
                    satir.setOnClickFillInIntent(R.id.sol_li_normal_taban, ana);
                    break;
                case "acil":
                    satir = new RemoteViews(paketIsmi, R.layout.sol_li_acil);
                    satir.setTextViewText(R.id.sol_li_acil_yazi, paketIsmi);
                    Intent acil = new Intent(intent.getAction());
                    acil.putExtra(Tasiyici.SOLDAN,"acil sag");
                    satir.setOnClickFillInIntent(R.id.sol_li_acil_buton,acil);
                    break;
                case "yaz":
                    satir = new RemoteViews(paketIsmi, R.layout.sol_li_normal);
                    satir.setTextViewText(R.id.sol_li_normal_yazi, "yazdan yaz buda:" + sag);
                    break;
                default:
                    satir = new RemoteViews(paketIsmi, R.layout.sol_li_normal);
                    satir.setTextViewText(R.id.sol_li_normal_yazi, "sag nedir:" + sag);
                    break;
            }

        }
        return satir;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
