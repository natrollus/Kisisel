package com.natrollus.kisisel.aktivite;
import android.app.*;
import android.os.*;
import android.widget.*;

public class Not extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		super.onCreate(savedInstanceState, persistentState);
		Toast.makeText(getApplicationContext(),"selam",Toast.LENGTH_LONG).show();
	}
}
