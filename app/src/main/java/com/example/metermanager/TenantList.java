package com.example.metermanager;

import java.text.SimpleDateFormat;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.Tenants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Intents;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ListView;

public class TenantList extends Activity  {
	
	 String[] menuItems = {"Delete","Edit Tenants","Add New Tenant","Make Tenant InActive","Add to Contacts","Call"};	
	private DatabaseHelperClass db;	
	private allTenants tenants;	
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	private ListView listview;
	Context context ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tenants1);	
		db=  DatabaseHelperClass.getInstance( this);	
		tenants= new allTenants(this);
		listview =(ListView)findViewById(R.id.lstalltenants);		
		listview.setAdapter(tenants);		
		registerForContextMenu(listview);
		context = this.getApplicationContext();	
		
		
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
				tenants.getFilter().filter(newText);
				return false;
			}
		});
		return true;
	}
	
		
	
	

	

	
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.lstalltenants) {		  
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
		Intent intent;
		  String menuItemName =menuItems[menuItemIndex];
		 if(menuItemName.equals("Add New Tenant"))
		 {
			  intent = new Intent(this,AddNewTenant.class);
				this.startActivity(intent);
			   	
		 }else if(menuItemName.equals("Edit Tenants")){
			 int pos = menuInfo.position;
				Tenants t=(Tenants)
				tenants.getItem(pos);
			 
			  intent = new Intent(this,EditTenants.class);
			  intent.putExtra("Tenant", t);
				this.startActivity(intent);   		 
	
	
  
	  		 
	}
	else if(menuItemName.equals("Make Tenant InActive")){
		int pos = menuInfo.position;
		Tenants t=(Tenants)
		tenants.getItem(pos);
		
        // check if selected already inactive	

         int tenantid=t.get_id();
         if (db.TenantStatus(tenantid)==true)
         {
        	 context = this.getApplicationContext();			
 			CharSequence msg ="Tenant already  InActive";			
 			int duration =Toast.LENGTH_LONG;
 			Toast toast = Toast.makeText(context, msg, duration);
 			toast.setGravity(Gravity.CENTER, 0, 0);
 			toast.show();
 			return false;
         }
         else
         {
		 intent = new Intent(this,SetInActiveTenant.class);
		 String[] extra= new String[]{Integer.toString(t.get_id()),t.FullName(t.getFirstName(),t.getSurName())};
		 intent.putExtra("test", extra);
			this.startActivity(intent); 

         }
   	  		 
   	}else if(menuItemName.equals("Add to Contacts")){
		
   		int pos = menuInfo.position;
		Tenants t=(Tenants)
		tenants.getItem(pos); 
        String name= t.FullName(t.getFirstName(), t.getOtherNames());
        String nunmber= null;
        if( t.getMobile1().length()==0 )
        {
        	nunmber= t.getMobile2();
        }
        else
        {
        	nunmber=t.getMobile1();
        }
        
        String email= t.getEmail();
        Intent intent1 = new Intent(Intent.ACTION_INSERT);
        intent1.setType(Contacts.CONTENT_TYPE);
        intent1.putExtra(Intents.Insert.NAME, name);
        intent1.putExtra(Intents.Insert.PHONE,nunmber);
        intent1.putExtra(Intents.Insert.EMAIL, email);
        if (intent1.resolveActivity(getPackageManager()) != null) {
            startActivity(intent1);
        }

        	
       

	  		 
	}else if(menuItemName.equals("Delete")){
		
   		int pos = menuInfo.position;
		Tenants t=(Tenants)
		tenants.getItem(pos); 
         int tenantid=t.get_id();
		if(db.deleteTenant(tenantid))
		{
			//removed deleted items
			tenants.remove(pos);
			tenants.notifyDataSetChanged();
			
		}else
		{
			Toast.makeText(getApplicationContext(), "Can not delete a tenant with a tenancy or a meter allocated", Toast.LENGTH_LONG).show();
		}
		
	}else if(menuItemName.equals("Call")){
		
		int pos = menuInfo.position;
		Tenants t=(Tenants)tenants.getItem(pos); 
		
		String number=null;
		if(t.getMobile1()==null)
		{
			number=t.getMobile2();
		}else
		{
			number=t.getMobile1();
		}
		String uri= "tel:"+ number;
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(uri));		                     
        startActivity(callIntent);
		
	}
	
	
		return super.onContextItemSelected(item);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tenants= new allTenants(this);			
		listview.setAdapter(tenants);		
		super.onResume();
	}
	
	
	
}
