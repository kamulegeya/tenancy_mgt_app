package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.example.metermanager.CreateReceiptTask.OnTaskFinishedListener;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.HtmlFile;
import meters.model.UnPaidInvoices;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class RentalPayments extends Activity  implements OnTaskFinishedListener{
	static final int Date_dialog_licker=0;	
	private DatabaseHelperClass db;
	private List<UnPaidInvoices> readings =new ArrayList<UnPaidInvoices>();	
	Spinner spinner;	
	 ArrayAdapter<String> dataAdapter ;
	/// for data display
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy");
	// for data insertions....
	SimpleDateFormat fm1 =new SimpleDateFormat("yyyy-MM-dd");
	 List<String> tenantlist= new  ArrayList<String>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	 private  int year,month,day;
	 TextView txtviewreadingDate;
     EditText amountText;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	 setContentView(R.layout.add_rental_payments);
	 db=  DatabaseHelperClass.getInstance( this);
	
		try{
			readings=db.GetUnPaidInvoices();
			 // create an array list of 
			
			 
			 for( UnPaidInvoices m:readings)
				 
			 {
				 String name="";
				 if(m.getFirstName()==null)
				 {
					 name=m.getSurname();
					 
				 }else
				 {
					 name=m.getFirstName();
				 }
				 String dropdown= name + ":"+ fm1.format( m.getStart_date())+ "_"+ fm1.format(m.getEnd_date());
				 tenantlist.add(dropdown);
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.invoice_id);
			 // add event listening		 
			 
		      dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter); 
		txtviewreadingDate =(TextView)findViewById(R.id.payment_date);	
	     amountText= (EditText)findViewById(R.id.editTextAmount);
	     amountText.addTextChangedListener(new NumberTextWatcher(amountText));
	     amountText.setText("0");
	      setCurrentDate();
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				 UnPaidInvoices m=(UnPaidInvoices)readings.get(arg2);
				 amountText.setText(df.format(m.getMonthly_rental()));
				 
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
      
		}catch (Exception e)
		{
			Log.e("err" ,e.toString());
		}

}
		
public void Add(View view)
{
	try{
		TextView paymentDate= (TextView)findViewById(R.id.payment_date);					
		
		double amount = Double.parseDouble(amountText.getText().toString().replace(",", ""));		
		EditText narrationText= (EditText)findViewById(R.id.editNarration);	
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
			Toast.makeText(getApplicationContext(), "There are no un-paid invoices to receive payments ", Toast.LENGTH_LONG).show();
			return;
		}
		UnPaidInvoices  tenant=(UnPaidInvoices )readings.get(pos);
				
		int id = tenant.getInvoice_id();
		//Log.e("TestId", Integer.toString(id));
		// check reading for that day
		Date mdate= new Date();		
		Date test= fm1.parse( paymentDate.getText().toString());
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
		
		
		String paymentdate=fm1.format(test);
		db.AddRentPayment(id,paymentdate,amount,narration);	
		//clear entry boxes	  
		amountText.setText("0")	;
		narrationText.getText().clear();
		Toast.makeText(getApplicationContext(), "Record added", Toast.LENGTH_LONG).show();
		// send notification sms?
		// get tenant name
		String sms=null;
		String fullname= tenant.getFullName();
		String mobile=null;
		// check mobile number
		if(Integer.parseInt(tenant.getMobile())==0)
		{
			mobile= tenant.getMobile2().trim();
		}	
		
		else
		{
			mobile= tenant.getMobile().trim();
		}
		// make da sms
		sms= "Dear "+ fullname + " this is to acknowledge  receipt of  UGX"+ df.format(amount) + "\n"+
		     "being rent for the period from "+ fm.format( tenant.getStart_date()) + " to " + fm.format(tenant.getEnd_date());
		//Log.e("sms", sms);
		// get email..
	  
	   
	   RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sendmsg);
	   int buttonid = radioGroup.getCheckedRadioButtonId();
		//send sms button
		if(buttonid==R.id.smsSend)
		{
			//Utilities utility = new Utilities();
			
		//utility.SendSms(mobile, sms);
			String sms1= sms;
			Intent foward= new Intent(Intent.ACTION_VIEW);
			foward.setData(Uri.parse("smsto"));
			foward.putExtra("sms_body", sms1);
			foward.setType("vnd.android-dir/mms-sms");
			foward.putExtra("address",mobile); 
			startActivity(foward);
		
		}
	
		if(buttonid==R.id.emailSend) 
		{
		  
		   new CreateReceiptTask(getCacheDir(), this,RentalPayments.this).execute(sms);
		   // i.putExtra("invoice", p);
		   
		}
		
		/// clear 
		tenantlist.remove(pos);
		 dataAdapter.notifyDataSetChanged();
		//readings.remove(pos);
		
		} catch(Exception e)
		{
			
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







public  void Cancel(View v)
{
	this.finish();
}

private void startSendEmailIntent(Uri attachmentUri) {
    // Create a new intent - we are 'sending' data
	int pos = spinner.getSelectedItemPosition();
	UnPaidInvoices  tenant=(UnPaidInvoices )readings.get(pos);
	String email=tenant.getEmail();
    Intent intent = new Intent(Intent.ACTION_SEND);
    // Mime type of html - so we can add some funky html tags in the email <b> etc </b>     
           
    intent.setType("text/html");
    // The subject of your email
    intent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
	
	  String test = " <h1> <b> Receipt </b></h1>" + 
			         "<p> Find your receipt  as attached </p>";
   	  
  // The uri to the attachment that is the real guts of our email        
    intent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
    intent.putExtra(Intent.EXTRA_EMAIL,email);
    // The email message
    intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(test));
    // Let the user select which application to send the email with, we have added a title
    // to give a hint that they should pick an email client
    Intent chooser = Intent.createChooser(intent, "Send Email");
    startActivity(chooser);
}

@Override
public void onHtmlCreated(HtmlFile html) {
	// TODO Auto-generated method stub
	startSendEmailIntent(html.getFilePath());
	
}

}
