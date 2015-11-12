package com.natrollus.kisisel.aktivite;
import android.app.*;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.*;
import android.view.View;
import android.widget.*;

import com.natrollus.kisisel.Kisisel;
import com.natrollus.kisisel.R;

import static com.natrollus.kisisel.araclar.Ortak.logla;

public class Not extends Activity {
	EditText girdi;
	Button kaydet_buton;
	SharedPreferences kayitlar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		kayitlar = getApplicationContext().getSharedPreferences("notlar",MODE_PRIVATE);
		setContentView(R.layout.a_not);
		girdi = (EditText) findViewById(R.id.girdi);
		girdi.setText(kayitlar.getString("not", "bos"));
		kaydet_buton = (Button) findViewById(R.id.kaydet);
		kaydet_buton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				kaydet();
			}
		});
	}

	private void kaydet() {
		Intent intent = new Intent(Kisisel.ACTION_AKTIVITE,null,getApplicationContext(), Kisisel.class);
		intent.putExtra("islem","not");
		PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0,intent,0);
		boolean sonuc = kayitlar.edit().putString("not",girdi.getText().toString()).commit();
		if (sonuc) {
			try {
				pi.send();
			} catch (PendingIntent.CanceledException e) {
				e.printStackTrace();
			}
			finish();
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		kaydet();
		finish();
	}
}
