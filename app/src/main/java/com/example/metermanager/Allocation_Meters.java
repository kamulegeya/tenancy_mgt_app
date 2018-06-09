package com.example.metermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.MeterAllocation;
import meters.model.MeterReading;
import meters.model.TenantList;
import meters.model.VMeterReadings;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class Allocation_Meters extends Activity {
	MeterAllocationAdapter adapter;	
	DatabaseHelperClass db;
	ListView listview;
	 String[] menuItems = {"Delete","Cancel allocation"};
	 @SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.meters_allocation_main);
	adapter= new 	MeterAllocationAdapter(this);
	 listview =(ListView)findViewById(R.id.lst_meter_allocation);			
	listview.setBackgroundColor(color.holo_orange_dark);	
	listview.setAdapter(adapter);
	registerForContextMenu(listview);
	db=  DatabaseHelperClass.getInstance(this);
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	return super.onCreateOptionsMenu(menu);
}
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
	if (v.getId()==R.id.lst_meter_allocation) {		  
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
	 AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
	            .getMenuInfo(); 
	 final int pos= menuInfo.position;
	Intent intent;
	  String menuItemName =menuItems[menuItemIndex];
	 if(menuItemName=="Edit")
	 {
		 MeterAllocation allocation= (MeterAllocation)adapter.getItem(pos);
		 //Log.e("Test Name", allocation.getFullName());
		  intent = new Intent(this,EditMeterAllocation.class);
		  intent.putExtra("MeterAllocation", allocation);
			this.startActivity(intent);
	 }else if(menuItemName=="Delete")
	 {
		 
		 // we grab reading details
		 ///we grab tenant ID
		 /// 
		 AlertDialog.Builder b= new AlertDialog.Builder(this);
			b.setTitle("Confirm Delete Action");
			b.setMessage("Are sure you want to delete this meter allocation?."+"\n"+
					     "doing so will delete all recorded meter readings related to this Tenant"+ "\n"+
					   " Are sure u want to proceed?"					
					);
			b.setNegativeButton("cancel", null);
			b.setIcon(R.drawable.ic_launcher);
			b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
				@Override
		 public void onClick(DialogInterface dialog, int which) {			 	 
		 
		 try{
			 MeterAllocation allocation= (MeterAllocation)adapter.getItem(pos);
			 
			 int tenantmeterid=allocation.get_id();
			 db.DeleteMeterAllocation(tenantmeterid);
			  adapter.Remove(pos);
			  adapter.notifyDataSetChanged();
		 
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
				}
			});
			b.show();		
		 
	 }else if(menuItemName=="Cancel allocation")
	 {
		 
		 try{
				final Dialog dialog = new Dialog(Allocation_Meters.this);
				dialog.setContentView(R.layout.end_allocation_dialog);
				dialog.setTitle("Cancel Meter Allocation");	
				int pos1=menuInfo.position;						
				 MeterAllocation r = (MeterAllocation)adapter.getItem(pos1);						
				
				final EditText id =(EditText)
						dialog.findViewById(R.id.editText1);
				id.setText(Integer.toString(r.get_id()));
				
				
				Button savebutton = (Button)
						dialog.findViewById(R.id.save_dialog);
				
				savebutton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						int myid= Integer.parseInt(id.getText().toString());
						 EditText readingDate =(EditText)
								dialog.findViewById(R.id.editText2);						
						 EditText meterreading =(EditText)
								dialog.findViewById(R.id.editText3);						
						 EditText rate =(EditText)
								dialog.findViewById(R.id.editText4);					
						
						String tomatch=readingDate.getText().toString();
						
						if(db.IsvalidDate(tomatch)==false)
						{
							CharSequence msg ="Enter Dates in format yyyy-mm-dd";			
							int duration =Toast.LENGTH_LONG;
							Toast toast = Toast.makeText(Allocation_Meters.this, msg, duration);
							toast.setGravity(Gravity.CENTER, 0, 0);
							toast.show();
							return;
						}
						Date search = null;
						try {
							search = fm.parse(readingDate.getText().toString());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try{
						String newDate=fm.format(search);						
						double myreading =Double.parseDouble(meterreading.getText().toString().replace(",", ""));
						double myrate= Double.parseDouble(rate.getText().toString().replace(",", ""));						
						
							// end meter allocation
						//db.updateMeterReading(myid, newDate, myreading, myrate);
						db.endMeterAllocation(myid, newDate);
						// add a meter reading
						db.AddMeterReading( new MeterReading(myid,  search, myreading, myrate));
						CharSequence msg ="Edits saved";			
						int duration =Toast.LENGTH_LONG;
						Toast toast = Toast.makeText(Allocation_Meters.this, msg, duration);
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
		 
		 
	 return super.onContextItemSelected(item);
}

}
