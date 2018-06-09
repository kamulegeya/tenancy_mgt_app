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
import meters.model.ActiveTenancy;
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

public class ProcessRentalInvoice extends Activity {
	static final int Date_dialog_licker=0;	
	static final int Date_dialog_licker1=1;	
	static final int Date_dialog_licker2=2;	
	
	// list for dropdownlist		
		private List<ActiveTenancy> b= new ArrayList<ActiveTenancy>();
		//dropdown tenants
		Spinner spinner;
		;
		@SuppressLint("SimpleDateFormat")
		private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("#,###,###,###");
		// get handle to db helper class
		private DatabaseHelperClass db;
		private  int year,month,day;
		 TextView txtviewstartdate;
		 TextView txtvieweenddate;
		 TextView txtviewduedate;
		 // hold list of invoices to pick from
		 List<String> tenancy= new  ArrayList<String>();
		   ArrayAdapter<String> dataAdapter ;
		   EditText rent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_rental_invoices);
		db= DatabaseHelperClass.getInstance( this);	
		Intent i= getIntent();
		b= i.getParcelableArrayListExtra("InvoiceList");		
		
		if(b==null)
		{
		b=db.GetActiveTenancy();
		}
		 // create an array list of 
		 
		 for( ActiveTenancy  m:b)
		 {
			
			 String dropdown= m.getFullName() + ":" + " Room: " + m.getApartment();
			 tenancy.add(dropdown);
			
			 
		 }
		 //Creating adapter for spinner
		 spinner = (Spinner) findViewById(R.id.tenancy);
		 // add event listening
		 
		 
	    dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, tenancy);
	     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter);  	
	
 txtviewstartdate=(TextView)findViewById(R.id.start_date);
 txtvieweenddate=(TextView)findViewById(R.id.end_date);
 txtviewduedate=(TextView)findViewById(R.id.due_date);
  rent =(EditText)
			findViewById(R.id.editTextRental);
 rent.addTextChangedListener(new NumberTextWatcher(rent));
 setCurrentDate();
 setEndDate();
 setDueDate();
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
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH);		
		 txtvieweenddate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
		    
	}
	
	public void setDueDate()
	{
		final Calendar c =Calendar.getInstance( Locale.UK);
		year=c.get(Calendar.YEAR);
		month=c.get(Calendar.MONTH);
		day=c.get(Calendar.DAY_OF_MONTH);		
		 txtviewduedate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
		    
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
			private DatePickerDialog.OnDateSetListener mDateSetListener2 =
					new DatePickerDialog.OnDateSetListener() {	
						// the callback received when the user "sets" the Date in the DatePickerDialog
					    public void onDateSet(DatePicker view, int yearSelected,
					                          int monthOfYear, int dayOfMonth) {
					       year = yearSelected;
					      
					       month = monthOfYear;
					       
					       day = dayOfMonth;  
					       	       
					       
					       txtviewduedate.setText(new StringBuilder().append(year).append("-").append(month+1).append("-").append(day).append("")); 
					       
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
			   
		case 2:
			   return new DatePickerDialog(this,
		            mDateSetListener2,
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
	@SuppressWarnings("deprecation")
	public void AddDueDate(View v)

	{
		try{
		showDialog(2);
		} catch(Exception e)
		{
			Log.e("Dialog Err", e.toString());
		}
	}
	public void Add(View v) throws ParseException
	
	{
		// get tenancy id
		int pos = spinner.getSelectedItemPosition();
		if(pos==-1)
		{
			Toast.makeText(getApplicationContext(), "No tenancy info entered in order to make invoices", Toast.LENGTH_LONG).show();
			return;
		}
		ActiveTenancy tenant=(ActiveTenancy)b.get(pos);
		final int tenancyid= tenant.getTenancy_id();
		Log.e("tID", Integer.toString(tenancyid));
		TextView startdate =(TextView)
				 findViewById(R.id.start_date);
		TextView enddate =(TextView)
				 findViewById(R.id.end_date);
		TextView duedate =(TextView)
				 findViewById(R.id.due_date);
		
		final double rental= Double.parseDouble(rent.getText().toString().replace(",", ""));
		Date mystartdate = fm.parse(startdate.getText().toString());
		Date eenddate =fm.parse(enddate.getText().toString());
		Date ddudate =fm.parse(duedate.getText().toString());
		final String sdate=fm.format( mystartdate);
		final String edate= fm.format(eenddate);
		final String ddate=fm.format(ddudate);
		// get differences in dates
		long start= mystartdate.getTime();
		long end= eenddate.getTime();	
		
		
		long diff= end-start;
		//avoid silly invoices
		if(end<start)
		{
			// make a toast
			Context context =  ProcessRentalInvoice.this;			
			CharSequence msg ="Invoice End Date can not be before Start Date";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
			
		}
		// does it fall within the tenancy Period?
		long tstartdate=tenant.getStart_date().getTime();
		long tenddate=tenant.getEnd_date().getTime();
		if(start < tstartdate || end >  tenddate)
		{
			// make a toast
						Context context =  ProcessRentalInvoice.this;			
						CharSequence msg ="An Invoice should be within the Tenancy period";			
						int duration =Toast.LENGTH_LONG;
						Toast toast = Toast.makeText(context, msg, duration);
						toast.setGravity(Gravity.CENTER, 0, 0);
						toast.show();
						return;
		}
		
		int days= (int) (diff/(24 * 60 * 60 * 1000));
		//Log.e("Days",Integer.toString(days));
		if(days<28 || days>31)
		{
			String msg=null;
			
			if(days>31)
			{
				msg="The days " + "("+ days + ")"+ " you have selected for the Invoice are more  than 31 days."+
			        " Do you want to proceed?";
				AlertDialog dialog = new AlertDialog.Builder(ProcessRentalInvoice.this).create();
				dialog.setMessage(msg);
				dialog.setIcon(R.drawable.home);			
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// check whether not already posted.
						if (db.InvoiceOverLap(tenancyid, sdate, edate)==false)
						{
						db.AddRentalInvoice(tenancyid, sdate, edate, rental, ddate);
						// remove item from collection 
						Toast.makeText(getApplicationContext(), "record added", Toast.LENGTH_LONG).show();
						rent.setText("0");
						
						}
						else
						{
							// make a toast
							Context context =  ProcessRentalInvoice.this;			
							CharSequence msg ="Overlapping Invoice Dates";			
							int duration =Toast.LENGTH_LONG;
							Toast toast = Toast.makeText(context, msg, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
							
						}
						
					}
				});
				
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return;
						
					}
				});
				dialog.show();
				
						
				
			}else if(days <28)
			{
				msg="The days " + "("+ days + ")"+ " you have selected for the Invoice are less  than 28 days."+
				        " Do you want to proceed?";
				AlertDialog dialog = new AlertDialog.Builder(ProcessRentalInvoice.this).create();
				dialog.setMessage(msg);
				dialog.setIcon(R.drawable.home);			
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						// check whether not already posted.
						if (db.InvoiceOverLap(tenancyid, sdate, edate)==false)
						{
						db.AddRentalInvoice(tenancyid, sdate, edate, rental, ddate);
						// remove item from collection 
						rent.setText("0");
						
						}
						else
						{
							// make a toast
							Context context =  ProcessRentalInvoice.this;			
							CharSequence msg ="Overlapping Invoice Dates";			
							int duration =Toast.LENGTH_LONG;
							Toast toast = Toast.makeText(context, msg, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
							
						}
						
					}
				});
				
				dialog.setButton(DialogInterface.BUTTON_NEGATIVE,"Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						return;
						
					}
				});
				dialog.show();
				
			
		
			}
			
			
		}else
		{
			
			if (db.InvoiceOverLap(tenancyid, sdate, edate)==false)
			{
			db.AddRentalInvoice(tenancyid, sdate, edate, rental, ddate);
			// remove item from collection 
			rent.setText("0");
			
			}
			else
			{
				// make a toast
				Context context =  ProcessRentalInvoice.this;			
				CharSequence msg1 ="Overlapping Invoice Dates. Invoice not created";			
				int duration =Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(context, msg1, duration);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				return;
				
			}
		}
				
		
	
		
		
	}
	public void Cancel(View v)
	{
		this.finish();
	}

}
