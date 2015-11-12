package com.natrollus.kisisel.diger;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.natrollus.kisisel.Kisisel;
import com.natrollus.kisisel.R;

import java.util.List;

public class Bilgilendirme extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String aks = intent.getAction();

        if (aks.equals(Intent.ACTION_MAIN)) {
            Toast.makeText(this,"kisisel widget kurulali cok oldu..",Toast.LENGTH_LONG).show();
            finish();
        }
        super.onCreate(savedInstanceState);
    }
}
