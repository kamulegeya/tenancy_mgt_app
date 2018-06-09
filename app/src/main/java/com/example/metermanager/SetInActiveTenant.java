package com.example.metermanager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import meter.manager.helper.DatabaseHelperClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetInActiveTenant extends Activity {
TextView idtext;
@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm1 =new SimpleDateFormat("dd-MM-yyyy");

private DatabaseHelperClass db;
static final int Date_dialog_licker=0;	
private  int year,month,day;
String[] myvalues;
EditText txtviewreadingDate;
private Context context;
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tenant_inactive);
			db=  DatabaseHelperClass.getInstance( this);
			 idtext =(TextView)
					findViewById(R.id.txtID);
			 Intent intent = getIntent();
			 myvalues = intent.getStringArrayExtra("test");
			 //String _id= intent.getStringExtra("test");
			 String name = myvalues[1];
			idtext.setText(name);
			txtviewreadingDate =(EditText)findViewById(R.id.txtInactive_Date);	
			setCurrentDate();
		}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	public void UpDate(View v)
	{
		try{
		final int myid= Integer.parseInt(myvalues[0]);
		EditText dateInActive= (EditText)	
				findViewById(R.id.txtInactive_Date);	
		Date InActiveDate=null;
		try {
			 InActiveDate =fm.parse( dateInActive.getText().toString());
		} catch (ParseException e) {
			CharSequence msg ="Date not entered in wrong format";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
			
		}
		// get last paid invoice date	
		
		Date d=db.getTenantLasInvoiceDate(myid);
		long last = 0;
		if(d!=null)
		{
		
			 last= d.getTime();
			
		}		
	    
	
		// get termination time
		long tt=InActiveDate.getTime();
		//Log.e("termination", Long.toString(tt));
		//Log.e("end date", Long.toString(last));		
		
		// compare
		// if termination time is before that paid invoice date
		if(tt < last)
		{
				String msg="The termination date of "+ fm1.format( InActiveDate) + " is before the last"+
					     " paid invoice end date of " + fm1.format(d)+ "."+"\n"+ 
						 " Change the termination date to proceed";				
	              Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			      return;
			
			
		}
		final String mydate= fm.format(InActiveDate);
		//Log.e("Test", test);
		if(mydate.length()==0)
		{
						
			CharSequence msg ="Date InActive not entered";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		if(db.IsvalidDate(mydate )==false)
		{
			CharSequence msg ="Date must be entered in format yyyy-mm-dd";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}								
		
		 AlertDialog.Builder b= new AlertDialog.Builder(this);
			b.setTitle("Confirm Action");
			String msg="Are sure you want to perform this action?."+"\n"+
				     "Once you click Ok, it can not be  undone";
			b.setMessage(msg
					     );
			b.setNegativeButton("cancel", null);
			b.setIcon(R.drawable.ic_launcher);
			b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					 // check last paid invoice?
					db.SetTenantInActive(myid, mydate );
					
				}
			});
		b.show();
		
		
		
		}catch (Exception e)
		{
			e.printStackTrace();
			Log.e("Err", e.toString());
		}
	}
	
	public void Cancel(View v)
	{
		try {
			this.finish();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
