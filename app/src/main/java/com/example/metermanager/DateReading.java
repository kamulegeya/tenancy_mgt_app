package com.example.metermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class DateReading extends Activity {
private DateReadings dr;
ListView list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices);
		list=(ListView)
				findViewById(R.id.invoices_list);
		Intent i= getIntent();
		String date = i.getStringExtra("Date");
		dr= new DateReadings(this, date);
		list.setAdapter(dr);
		
	}
	
}
