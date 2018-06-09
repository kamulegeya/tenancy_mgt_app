package com.example.metermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.MeterReading;
import meters.model.TenantMeterID;
import meters.model.Tenants;
import meters.model.VMeterReadings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ReadMeter extends Activity {
	static final int Date_dialog_licker=0;	
	private DatabaseHelperClass db;
	private List<TenantMeterID> readings =new ArrayList<TenantMeterID>();
	Spinner spinner;	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	 private  int year,month,day;
	 TextView txtviewreadingDate;
	 EditText reading;
	 EditText rate;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	 setContentView(R.layout.read_meter);
	 db=  DatabaseHelperClass.getInstance( this);
	 readings=db.GetTenantMeters_Reading();
	 // create an array list of 
	 List<String> tenantlist= new  ArrayList<String>();
	 
	 for( TenantMeterID m:readings)
	 {
		 tenantlist.add(m.getFullName());
	 }
	// Creating adapter for spinner
	 spinner = (Spinner) findViewById(R.id.selectTenant);
	 // here wrong. cross check
     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
     dataAdapter
     .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// attaching data adapter to spinner
spinner.setAdapter(dataAdapter);   
txtviewreadingDate =(TextView)findViewById(R.id.txtReading_Date);	
 rate= (EditText)findViewById(R.id.editText3);
reading= (EditText)findViewById(R.id.editText2);
reading.setText("0");
rate.setText("0");
setCurrentDate();
}

public void Cancel(View v)
{
	EditText reading= (EditText)findViewById(R.id.editText2);	
	EditText rate= (EditText)findViewById(R.id.editText3);	
	reading.getText().clear();
	rate.getText().clear();
	
}
public void AddNew(View view) throws ParseException
{
	try{
		TextView readDate= (TextView)findViewById(R.id.txtReading_Date);	
		Date myreadingdate = fm.parse(readDate.getText().toString());	
				
		double myreading = Double.parseDouble(reading.getText().toString());
					
		Double myrate =Double.parseDouble(rate.getText().toString());
		
		Spinner spinner= (Spinner)findViewById(R.id.selectTenant);
		int pos = spinner.getSelectedItemPosition();
		if(pos==-1)		{
			Toast.makeText(getApplicationContext(), "No Meters allocated yet", Toast.LENGTH_LONG).show();
			return;
		}
		TenantMeterID tenant=(TenantMeterID)readings.get(pos);
		int id = tenant.get_id();
		// check reading for that day
		Date test= fm.parse( readDate.getText().toString());
		Date mdate= new Date();		
		if(mdate.before(test)==true)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Meter Reading  Date can not be in the future";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		
		//Log.e("Testing Date String", fm.format(test));
		if (db.ReadingMade(id,fm.format(test))== true)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Reading for this Date and  Tenant already taken";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
				
		db.AddMeterReading( new MeterReading(id,myreadingdate,myreading,myrate));	
		//clear entry boxes	  
		reading.getText().clear();
		rate.getText().clear();		
		} catch(NumberFormatException e)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Meter Reading and  Power  rate must be entered";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			
		}

	
}
public void setCurrentDate()
{
	final Calendar c =Calendar.getInstance( Locale.UK);
	year=c.get(Calendar.YEAR);
	month=c.get(Calendar.MONTH);
	day=c.get(Calendar.DAY_OF_MONTH);
	
 	   txtviewreadingDate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
    
}
private DatePickerDialog.OnDateSetListener mDateSetListener =
new DatePickerDialog.OnDateSetListener() {	
	// the callback received when the user "sets" the Date in the DatePickerDialog
    public void onDateSet(DatePicker view, int yearSelected,
                          int monthOfYear, int dayOfMonth) {
       year = yearSelected;
      
       month = monthOfYear;
       
       day = dayOfMonth;   
       
        txtviewreadingDate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
       
       
    }
       
       
       
};
@Override
protected Dialog onCreateDialog(int id)
{
	
	switch (id){
	case Date_dialog_licker:
	   return new DatePickerDialog(this,
            mDateSetListener,
            year,month,day);			
	
	}
	return null;
	
	
	
	
}

@SuppressWarnings("deprecation")
public void AddDate(View v)

{
	try{
	showDialog(0);
	} catch(Exception e)
	{
		Log.e("Dialog Err", e.toString());
	}
}









}
