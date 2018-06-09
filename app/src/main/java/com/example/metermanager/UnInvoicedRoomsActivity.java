package com.example.metermanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meters.model.ActiveTenancy;
import meters.model.Tenants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class UnInvoicedRoomsActivity extends Activity {
private ListView list;
@SuppressLint("SimpleDateFormat")
String[] menuItems = {"Make Invoices"};	

private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
UnInvoicedListAdapter p;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices);
		
		list=(ListView)
				findViewById(R.id.invoices_list);
		Date d= new Date();
		//Character c = new Character((char) 39);
		String mydate=  fm.format(d);
		p= new UnInvoicedListAdapter(getApplicationContext(), mydate);
		list.setAdapter(p);
		registerForContextMenu(list);
		
		
	}
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.invoices_list) {		  
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
		  List<ActiveTenancy> uninvoiced =new ArrayList<ActiveTenancy>();
		  uninvoiced= p.getList();
		Intent intent;
		  String menuItemName =menuItems[menuItemIndex];
		 if(menuItemName.equals("Make Invoices"))
		 {
			  intent = new Intent(this,ProcessRentalInvoice.class);
			  intent.putParcelableArrayListExtra("InvoiceList", (ArrayList<? extends Parcelable>) uninvoiced);
				this.startActivity(intent);
		
		 }
	
		return super.onContextItemSelected(item);
	}
		

	

}
