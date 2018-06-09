package com.example.metermanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.metermanager.NumberTextWatcher;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.PowerPayments;
import meters.model.TenantList;
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

public class Payments extends Activity {
	static final int Date_dialog_licker=0;	
	private DatabaseHelperClass db;
	private List<TenantList> readings =new ArrayList<TenantList>();	
	Spinner spinner;	
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	 private  int year,month,day;
	 TextView txtviewreadingDate;
     EditText amountText;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	 setContentView(R.layout.payments);
	 db=  DatabaseHelperClass.getInstance( this);
	
		try{
			readings=db.GetTenantIDs();
			 // create an array list of 
			 List<String> tenantlist= new  ArrayList<String>();
			 
			 for( TenantList m:readings)
			 {
				 tenantlist.add(m.getFullName());
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.spPayments);
			 // add event listening		 
			 
		     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);  		
      txtviewreadingDate =(TextView)findViewById(R.id.paymentDate);	
     amountText= (EditText)findViewById(R.id.editText2);
     amountText.addTextChangedListener(new NumberTextWatcher(amountText));
     amountText.setText("0");
      setCurrentDate();
		}catch (Exception e)
		{
			Log.e("err" ,e.toString());
		}

}
		
public void AddNew(View view)
{
	try{
		TextView paymentDate= (TextView)findViewById(R.id.paymentDate);					
		
		double amount = Double.parseDouble(amountText.getText().toString().replace(",", ""));		
		EditText narrationText= (EditText)findViewById(R.id.editText3);	
		String narration =narrationText.getText().toString();
		
		if(Double.toString(amount).length()==0 || narration.length()==0)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Amount and Narration must be filled";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		int pos = spinner.getSelectedItemPosition();
		if(pos==-1)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="You can not receive payments when tenant data not entered";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		TenantList tenant=(TenantList)readings.get(pos);
		int id = tenant.get_id();;
		//Log.e("TestId", Integer.toString(id));
		// check reading for that day
		Date mdate= new Date();		
		Date test= fm.parse( paymentDate.getText().toString());
		if(mdate.before(test)==true)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Payment Date can not be in the future";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
			
		
		//Log.e("Testing Date String", fm.format(test));
		if (db.PaymentMade(id,fm.format(test))== true)
		{
			Context context = this.getApplicationContext();			
			CharSequence msg ="Payment  for this Date and  Tenant already received";			
			int duration =Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, msg, duration);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			return;
		}
		String paymentdate=fm.format(test);
		db.AddPowerPayments(id,paymentdate,amount,narration);	
		//clear entry boxes	  		
		amountText.setText("0")	;
		narrationText.getText().clear();
		Context context = this.getApplicationContext();			
		CharSequence msg ="Record added";			
		int duration =Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, msg, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		
		} catch(Exception e)
		{
			// Log.e("Here", e.toString());
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







public  void CancelAdd(View v)
{
	this.finish();
}


}
