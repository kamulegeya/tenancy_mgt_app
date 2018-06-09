package com.example.metermanager;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.example.metermanager.CreateReceiptTask.OnTaskFinishedListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.HtmlFile;
import meters.model.RentalPayments;
public class RentalPaymentsList extends   Activity implements OnItemSelectedListener , OnTaskFinishedListener {
ListView list;
Spinner spinner;
RentalPaymentsAdapter rentalpayments;
DecimalFormat df = new DecimalFormat("#,###,###,###");
@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
String[] menuItems= {"SMS Receipt","Delete Payment","Email Receipt"};
// choices for filtering payments
String[] periods= {"All","This month","Two Months","Three Months","Six Months","Year"};
UserSettingsClass u;
AdapterContextMenuInfo menuInfo ;
private DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rental_payments_list);
		u= new UserSettingsClass(getApplicationContext());
		GregorianCalendar c1= new GregorianCalendar();
        db=  DatabaseHelperClass.getInstance(this);
		 c1.add(Calendar.DATE,-30);
		 Date myStartDate =c1.getTime();
		
		String s = fm.format(myStartDate);
		
		//Log.e("sdate", s);
		
		rentalpayments= new RentalPaymentsAdapter(this,s);
		 //Creating adapter for spinner
		 spinner = (Spinner) findViewById(R.id.payments);		 	 
		 
	     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,periods);
	     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter);  
	list =(ListView)
			findViewById(R.id.rental_payments);
	list.setAdapter(rentalpayments);
	registerForContextMenu(list);
	spinner.setOnItemSelectedListener(this);
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		GregorianCalendar c1= new GregorianCalendar();

		if(periods[pos].equals("This month"))
		{
			// get data within one month
			c1.add(Calendar.DATE,-30);
			 Date myStartDate =c1.getTime();
			
			String s = fm.format(myStartDate);
			
			Log.e("sdate", s);
			
			rentalpayments= new RentalPaymentsAdapter(this,s);
			list.setAdapter(rentalpayments);
			
		}else if(periods[pos].equals("Two Months"))
		{
			// get data within one month
						c1.add(Calendar.DATE,-60);
						 Date myStartDate =c1.getTime();						
						String s = fm.format(myStartDate);						
						Log.e("sdate", s);
						
						rentalpayments= new RentalPaymentsAdapter(this,s);
						list.setAdapter(rentalpayments);
			
		}else if(periods[pos].equals("Three Months"))
		{
			// get data within one month
						c1.add(Calendar.DATE,-90);
						 Date myStartDate =c1.getTime();
						
						String s = fm.format(myStartDate);
						
						Log.e("sdate", s);
						
						rentalpayments= new RentalPaymentsAdapter(this,s);
						list.setAdapter(rentalpayments);
			
		}
		
		else if(periods[pos].equals("Six Months"))
		{
			// get data within one month
						c1.add(Calendar.DATE,-180);
						 Date myStartDate =c1.getTime();
						
						String s = fm.format(myStartDate);
						
						Log.e("sdate", s);
						
						rentalpayments= new RentalPaymentsAdapter(this,s);
						list.setAdapter(rentalpayments);
			
		}
		else if(periods[pos].equals("Year"))
		{
			// get data within one month
			          
						c1.add(GregorianCalendar.DATE,-365);
						 Date myStartDate =c1.getTime();
						
						String s = fm.format(myStartDate);						
					
						rentalpayments= new RentalPaymentsAdapter(this,s);
						list.setAdapter(rentalpayments);
			
		}else if(periods[pos].equals("All"))
		{
			
						rentalpayments= new RentalPaymentsAdapter(this);
						list.setAdapter(rentalpayments);
			
		}
		
	}
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		SearchView v=(SearchView) menu.findItem(R.id.mysearch).getActionView();
		v.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				 rentalpayments.getFilter().filter(newText);
				return false;
			}
		});
		return true;
		
		}
	
	@Override
		public void onCreateContextMenu(ContextMenu menu, View v,
				ContextMenuInfo menuInfo) {
			// TODO Auto-generated method stub
		if (v.getId()==R.id.rental_payments) {		  
		    menu.setHeaderTitle("Choose a menu item");			   
		    for (int i = 0; i<menuItems.length; i++) {
		        menu.add(Menu.NONE, i, i, menuItems[i]);
		    
		    }
		}	    
		    
		
			super.onCreateContextMenu(menu, v, menuInfo);
		}
	@Override
		public boolean onContextItemSelected(MenuItem item) {
			// TODO Auto-generated method stub
		int menuItemIndex = item.getItemId();
		 String menuItemName =menuItems[menuItemIndex];
		 menuInfo = (AdapterContextMenuInfo) item 
		            .getMenuInfo(); 
		 final int pos = menuInfo.position;
		 
		 if(menuItemName.equals("Delete Payment"))
		 {
			 RentalPayments payment=(RentalPayments)rentalpayments.getItem(pos);
			int paymentid= payment.getPayment_id();
			db.deleteRentalPayment(paymentid);
			rentalpayments.Remove(pos);
			rentalpayments.notifyDataSetChanged();
			 
		 }else if(menuItemName.equals("SMS Receipt"))
			 
		 {
			 

			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm Sending Sms");
				b.setMessage("Are sure you want to send an sms of this invoice?."+"\n"+
						     "Applicable Service Provider SMS Charges applies.");
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
					@Override
			 public void onClick(DialogInterface dialog, int which) {			 	 
			 
			 try{
			
				// get the ID
				//int id =t.get_id();
					RentalPayments p= (RentalPayments)	rentalpayments.getItem(pos)	;	
				 String mobile = null;
				
				if(Integer.parseInt(p.getMobile())==0)
				{
					mobile= p.getMobile2().trim();
					Log.e("mobil",mobile);
				}				
				
				else
				{
					mobile= p.getMobile().trim();
					Log.e("mobil",mobile);
				}
				
				
				String fName= p.getFullName();						
								
				String rent= df.format(p.getAmount());
                String period=p.getPeriod();
				
				String msg ;
				
				msg=  "Dear "+ fName + "," +" this is to acknowledge receipt of " + u.currency() + " " + rent+ 
				      " being rent  for the period:"+ period + ".";
				Log.e("msg", msg);  
			//Utilities u= new Utilities();
			 //u.SendSms(mobile, msg);
				String sms= msg;
				Intent foward= new Intent(Intent.ACTION_VIEW);
				foward.setData(Uri.parse("smsto"));
				foward.putExtra("sms_body", sms);
				foward.setType("vnd.android-dir/mms-sms");
				foward.putExtra("address",mobile); 
				startActivity(foward);
				
			 }catch(Exception e)
			 {
				 e.printStackTrace();
			 }
					}
				});
				b.show();		
			 
			 
			 
		 }else if(menuItemName.equals("Email Receipt"))
		 {
			 
			 RentalPayments p= (RentalPayments)	rentalpayments.getItem(pos);					
				String fName= p.getFullName();				
							
				String rent= df.format(p.getAmount());
				int a= (int)(p.getAmount());
				NumberToWord  n= new NumberToWord();
				String s= n.convert(a);
				
		     String period=p.getPeriod();		
				String msg ;				
				msg=  "Dear "+ fName + "," +" this is to acknowledge receipt of " + u.currency() + " " + rent+ " "+
						"(in words "+ n.toTitleCase(s) + ")"+
				      " being rent  for the period:"+ period + ".";
				// new CreateHtmlTask(getCacheDir(), this,RentalPaymentsList.this).execute(msg);
				new CreateReceiptTask(getCacheDir(), this,RentalPaymentsList.this).execute(msg);
			 
		 }
		
			return super.onContextItemSelected(item);
		}
	@Override
	public void onHtmlCreated(HtmlFile html) {
		// TODO Auto-generated method stub		
		startSendEmailIntent(html.getFilePath());
		
		
	}
	private void startSendEmailIntent(Uri attachmentUri) {
        // Create a new intent - we are 'sending' data
		  int pos = menuInfo.position;		
		  RentalPayments p= (RentalPayments)	rentalpayments.getItem(pos);
		  String email=p.getEmail();
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Mime type of html - so we can add some funky html tags in the email <b> etc </b>     
               
        intent.setType("text/html");
        // The subject of your email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Receipt");
    	
		  String test = " <h1> <b> Receipt </b></h1>" + 
				         "<p> Find your receipt  as attached </p>";
       	  
	  // The uri to the attachment that is the real guts of our email        
        intent.putExtra(Intent.EXTRA_STREAM, attachmentUri);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        // The email message
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(test));
        // Let the user select which application to send the email with, we have added a title
        // to give a hint that they should pick an email client
        Intent chooser = Intent.createChooser(intent, "Send Email");
        startActivity(chooser);
    }

}
