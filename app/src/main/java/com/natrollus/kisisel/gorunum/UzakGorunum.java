package com.natrollus.kisisel.gorunum;

import android.content.Intent;
import android.widget.RemoteViewsService;

import static com.natrollus.kisisel.araclar.Ortak.logla;

public class UzakGorunum extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new UzakGorunumListe(getApplicationContext(),intent);
    }
}
