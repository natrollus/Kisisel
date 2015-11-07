package com.natrollus.kisisel.gorunum;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService.RemoteViewsFactory;

import com.natrollus.kisisel.R;

import static com.natrollus.kisisel.araclar.Ortak.logla;

public class UzakGorunumListe implements RemoteViewsFactory {

    Context context;
    Intent intent;
	

    public UzakGorunumListe(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
		logla("cikti="+ intent.getStringExtra("tasiyici"));
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews satir;
        satir = new RemoteViews(context.getPackageName(), R.layout.widget_liste_itemi);
        String tasinan = intent.getStringExtra("tasiyici");
        satir.setTextViewText(R.id.widget_liste_itemi_yazi, tasinan);
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
