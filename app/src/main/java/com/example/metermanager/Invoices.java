package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.example.metermanager.CreateInvoiceTask.OnTaskFinishedListener;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.HtmlFile;
import meters.model.UnPaidInvoices;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;

public class Invoices extends Activity implements  OnTaskFinishedListener{
	private DatabaseHelperClass db;
	private InvoiceListAdapter  invoicelist ;
	SimpleDateFormat fm = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault());	
	
	SimpleDateFormat fm1 = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
	ListView list;
	TextView textv;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	NumberFormat f= NumberFormat.getCurrencyInstance();
	String[] menuItems= {"SMS Remainder","Delete Invoice","Edit Invoice","Email Invoice"};
	  AdapterContextMenuInfo menuInfo;
    //static SharedPreferences sharedpreferences;
	UserSettingsClass u;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices2);
		textv=(TextView)findViewById(R.id.txtTotalUnpaid);
		u= new UserSettingsClass(getApplicationContext());
		db = DatabaseHelperClass.getInstance(this);
		invoicelist= new InvoiceListAdapter (this);	
		
		list= (ListView) findViewById(R.id.invoices_list);			
			    list.setAdapter(invoicelist); 
		
		registerForContextMenu(list);
	   textv.setText(df.format(db.totalUnpaidInvoices()));
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.invoices_list) {		  
		    menu.setHeaderTitle("Choose a menu item");			   
		    for (int i = 0; i<menuItems.length; i++) {
		        menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
          invoicelist= new InvoiceListAdapter (this);
						
			    list.setAdapter(invoicelist);
		super.onResume();
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		int menuItemIndex = item.getItemId();
		 String menuItemName =menuItems[menuItemIndex];
		 menuInfo = (AdapterContextMenuInfo) item 
		            .getMenuInfo(); 
		 final int pos = menuInfo.position;
 if(menuItemName.equals("SMS Remainder"))
			 
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
					UnPaidInvoices p= (UnPaidInvoices)	invoicelist.getItem(pos)	;	
				 String mobile = null;
				
				if(Integer.parseInt(p.getMobile())==0)
				{
					mobile= p.getMobile2().trim();
				}				
				
				else
				{
					mobile= p.getMobile().trim();
				}
				
				
				String fName= p.getFullName();
							
				
				String sDate= fm.format(p.getStart_date());
				String eDate = fm.format(p.getEnd_date());
				//String dDate =fm.format(p.getDatedue());
				String rent=df.format(p.getMonthly_rental());
				String period = sDate  + " to  "+ eDate;
				String msg ;
				msg=  "Hello, "+ fName + "," +" the rent of " + u.currency() + " " + rent+ 
				      " for the period:"+ period + " "+"for room/apartment"+ " "+ p.getApartment() + " "+
						"is due for payment";
				
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
			 
		 } if(menuItemName.equals("Delete Invoice"))
		 {
			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm delete action");
				b.setMessage("Are sure you want to delete this Invoice?."+"\n"+
						     "You wont be able to undo this action.");
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
					@Override
			 public void onClick(DialogInterface dialog, int which) {			 	 
			 
			 try{
              UnPaidInvoices p= (UnPaidInvoices)	invoicelist.getItem(pos);
			 int invoiceid= p.getInvoice_id();
			 db.deleteRentalInvoice(invoiceid);
			 invoicelist.Remove(pos);
			 invoicelist.notifyDataSetChanged();			
				
			 }catch(Exception e)
			 {
				 e.printStackTrace();
			 }
					}
				});
				b.show();	
			 
			 
		 } if(menuItemName.equals("Edit Invoice"))
		 {
			    UnPaidInvoices p= (UnPaidInvoices)invoicelist.getItem(pos);
			    // pack individual data fields into a string array....
			    // why??? serializing failed.
			    String tenancyid=Integer.toString(p.getTenancy_id());
			    String invoiceid=Integer.toString(p.getInvoice_id());
			    String sdate=fm1.format(p.getStart_date());
			    String edate=fm1.format(p.getEnd_date());
			    String dd= fm1.format(p.getDatedue());
			    String rental= Double.toString(p.getMonthly_rental());
			    String fname= p.getFullName();
			    String room=p.getApartment();
			    String[] myextral= new String[]{tenancyid,invoiceid, sdate,edate,dd,rental,fname,room};
			    Intent i = new Intent(this,EditInvoices.class);
			    i.putExtra("invoice", myextral);	
			 
			    startActivity(i);
			    
			    
		 }if(menuItemName.equals("Email Invoice"))
		 {
			    UnPaidInvoices p= (UnPaidInvoices)	invoicelist.getItem(pos);
			    String fName= p.getFullName();					
				String sDate= fm.format(p.getStart_date());
				String eDate = fm.format(p.getEnd_date());
				String dDate =fm.format(p.getDatedue());
				String rent=df.format(p.getMonthly_rental());
				String period = sDate  + " to  "+ eDate;
				int a= (int)(p.getMonthly_rental());
				NumberToWord  n= new NumberToWord();				
				String s= n.convert(a);
				//here our message we want printed on pdf!
				String msg ;
				msg=  "Dear "+ fName + "," +" this is to inform you that the rent of " + u.currency() + " " + rent+ "(in words "+ n.toTitleCase(s) + ")"+	
				       "for room/appartment" + " "+p.getApartment() +" "+ "located at" + " " + p.getLocation() +
				      " for the period:"+ period + " is due for payment on" + " " + dDate + " ." + "\r\n"+
						"Please make arrangement to clear the amount due.";
				new CreateInvoiceTask(getCacheDir(), this,Invoices.this).execute(msg);
			   
			    
		 }
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onHtmlCreated(HtmlFile html) {
		// TODO Auto-generated method stub
		startSendEmailIntent(html.getFilePath());
		
	}
	
	private void startSendEmailIntent(Uri attachmentUri) {
        // Create a new intent - we are 'sending' data
		  int pos = menuInfo.position;
		  UnPaidInvoices p= (UnPaidInvoices)	invoicelist.getItem(pos);
		  String email=p.getEmail();
        Intent intent = new Intent(Intent.ACTION_SEND);
        // Mime type of html - so we can add some funky html tags in the email <b> etc </b>     
               
        intent.setType("text/html");
        // The subject of your email
        intent.putExtra(Intent.EXTRA_SUBJECT, "Invoice");
    	
		  String test = " <h1> <b> Invoice </b></h1>" + 
				         "<p> Find your Invoice as attached </p>";
       	  
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
	
	
	
}
