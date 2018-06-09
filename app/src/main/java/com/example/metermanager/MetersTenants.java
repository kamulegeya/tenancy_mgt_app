package com.example.metermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.CurrentTenants;
import meters.model.MeterReading;
import meters.model.Meters;
import meters.model.TenantList;
import meters.model.TenantMeters;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MetersTenants extends Activity {
	static final int Date_dialog_licker=0;	
	private DatabaseHelperClass db;
	private List<CurrentTenants> tenants =new ArrayList<CurrentTenants>();	
	private List<Meters> meters =new ArrayList<Meters>();	
	// tenants list
	Spinner spinner;
	// meter list
	Spinner spinner1;	
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	// date picker
	
	 private  int year,month,day;
	 // date textbox
	 TextView txtviewreadingDate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.allocate_meters);
		// tenant spinner
		db=  DatabaseHelperClass.getInstance( this);	
		 txtviewreadingDate=(TextView)findViewById(R.id.txt_date_allocated);	
		try{
			tenants=db.GetCurrentTenants();
			 // create an array list of 
			 List<String> tenantlist= new  ArrayList<String>();
			 
			 for( CurrentTenants m:tenants)
			 {
				 tenantlist.add(m.getFullName());
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.tenant_allocate_m);
			 // add event listening			 
			 
		     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter); 
		
		/// spinner 2
		meters=db.GetMeterIDs();
		 // create an array list of 
		 List<String> metelist= new  ArrayList<String>();
		 
		 for( Meters m:meters)
		 {
			 metelist.add(m.getMeterNumber());
		 }
		 //Creating adapter for spinner
		 spinner1 = (Spinner) findViewById(R.id.choose_meter);
		 // add event listening			 
		 
	     ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,metelist);
	     dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner1.setAdapter(dataAdapter1); 
	setCurrentDate();
		
		}catch(Exception  e)
		{
			Log.e("Meter Tenant", e.toString());
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
	
	public boolean CheckData()
	{
		// check meter reading, power rate
		EditText txtMeterReading =(EditText)
				findViewById(R.id.meterReading);
		String reading =txtMeterReading.getText().toString();
		
		EditText txtPoweRate=(EditText)
				findViewById(R.id.txtRate);
		String rate = txtPoweRate.getText().toString();		
		if (reading.length()==0 || rate.length()==0){
			return false;
		}
		else
		{
		return true;
		}
	}
	
	public void Cancel(View v)
	{
		EditText txtMeterReading =(EditText)
				findViewById(R.id.meterReading);
		txtMeterReading.getText().clear();
		EditText txtPoweRate=(EditText)
				findViewById(R.id.txtRate);
		txtPoweRate.getText().clear();
	}
	public void AddNew(View v)
	{
		/*
		 can not allocate a meter already given to an active tenant.
		 if tenant left, first make that tenant inactive and allocate the meter.
		 */
		// get tenant id from tenant spinner
		// get meter id from meter spinner
		// check data
		if(CheckData()==false)
		{
			// get a toast
			Context context = this.getApplicationContext();			
			CharSequence msg ="You must enter Meter Reading and  Power rate";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();	
			return;
		}
		int pos = spinner.getSelectedItemPosition();
		// get the tenant selected  in the list
		if(pos==-1)
		{
			Toast.makeText(getApplicationContext(), "No Tenants Tenant data entered", Toast.LENGTH_LONG).show();
			return;
		}	
		CurrentTenants tenant=(CurrentTenants)tenants.get(pos);
		// get the ID
		int tenat_id =tenant.get_id();
		
		int pos1 = spinner1.getSelectedItemPosition();
		// get the meter selected  in the list
		if(pos1==-1)
		{
			Toast.makeText(getApplicationContext(), "No Meters data entered", Toast.LENGTH_LONG).show();
			return;
		}
		Meters m=(Meters)meters.get(pos1);
		// get the ID
		int meterid =m.get_id();
		// check status
		// check null meters/ tenants
		
		if(tenant==null||m==null)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="You must first Tenants and Meters before trying to allocate them";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
			
			
		}
		if(db.CanAllocateMeter(meterid, tenat_id)==false)
		{
			// get a toast
			Context context = this.getApplicationContext();			
			CharSequence msg ="The Tenant has a meter or meter already allocated.";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		else
		{
			// it was false
			// add new record
			// get date
			try {
			TextView textstartdate=(TextView)
					findViewById(R.id.txt_date_allocated);
			
				java.util.Date dateallocated =fm.parse( textstartdate.getText().toString());
			//allocate meter
			db.AllocateMeter( new TenantMeters(tenat_id,meterid,dateallocated));
			// add meter reading
			EditText txtMeterReading =(EditText)
					findViewById(R.id.meterReading);
			double reading =Double.parseDouble(txtMeterReading.getText().toString());
			
			EditText txtPoweRate=(EditText)
					findViewById(R.id.txtRate);
			double rate = Double.parseDouble(txtPoweRate.getText().toString());
			int new_id= (int)db.getTenantMeterID();
			
			db.AddMeterReading( new MeterReading(new_id,dateallocated,reading,rate));
			/// clear boxes
			txtPoweRate.setText("0");
			txtMeterReading.setText("0");
			
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				Log.e("err", e.toString());
			}
			
		}
	}
	 
}