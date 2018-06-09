package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentBlocks;
import meters.model.Apartments;
import meters.model.TenantList;
import meters.model.UnPaidInvoices;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class AddTenancy extends Activity {
	static final int Date_dialog_licker=0;	
	static final int Date_dialog_licker1=1;	
	// list for dropdownlist
		private List<TenantList> t =new ArrayList<TenantList>();
		//apartments to choose from
		private List<Apartments> b= new ArrayList<Apartments>();
		//dropdown tenants
		Spinner spinner;
		// dropdown appartments
		Spinner spinner1;
		@SuppressLint("SimpleDateFormat")
		private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#,###,###,###");
		// get handle to db helper class
		private DatabaseHelperClass db;
		private  int year,month,day;
		 TextView txtviewstartdate;
		 TextView txtvieweenddate;
		 EditText rental;
		 // hold list of tenants to pick from
		 List<String> tenantlist= new  ArrayList<String>();
		   ArrayAdapter<String> dataAdapter ;
			// list of appartmenst
		 List<String> apartments= new  ArrayList<String>();
		   ArrayAdapter<String> dataAdapter1;
		   // dates 
		   Date sdate=null;
			  Date edate=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_tenancy);
		db=  DatabaseHelperClass.getInstance( this);	
			t=db.GetTenantIDs();
			 // create an array list of 
			 
			 for( TenantList m:t)
			 {
				
				 tenantlist.add(m.getFullName());
				 //Log.e("FullName", m.getFirstName());
				// Log.e("FullName", m.getSurname());
				 
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.add_tenancy);
			 // add event listening
			 
			 
		    dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);  		
		b=db.GetFreeApartment();
		 // create an array list of 
		 
		 for( Apartments bl:b)
		 {
			 String s=bl.getLocation() + " "+ bl.getBlock() +" "+  "Room:"+ bl.getNumber();
			 apartments.add(s);
		 }
		 //Creating adapter for spinner
	      spinner1=(Spinner) findViewById(R.id.select_apartment);
		 // add event listening		 	 
	     dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,apartments);
	     dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner1.setAdapter(dataAdapter1);  	
	 txtviewstartdate=(TextView)findViewById(R.id.start_date);
	 txtvieweenddate=(TextView)findViewById(R.id.end_date);
	 rental= (EditText)
				findViewById(R.id.editTextRental);
	 rental.addTextChangedListener(new NumberTextWatcher(rental));
	 setCurrentDate();
	 setEndDate();
}
	public void setCurrentDate()
	{
		final Calendar c =Calendar.getInstance( Locale.UK);
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH);		
		 txtviewstartdate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
			    
	}
	public void setEndDate()
	{
		final Calendar c =Calendar.getInstance( Locale.UK);
		year=c.get(Calendar.YEAR)+1;
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH)-1;		
		 txtvieweenddate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
		    
	}
	
	
	private DatePickerDialog.OnDateSetListener mDateSetListener =
	new DatePickerDialog.OnDateSetListener() {	
		// the callback received when the user "sets" the Date in the DatePickerDialog
	    public void onDateSet(DatePicker view, int yearSelected,
	                          int monthOfYear, int dayOfMonth) {
	       year = yearSelected;
	      
	       month = monthOfYear;
	       
	       day = dayOfMonth;   
	       
	       txtviewstartdate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
	       
	       
	       
	    }
	       
	       
	       
	};
	
	private DatePickerDialog.OnDateSetListener mDateSetListener1 =
			new DatePickerDialog.OnDateSetListener() {	
				// the callback received when the user "sets" the Date in the DatePickerDialog
			    public void onDateSet(DatePicker view, int yearSelected,
			                          int monthOfYear, int dayOfMonth) {
			       year = yearSelected;
			      
			       month = monthOfYear;
			       
			       day = dayOfMonth;  
			       	       
			       
			       txtvieweenddate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
			       
			    }
			       
			       
			       
			};
	
	@Override
	protected Dialog onCreateDialog(int id)
	{
		
		switch (id){
		case 0:
		   return new DatePickerDialog(this,
	            mDateSetListener,
	            year,month,day);
		   
		case 1:
			   return new DatePickerDialog(this,
		            mDateSetListener1,
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
	@SuppressWarnings("deprecation")
	public void AddEndDate(View v)

	{
		try{
		showDialog(1);
		} catch(Exception e)
		{
			Log.e("Dialog Err", e.toString());
		}
	}
	public void Add(View v)
	{
		// get tenant id
		// check whether appartment occupied ...and still active
		// check tenants..if same tenant? ask whether want to renew and end previous tenacy
		// test overlapping tenancy
		int pos= spinner.getSelectedItemPosition();
		if(pos==-1)
		{
			Toast.makeText(getApplicationContext(), "No Tenants   data entered. Can not add a Tenancy without Tenant data", Toast.LENGTH_LONG).show();
			return;
		}
		TenantList tenant= (TenantList)t.get(pos);
		
		// get tenant id
		int tenant_id= tenant.get_id();
		// get tenant name
		final String tname= tenant.getFullName();
		int pos1= spinner1.getSelectedItemPosition();
		if(pos1==-1)
		{
			Toast.makeText(getApplicationContext(), "No Apartments   data entered. Can not add a Tenancy without Apartment data", Toast.LENGTH_LONG).show();
			return;
		}
		Apartments a = (Apartments)b.get(pos1);
		
		//get apartment id
		int apartment_id= a.get_id();
		// get room number
		final String room = a.getNumber();
		// tenant has a valid tenancy
		if(db.HasValidTenancy(tenant_id,apartment_id)==true)
		{
			// inform user and exit
						// make a toast
									Context context = this.getApplicationContext();			
									CharSequence msg ="This Tenant has a vaild running teanancy"+ "\n"+
									                  "If the tenancy  has expired  first make it  expired"+ "\n"+
											           "Before creating a new tenancy";
									int duration =Toast.LENGTH_LONG;
									Toast toast = Toast.makeText(context, msg, duration);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
									return;
		}
				
		
		
		try {
			 sdate = fm.parse(txtviewstartdate.getText().toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
		 edate =fm.parse( txtvieweenddate.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String startdate = fm.format(sdate);
		String enddate =fm.format(edate);
		
			// test rent ented
		if(rental.getText().toString().length()==0)
		{
			// inform user and exit
			// make a toast
						Context context = this.getApplicationContext();			
						CharSequence msg ="You must enter the Monthly Rental amount";						                  
						int duration =Toast.LENGTH_LONG;
						Toast toast = Toast.makeText(context, msg, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
		}
		double rent = Double.parseDouble(rental.getText().toString().replace(",",""));
	// apartment free?
		
		if(db.ApartmentNotFree(apartment_id,startdate)==true)
		{
			// inform user and exit
			// make a toast
						Context context = this.getApplicationContext();			
						CharSequence msg ="This apartment not free"+ "\n"+
						                  "If the tenant left, first teminate the Tenancy";
						int duration =Toast.LENGTH_LONG;
						Toast toast = Toast.makeText(context, msg, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
			
		}
		try
		{
		db.AddTenancy(tenant_id, apartment_id, startdate, enddate, rent, 0);
		Toast.makeText(getApplicationContext(), "Record added.", Toast.LENGTH_LONG).show();		
	
		// can u refresh data on spinners?
		// remove apartment
		// remove tenant
		t.remove(pos);
		b.remove(pos1);
		tenantlist.remove(pos);
		apartments.remove(pos1);
		dataAdapter.notifyDataSetChanged();
		dataAdapter1.notifyDataSetChanged();
		// clear
		rental.setText("0");
		// add a calender reminder event?
		// description of tenancy
		
		AlertDialog.Builder b= new AlertDialog.Builder(this);
		b.setTitle("Add Calendar Reminder");
		b.setMessage("Do you want to add  a calender reminder when this Tenancy Expires?");				     
		b.setNegativeButton("cancel", null);
		b.setIcon(R.drawable.ic_launcher);
		b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
			@Override
	 public void onClick(DialogInterface dialog, int which) {		 	 
	           Date d=null;
				 try {
					d = fm.parse(txtvieweenddate.getText().toString());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				
	            String msg= "Tenancy expiry date for: " + tname + " Room: "+ room;
				Intent intent = new Intent(Intent.ACTION_EDIT);
		        intent.setType("vnd.android.cursor.item/event");
		        intent.putExtra("beginTime", d.getTime());
		        intent.putExtra("allDay", true);       
		        intent.putExtra("endTime", d.getTime());
		        intent.putExtra("title", "Tenancy Expiry Date");
		        intent.putExtra("description", msg);
		        startActivity(intent);	
	
			}
		});
		b.show();	
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Err adding the Tenancy.Try again", Toast.LENGTH_LONG).show();
		}
		
	}
	public void Cancel(View v)
	{
		this.finish();
	}
	
	
	
}
