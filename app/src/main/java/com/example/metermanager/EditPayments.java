package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.PowerPayments;
import meters.model.VReadings;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class EditPayments extends Activity {
	static final int Date_dialog_licker=0;
	 private  int year,month,day;
	TextView txtviewreadingDate;	
	private DatabaseHelperClass db;
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	ListView listview ;	
	Context context; 
	Date search=null;
	String searchDate;
	EditPaymentsAdapter adapter	;
	 String[] menuItems = {"Edit Record","Delete Record"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db= DatabaseHelperClass.getInstance( this);
		setContentView(R.layout.edit_reading_main);
		txtviewreadingDate =(TextView)findViewById(R.id.textReadingDate);	
		setCurrentDate();
		context = this.getApplicationContext();
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
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
	public void GetDate(View v)

	{
		try{
		showDialog(0);
		} catch(Exception e)
		{
			Log.e("Dialog Err", e.toString());
		}
	}
public void Search(View v)
{
	try{
				
		 search=fm.parse(txtviewreadingDate.getText().toString());
		 searchDate=fm.format(search);
		adapter	= new EditPaymentsAdapter(this ,searchDate);
		ListView listview =(ListView)findViewById(R.id.lstSearchReadings);	
		listview.setAdapter(adapter);		
		registerForContextMenu(listview);
		} catch(Exception e)
		{
			Log.e("Search Error", e.toString());
		}	
	
}



@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
	if (v.getId()==R.id.lstSearchReadings) {		  
	    menu.setHeaderTitle("Choose a menu item");			   
	    for (int i = 0; i<menuItems.length; i++) {
	        menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
}
}

@Override
public boolean onContextItemSelected(MenuItem item) {
	// TODO Auto-generated method stub
	 int menuItemIndex = item.getItemId();
	 final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
	            .getMenuInfo(); 
	  String menuItemName =menuItems[menuItemIndex];
	 if(menuItemName=="Edit Record")
	 {
		// custom dialog
			try{
						final Dialog dialog = new Dialog(EditPayments.this);
						dialog.setContentView(R.layout.editpayments_dialog);
						dialog.setTitle("Edit Meter Readings");	
						int pos=menuInfo.position;						
						PowerPayments r= (PowerPayments)adapter.getItem(pos);						
						final int paymentid= r.getPaymentid();
						final EditText id =(EditText)
								dialog.findViewById(R.id.editText1);
						id.setText(Integer.toString(r.get_id()));
						final EditText paymentDate =(EditText)
								dialog.findViewById(R.id.editText2);
						paymentDate.setText(fm.format(r.getPaymentDate()));
						final EditText amount =(EditText)
								dialog.findViewById(R.id.editText3);
						amount.setText(df.format(r.getAmount()));
						final EditText narration =(EditText)
								dialog.findViewById(R.id.editText4);
						narration.setText(r.getNarration().toString());
						
						Button savebutton = (Button)
								dialog.findViewById(R.id.save_dialog);
						
						savebutton.setOnClickListener(new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								//int myid= Integer.parseInt(id.getText().toString());
								
								String tomatch=paymentDate.getText().toString();
								
								if(db.IsvalidDate(tomatch)==false)
								{
									CharSequence msg ="Enter Dates in format yyyy-mm-dd";			
									int duration =Toast.LENGTH_LONG;
									Toast toast = Toast.makeText(context, msg, duration);
									toast.setGravity(Gravity.CENTER, 0, 0);
									toast.show();
									return;
								}
								Date search = null;
								try {
									search = fm.parse(paymentDate.getText().toString());
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								String newDate=fm.format(search);						
								double amount1 = 0;
								amount1 = Double.parseDouble(amount.getText().toString().replace(",", ""));
								String narration1= narration.getText().toString();
								// TODO Auto-generated method stub
								try{
								db.UpdatePower_Payments( paymentid,newDate,amount1, narration1);
								CharSequence msg ="Edits saved";			
								int duration =Toast.LENGTH_LONG;
								Toast toast = Toast.makeText(context, msg, duration);
								toast.setGravity(Gravity.CENTER, 0, 0);
								toast.show();
								dialog.dismiss();
								}catch(Exception e)
								{
									Log.e("Meter Reading error", e.toString());
								}
							}
						});
						Button cancelbutton = (Button)
								dialog.findViewById(R.id.cancel_dialog);
						cancelbutton.setOnClickListener( new View.OnClickListener() {
							
							@Override
							public void onClick(View v) {
								dialog.dismiss();
								// TODO Auto-generated method stub
								
							}
						});
						dialog.show();
			} catch(Exception e)
			{
				Log.e("Dialog Err", e.toString());
			} 
		 

      }
	 else if(menuItemName.equals("Delete Record"))
	 {
		 final int pos=menuInfo.position;						
		 PowerPayments r= (PowerPayments)adapter.getItem(pos);	
			final int record_id= r.getPaymentid();
		AlertDialog.Builder b= new AlertDialog.Builder(this);
		b.setTitle("Confirm Delete");
		b.setMessage("Are sure you want to delete this record no " + record_id + "?"+ "\n"+
				     "You wont be able to undo this operation");
		b.setNegativeButton("cancel", null);
		b.setIcon(R.drawable.ic_launcher);
		b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				/// get id of record
				
				db.deletePowerPayment(record_id);
				// refresh
				adapter.remove(pos);
				 adapter.notifyDataSetChanged();
				
				
			}
			
		});
		b.show();
			

		 
	 }
	  
	 
	 
	return super.onContextItemSelected(item);
}



	
	

}
