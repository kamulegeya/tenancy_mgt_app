package com.example.metermanager;

import java.text.SimpleDateFormat;

import meters.model.VReadings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class MeterReadings extends Activity {
private MeterReadingsAdapter mr;
ListView list;
@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
String[] menuItems= {"View Details"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices);
		list=(ListView)
				findViewById(R.id.invoices_list);
		mr= new MeterReadingsAdapter(this);
		list.setAdapter(mr);
		registerForContextMenu(list);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
	if (v.getId()==R.id.invoices_list) 
	{		  
	    menu.setHeaderTitle("Choose a menu item");			   
	    for (int i = 0; i<menuItems.length; i++) {
	        menu.add(Menu.NONE, i, i, menuItems[i]);
	    }
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	}
@SuppressLint("UseValueOf")
@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
	int menuItemIndex = item.getItemId();
	 String menuItemName =menuItems[menuItemIndex];
	 final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
	           .getMenuInfo(); 
	final int pos = menuInfo.position;
	
	 if(menuItemName.equals("View Details"))
	 {
		 VReadings s= (VReadings)mr.getItem(pos);
		 // get the date
		 Character c= new Character((char) 39);
		 String date= c+ fm.format(s.getReadingDate())+ c;
		 Intent i= new Intent(this, DateReading.class);
		 Log.e("date", date);
		 i.putExtra("Date", date);
		 startActivity(i);
		 
		 
	 }
		return super.onMenuItemSelected(featureId, item);
	}
	
}
