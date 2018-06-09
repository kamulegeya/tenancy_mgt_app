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
import android.widget.TextView;
import android.widget.Toast;

public class MetersInActive extends Activity {
Meters meter;
DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.meters_inactive);
		Intent i= getIntent();
		meter=(Meters)i.getSerializableExtra("meter");
		TextView number =(TextView)
				   findViewById(R.id.txtmeternumber);
		number.setText(meter.getMeterNumber());
		Button bsave=(Button)
				findViewById(R.id.btnSave);
		db= DatabaseHelperClass.getInstance(getApplicationContext());
		 bsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText endate= (EditText)
						findViewById(R.id.txtInactive_Date);
				String mydate= endate.getText().toString().trim();
				if(mydate.length()==0)
				{
					Toast.makeText(getApplicationContext(), "You must enter Inactive date", Toast.LENGTH_LONG).show();
					return;
				}
				if(db.IsvalidDate(mydate)==false)
				{
					Toast.makeText(getApplicationContext(), "Enter Dates in format yyyy/mmm/dd", Toast.LENGTH_LONG).show();
					return;
				}
				int meterid= meter.get_id();
				db.meterInActive(meterid, mydate);
				Toast.makeText(getApplicationContext(), "Meter Made Inactive", Toast.LENGTH_LONG).show();
				
				
			}
		});
		
	}
	

}
