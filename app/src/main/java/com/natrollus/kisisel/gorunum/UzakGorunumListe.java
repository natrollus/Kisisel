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

public class UzakGorunumListe implements RemoteViewsFactory {

    Context context;
    Intent intent;
    RemoteViews satir;
    String aksiyon = null;
	

    public UzakGorunumListe(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
        this.aksiyon = intent.getAction();
        if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)){
            this.satir = new RemoteViews(context.getPackageName(), R.layout.sag_secim_liste_itemi);
        } else if (aksiyon.equals(Kisisel.ACTION_UZAK_SOL)) {
            this.satir = new RemoteViews(context.getPackageName(),R.layout.sol_cerceve_liste_itemi);
        }
    }


    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
		if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)){
            logla("data degisti sag:"+ getBilgi());
        } else if (aksiyon.equals(Kisisel.ACTION_UZAK_SOL)) {
            logla("data degisti sol");
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
        if (aksiyon.equals(Kisisel.ACTION_UZAK_SAG)){
            satir.setTextViewText(R.id.sag_secim_liste_itemi_yazi, Tasiyici.SAG_SECIM_LISTE_BASLIKLARI[pos]);
            Intent doldur = new Intent(intent.getAction());
            doldur.putExtra(Tasiyici.SAGDAN, Tasiyici.SAG_SECIM_LISTE_BASLIKLARI[pos]);
            satir.setOnClickFillInIntent(R.id.sag_secim_liste_itemi_taban,doldur);
        } else if (aksiyon.equals(Kisisel.ACTION_UZAK_SOL)) {
            satir.setTextViewText(R.id.sol_cerceve_liste_itemi_yazi, "simdilik");
            Intent doldur = new Intent(intent.getAction());
            doldur.putExtra(Tasiyici.SOLDAN,"soldan..");
            satir.setOnClickFillInIntent(R.id.sol_cerceve_liste_itemi_taban, doldur);
        }
        return satir;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
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
