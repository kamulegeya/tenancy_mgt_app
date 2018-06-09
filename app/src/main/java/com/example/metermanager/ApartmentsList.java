package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Apartments;
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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;

public class ApartmentsList extends Activity {
	private DatabaseHelperClass db;	
	private List<Apartments> appartments= new ArrayList<Apartments>();
	List <String> s = new ArrayList<String>();
	 String[] menuItems = {"Delete","View Receipts","View Invoices","Edit"};
	ListView list;
	ArrayAdapter<String> arrayAdapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.appartments_list);
		db= DatabaseHelperClass.getInstance(this);
		list= (ListView) findViewById(R.id.apartments);	
		appartments=db.GetApartments();	
		 s = new ArrayList<String>();
			
			for (Apartments l:appartments){
				s.add(l.toString());
			}
		 arrayAdapter = new ArrayAdapter<String>(
	            this, 
	            android.R.layout.simple_list_item_1,s);
	            

	    list.setAdapter(arrayAdapter); 
	    registerForContextMenu(list);
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.apartments) {		  
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
			Apartments a = (Apartments) appartments.get(pos);
			// get id
			int id= a.get_id();
		
		  
		 if(menuItemName.equals("View Receipts"))		 {
		
			// put it in an extral
			// intent to start display activity
			Intent i= new Intent(this, ApartmentPayments.class);
			i.putExtra("ID", id);
			startActivity(i);
		 }if(menuItemName.equals("Delete"))
		 {
			 try{
			 if(db.deleteApartment(id))	
			 {
			 appartments.remove(pos);
			 s.remove(pos);
			 arrayAdapter.notifyDataSetChanged();
				
			 }else
			 {
				 Toast.makeText(this,"Can not delete an apartment/room with a tenancy",Toast.LENGTH_LONG).show();
			 }
			 
			 }catch(Exception e)
			 {
				 e.printStackTrace();
			 }
			 
		 } if(menuItemName.equals("View Invoices"))		 {
				
				// put it in an extral
				// intent to start display activity
				Intent i= new Intent(this, ApartmentInvoices.class);
				i.putExtra("ID", id);
				startActivity(i);
		 }if(menuItemName.equals("Edit"))		 {
				
				// put it in an extral
				// intent to start display activity
			 Apartments A= (Apartments)appartments.get(pos);
				Intent i= new Intent(this, EditApartments.class);
				i.putExtra("Room", A);
				startActivity(i);
		 }
		 
		return super.onContextItemSelected(item);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		appartments=db.GetApartments();	
		 s = new ArrayList<String>();
			
			for (Apartments l:appartments){
				s.add(l.toString());
			}
		 arrayAdapter = new ArrayAdapter<String>(
	            this, 
	            android.R.layout.simple_list_item_1,s);
	            

	    list.setAdapter(arrayAdapter); 
		super.onResume();
	}
}
