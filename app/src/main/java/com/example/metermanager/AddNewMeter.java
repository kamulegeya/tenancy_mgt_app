package com.example.metermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Meters;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewMeter extends Activity {
	private DatabaseHelperClass db;
	 SimpleDateFormat df = new SimpleDateFormat(
	            "dd:MMMM:yyyy HH:mm:ss a", Locale.getDefault());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_meter);
		db=  DatabaseHelperClass.getInstance( this);	
	}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	return super.onCreateOptionsMenu(menu);
}
public void AddMeter(View v)

{
	try{
	EditText serialnumberText= (EditText)
			findViewById(R.id.editText1);
	String SerialNumber = serialnumberText.getText().toString();
	
	EditText MeternumberText= (EditText)
			findViewById(R.id.editText2);
	String MeterNumber = MeternumberText.getText().toString();
	Date dateadded =null;
	try {
			dateadded = df.parse(db.getDateTime());
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(SerialNumber.length()==0||MeterNumber.length()==0)
	{
	Context context = this.getApplicationContext();			
	CharSequence msg ="Serial number and Meter number are required";			
	int duration =Toast.LENGTH_LONG;
	Toast toast = Toast.makeText(context, msg, duration);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
	return;
	}
	
	db.AddMeter( new Meters( SerialNumber,MeterNumber,dateadded));
	//clear entries.
	serialnumberText.getText().clear();	
	MeternumberText.getText().clear();
	
	Context context = this.getApplicationContext();			
	CharSequence msg ="Record added";			
	int duration =Toast.LENGTH_LONG;
	Toast toast = Toast.makeText(context, msg, duration);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
	}catch(Exception e)
	{
		e.printStackTrace();
	}
}

public void Clear(View v)
{
	EditText serialnumberText= (EditText)
			findViewById(R.id.editText1);
	serialnumberText.getText().clear();
	EditText MeternumberText= (EditText)
			findViewById(R.id.editText2);
	MeternumberText.getText().clear();
}

}
