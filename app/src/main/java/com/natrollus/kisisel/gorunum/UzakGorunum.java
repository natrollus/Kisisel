package com.natrollus.kisisel.gorunum;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by byram on 10/31/15.
 */
public class UzakGorunum extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new UzakGorunumListe(getApplicationContext(),intent);
    }
}
