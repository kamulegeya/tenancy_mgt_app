package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.UnPaidInvoices;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class OverDueInvoices extends Activity {
	private DatabaseHelperClass db;
	private OverDueInvoiceListAdapter  invoicelist ;
	SimpleDateFormat fm = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.getDefault());	
	
	SimpleDateFormat fm1 = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());	
	ListView list;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	String[] menuItems= {"SMS Invoice","Delete Invoice"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices);
		db = DatabaseHelperClass.getInstance(this);
		Date d= new Date();
		String mydate=fm1.format(d);
		invoicelist= new OverDueInvoiceListAdapter (this,mydate);	
		
		list= (ListView) findViewById(R.id.invoices_list);			
			    list.setAdapter(invoicelist); 
		
		registerForContextMenu(list);
	
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
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		int menuItemIndex = item.getItemId();
		 String menuItemName =menuItems[menuItemIndex];
		 final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
		            .getMenuInfo(); 
		 final int pos = menuInfo.position;
 if(menuItemName.equals("SMS Invoice"))
			 
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
				msg=  "Hello "+ fName + "," +" the rent of UGX " + rent+ 
				      " for the period:"+ period + " is due for payment.";
				//Log.e("sms",msg);
					  
			//Utilities u= new Utilities();
			// u.SendSms(mobile, msg);
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
				b.setTitle("Confirm Delete Action");
				b.setMessage("Are sure you want to delete this Invoice?."+"\n"+
						     "You waont be able to undo this action.");
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
			 
			 
		 }
		return super.onMenuItemSelected(featureId, item);
	}
	
}
