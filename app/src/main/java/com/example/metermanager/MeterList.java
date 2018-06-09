package com.example.metermanager;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Meters;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;
import android.widget.ListView;

public class MeterList extends Activity   {
	ListView listview;
	MetersListAdapter meters;
	private DatabaseHelperClass db;
	 String[] menuItems = {"Make InActive","Edit","Add New Meter","Allocate Meters",
			 "Read Meters","Edit Meter Reading","View Meter Allocations","Delete Meter"};
		@Override	
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db= DatabaseHelperClass.getInstance(this);
		setContentView(R.layout.meters_main);			
		meters= new MetersListAdapter(this);
		listview =(ListView)findViewById(R.id.meters_list);		
		listview.setAdapter(meters);
		registerForContextMenu(listview);
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.meters_list) {		  
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
		 final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo(); 
		 final int pos = menuInfo.position;
		
		Intent intent;
		  String menuItemName =menuItems[menuItemIndex];
		 if(menuItemName.equals("Read Meters"))
		 {
			  intent = new Intent(this,ReadMeter.class);
				this.startActivity(intent);
			   	
		 }else if(menuItemName=="Edit Meter Reading"){
			 
			  intent = new Intent(this,EditMeterReading.class);
				this.startActivity(intent); 
	   
		 
		 
	}else if(menuItemName.equals("Add New Meter")){
		 
		  intent = new Intent(this,AddNewMeter.class);
			this.startActivity(intent); 
 
	}else if(menuItemName.equals("Make InActive")){
		 
		 Meters m= (Meters)meters.getItem(pos);
		  intent = new Intent(this,MetersInActive.class);
		  intent.putExtra("meter", m);
			this.startActivity(intent); 

		 
	}else if(menuItemName.equals("Allocate Meters")){
		 
		  intent = new Intent(this,MetersTenants.class);
			this.startActivity(intent); 

	}else if(menuItemName.equals("Edit")){
		 Meters m= (Meters)meters.getItem(pos);
		  intent = new Intent(this,EditMeters.class);
		  intent.putExtra("meter", m);
			this.startActivity(intent); 

			
			
	}else if(menuItemName.equals("View Meter Allocations")){
		 
		  intent = new Intent(this,Allocation_Meters.class);
			this.startActivity(intent); 	  
	}else if(menuItemName.equals("Delete Meter")){		
		 Meters m= (Meters)meters.getItem(pos);
		 int id= m.get_id();
		 if(db.deleteMeter(id))
				 {
			      // remove item from the list
			      meters.removeItem(pos);
			      meters.notifyDataSetChanged();
				 }else
				 {
					 // not removed tell user
					 Toast.makeText(getApplicationContext(), "can not delete allocated meter", Toast.LENGTH_LONG).show();
				 }
		  

	 }   
		 
		return super.onContextItemSelected(item);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		meters= new MetersListAdapter(this);			
		listview.setAdapter(meters);
		super.onResume();
	}
}
