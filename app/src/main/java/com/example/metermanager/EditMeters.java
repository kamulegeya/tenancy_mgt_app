package com.example.metermanager;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Meters;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMeters extends Activity {
Meters meters;
Button b;
DatabaseHelperClass db;
	EditText serial;
	EditText meternumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_meter);
		db= DatabaseHelperClass.getInstance(getApplicationContext());
		Intent i= getIntent();
		 meters = (Meters) i.getSerializableExtra("meter");
		String sernumber= meters.getSerialNumber();
		String numbers= meters.getMeterNumber();
		serial=(EditText)
				findViewById(R.id.editText1);
		serial.setText(sernumber);
		meternumber=(EditText)
				findViewById(R.id.editText2);
		meternumber.setText(numbers);
		b=(Button)
				findViewById(R.id.btnSave);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try
				{
				Meters m= new Meters();
				String sn= serial.getText().toString().trim();
				String n= meternumber.getText().toString().trim();
				if(sn.length()==0||n.length()==0)
				{
					Toast.makeText(getApplicationContext(), "You must enter both Serial Number and Meter Number", Toast.LENGTH_LONG).show();
				    return;
				}
				
				m.set_id(meters.get_id());
				m.setSerialNumber(sn);
				m.setMeterNumber(n);
			    db.EditMeter(m);
			    Toast.makeText(getApplicationContext(), "Edits Saved", Toast.LENGTH_LONG).show();
				}catch(Exception e)
				{
					 Toast.makeText(getApplicationContext(), "Err.Edits  not Saved", Toast.LENGTH_LONG).show();					
					e.printStackTrace();
				}
			}
			
		});
	}
	
}
