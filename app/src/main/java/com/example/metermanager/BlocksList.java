package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentBlocks;
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

public class BlocksList extends Activity {
	private DatabaseHelperClass db;	
	private List<ApartmentBlocks> locations= new ArrayList<ApartmentBlocks>();
	 String[] menuItems = {"Delete","View Receipts","Edit"};
	 List <String> s = new ArrayList<String>();
	 ArrayAdapter<String> arrayAdapter ;
	ListView list;
@Override

protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.appartment_locations);
	db= DatabaseHelperClass.getInstance(this);
	list= (ListView) findViewById(R.id.apartments_locations);	
	locations=db.GetApartmentblocks();
	s = new ArrayList<String>();
	
	for (ApartmentBlocks l:locations){
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
	 final int pos = menuInfo.position;
	  String menuItemName =menuItems[menuItemIndex];
	 if(menuItemName.equals("Delete"))
	 {
		 try{
		 ApartmentBlocks l= (ApartmentBlocks)locations.get(pos);
		 int id= l.get_id();
		 if(db.deleteBlock(id)==true)
		 {
			 
		 locations.remove(pos);
		 s.remove(pos);
		 arrayAdapter.notifyDataSetChanged();
		 }else
		 {
			 Toast.makeText(this,"Can not delete a block with apartments entered",Toast.LENGTH_LONG).show(); 
		 }
		 }catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }else if((menuItemName.equals("View Receipts")))
	 {
		 
		 // get an extral to pass to the intent
		 ApartmentBlocks l= (ApartmentBlocks)locations.get(pos);
		 int id= l.get_id();
		 int[]myid= {id,0};
		 Intent intent = new Intent(this,LocationSales.class);
		 intent.putExtra("BLOCKID", myid);
		 startActivity(intent);
		 
		 
	 }if(menuItemName.equals("Edit"))
	 {
		 ApartmentBlocks bb= ( ApartmentBlocks)locations.get(pos);
		 Intent i= new Intent(getApplicationContext(), EditApartmentBlocks.class);
		 i.putExtra("Block", bb);
		 startActivity(i);
		 
	 }
	return super.onContextItemSelected(item);
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	locations=db.GetApartmentblocks();
	s = new ArrayList<String>();
	
	for (ApartmentBlocks l:locations){
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
