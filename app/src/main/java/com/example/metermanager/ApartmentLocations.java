package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Locations;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class ApartmentLocations extends Activity {
	private DatabaseHelperClass db;	
	private List<Locations> locations= new ArrayList<Locations>();
	List <String> s = new ArrayList<String>();

	 String[] menuItems = {"Delete","View Receipts","Edit"};
	 ArrayAdapter<String> arrayAdapter ;
	ListView list;
@Override

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.appartment_locations);
	db= DatabaseHelperClass.getInstance(this);
	list= (ListView) findViewById(R.id.apartments_locations);	
	locations=db.GetLocations();
	 s = new ArrayList<String>();
	
	for (Locations l:locations){
		s.add(l.toString());
	}
	 arrayAdapter = new ArrayAdapter<String>(
            this, 
            android.R.layout.simple_list_item_1,
            s );

    list.setAdapter(arrayAdapter); 
    registerForContextMenu(list);
}
@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
	if (v.getId()==R.id.apartments_locations) {		  
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
	 //final int pos = menuInfo.position;
	 final String menuItemName =menuItems[menuItemIndex];
	 if(menuItemName.equals("Delete"))
	 {
		 try{
		 int pos = menuInfo.position;
		 Locations l= (Locations)locations.get(pos);
		 int id= l.get_id();
		if( db.deleteLocation(id))
			{
			locations.remove(pos);
			s.remove(pos);
			arrayAdapter.notifyDataSetChanged();
			
			
			}else
				
				
		   {
				
			Toast.makeText(this,"Can not delet a location  with apartment blocks",Toast.LENGTH_LONG).show();
			
		}
		
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }else if((menuItemName.equals("View Receipts")))
	 {
		 
		 // get an extral to pass to the intent
		 int pos1 = menuInfo.position;
		 Locations l= (Locations)locations.get(pos1);
		 int id= l.get_id();
		 Intent intent = new Intent(this,LocationSales.class);
		 intent.putExtra("ID", id);
		 startActivity(intent);
		 
		 
	 }else if((menuItemName.equals("Edit")))
	 {
		 
		 // get an extral to pass to the intent
		 int pos1 = menuInfo.position;
		 Locations l= (Locations)locations.get(pos1);
		 
		 Intent intent = new Intent(this,EditLocations.class);
		 intent.putExtra("L", l);
		 startActivity(intent);
		 
		 
	 }
	 
	 
	return super.onContextItemSelected(item);
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	locations=db.GetLocations();
	 s = new ArrayList<String>();
	
	for (Locations l:locations){
		s.add(l.toString());
	}
	 arrayAdapter = new ArrayAdapter<String>(
           this, 
           android.R.layout.simple_list_item_1,
           s );

   list.setAdapter(arrayAdapter); 
	super.onResume();
}
}
