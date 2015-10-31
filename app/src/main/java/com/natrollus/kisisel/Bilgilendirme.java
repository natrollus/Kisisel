package com.natrollus.kisisel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class Bilgilendirme extends Activity {
    SensorManager sm;
    String s="";
    TextView bilgilendirme;
    ListView liste;
    List<Sensor> sensorler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String aks = intent.getAction();

        if (aks.equals(Intent.ACTION_MAIN)) {
            Toast.makeText(this,"kisisel widget kurulali cok oldu..",Toast.LENGTH_LONG).show();
            finish();
        } else if (aks.equals(Kisisel.ACTION_ORTA)) {
            setContentView(R.layout.bilgilendirme);
            bilgilendirme = (TextView) findViewById(R.id.bilgilendirme);
            liste = (ListView) findViewById(R.id.liste);
            sm = (SensorManager) getSystemService("sensor");
            sensorler = sm.getSensorList(Sensor.TYPE_ALL);
			Sensor hrm = sm.getDefaultSensor(65562);
			if(hrm!=null){
				bilgilendirme.setText("deell");
			} else {
                if (hrm != null) {
                    bilgilendirme.setText("boyut:"+sensorler.size()+" hrm:"+hrm.getName());
                }
            }
            liste.setAdapter(new Listeleme());
            liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    SensorEventListener sel = new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent event) {
                            String s = "";
                            float deger;
                            for (int i = 0; i < event.values.length; i++) {
                                deger = event.values[i];
                                s += i + ":" + deger + " ";
                            }
                            bilgilendirme.setText(s);
                        }
                        @Override
                        public void onAccuracyChanged(Sensor sensor, int accuracy) {

                        }
                    };
                    sm.unregisterListener(sel);
                    sm.registerListener(sel, sensorler.get(position), SensorManager.SENSOR_DELAY_GAME);
                }
            });

        }
        super.onCreate(savedInstanceState);
    }
    private void ayarla() {
		
    }

    private class Listeleme extends BaseAdapter {
        LayoutInflater inflater;

        public Listeleme() {
            this.inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return sensorler.size();
        }

        @Override
        public Object getItem(int position) {
            return sensorler.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.liste_itemi,null);
            }
            Sensor sensor = sensorler.get(position);
            TextView ustyazi = (TextView) convertView.findViewById(R.id.ustyazi);
            TextView altyazi = (TextView) convertView.findViewById(R.id.altyazi);
            ustyazi.setText(sensor.getName());
            altyazi.setText(sensor.getType()+" "+sensor.getVendor()+" "+sensor.getMaximumRange());

            return convertView;
        }
    }
}
