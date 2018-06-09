package com.example.metermanager;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
import meters.model.Apartments;
import meters.model.Tenants;
import meters.model.VReadings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
public class ActiveTenancyList extends Activity {
	private DatabaseHelperClass db;	
	private TenancyList tenants;
	
	private List<ActiveTenancy> t= new ArrayList<ActiveTenancy>();
	private ListView listview;
	Context context ;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	//SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	int pos;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat fm1 =new SimpleDateFormat("dd-MM-yyyy");
	String[]menuitems= {"Delete","Terminate","Expired","Edit Record","Rental Payments","View InActive"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tenants);
		//test();
		db=  DatabaseHelperClass.getInstance( this);	
		tenants= new TenancyList(this);
		listview =(ListView)findViewById(R.id.lstalltenants);		
		listview.setAdapter(tenants);		
		registerForContextMenu(listview);
		Date date = new Date();
		String getdate = fm.format(date);
		t= db.GetActiveTenancy(getdate);
		if(t.size()!=0)
		{
			String s=null;
			StringBuilder listString = new StringBuilder();
			for(int i=0;i< t.size();i++)
			{
				ActiveTenancy n=(ActiveTenancy)t.get(i);
				 s= "Location:"+ n.getLocation() + " " + "Block:" + n.getBlock() + " " +  "Room:"+ n.getApartment() + "Tenant:"+ n.getFullName() + " " + "End Date:"+ " " + fm1.format(n.getEnd_date());
				 listString.append(s+ "\n");
			
				
			}
			
			final Dialog dialog = new Dialog(ActiveTenancyList.this);
			dialog.setContentView(R.layout.expiry_display_dialog);
			dialog.setTitle("Expired Tenancies");	
			TextView t=(TextView)
					dialog.findViewById(R.id.txtExpired);
			t.setText(listString);			
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
			
			//Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
		}
		
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.rental, menu);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(item.getItemId()){
			
					   
			case R.id.add_locations:
				 intent = new Intent(this,AddLocations.class);
				this.startActivity(intent);
			   break;	
			
			
			case R.id.locations:
				 intent = new Intent(this,ApartmentLocations.class);
				this.startActivity(intent);
			   break;	
			case R.id.add_blocks:
				 intent = new Intent(this,AddApartmentBlocks.class);
				this.startActivity(intent);
			   break;	
			case R.id.apartments_list:
				 intent = new Intent(this,ApartmentsList.class);
				this.startActivity(intent);
			   break;	
			case R.id.add_apartments:
				 intent = new Intent(this,AddApartments.class);
				this.startActivity(intent);
			   break;	
			case R.id.invoices_view:
				 intent = new Intent(this,Invoices.class);
				this.startActivity(intent);
			   break;	
			   
			case R.id.add_tenancy:
				 intent = new Intent(this, AddTenancy.class);
				this.startActivity(intent);
			   break;	
			case R.id.add_invoices:
				 intent = new Intent(this, ProcessRentalInvoice.class);
				this.startActivity(intent);
			   break;	
			case R.id.receipts:
				 intent = new Intent(this, RentalPayments.class);
				this.startActivity(intent);
			   break;
			case R.id.rental_payments:
				 intent = new Intent(this, RentalPaymentsList.class);
				this.startActivity(intent);
			   break;
			case R.id.payments_status:
				 intent = new Intent(this, RentalPaymentsStaus.class);
				this.startActivity(intent);
			   break;
			case R.id.invoice_status:
				intent=new Intent(this,RoomInvoicingStatus.class);
				this.startActivity(intent);
               break;
			case R.id.blocks:
				 intent = new Intent(this, BlocksList.class);
				this.startActivity(intent);
			   break;
			   
						case R.id.annual:
							 intent = new Intent(this, AnnualFigures.class);
							this.startActivity(intent);
						   break;
			   
			   default:
			  return super.onOptionsItemSelected(item);
				}
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.lstalltenants) {		  
		    menu.setHeaderTitle("Choose a menu item");			   
		    for (int i = 0; i<menuitems.length; i++) {
		        menu.add(Menu.NONE, i, i, menuitems[i]);
		    }
	}
}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		 int menuItemIndex = item.getItemId();		
		  String menuItemName =menuitems[menuItemIndex];
		  final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
		            .getMenuInfo(); 
		  pos= menuInfo.position;
		 if(menuItemName.equals("Expired"))
		 {
			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm Expired Tenancy");
				b.setMessage("Are sure this Tenancy Expired?."+"\n"+
						     "Once You click yes, you wont be able to undo this");
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
					@Override
			 public void onClick(DialogInterface dialog, int which) {	
						// get tenancy						 
						 ActiveTenancy t=(ActiveTenancy ) tenants.getItem(pos);
						 // get id
						 // get expiry date
						 Date edd=t.getEnd_date();
						 long exx=edd.getTime();
						 // get current date
						 Date d= new Date();
						 long today=d.getTime();
						 // is end date after today?
						 if(exx <=today)
						 {
						 int tenancy_id= t.getTenancy_id();
						 db.ExpiredTenancy(tenancy_id);
						 }else
						 {
							 String msg= "This Tenancy has not expired because the end date is on:"+ fm.format(edd);
						 Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
								return;
						 }
			
					}
				});
				b.show();
		   
		   
			 
			 
		 }else if(menuItemName.equals("Edit Record"))
		 {
			 //pos=menuInfo.position;						
			 ActiveTenancy r=(ActiveTenancy ) tenants.getItem(pos);	
			// Log.e("Tenant", r.getFullName());
			Intent intent = new Intent(this,EditTenancy.class);
			intent.putExtra("Tenancy", (Serializable)r);
			startActivity(intent);
			 
		 }else if(menuItemName.equals("Terminate"))
		 {
			 int pos = menuInfo.position;
			 ActiveTenancy t=(ActiveTenancy)
				tenants.getItem(pos);				
		        // check if selected already inactive
                 //String tenant=t.getFullName();	
			      String[] extra= new String[]{Integer.toString(t.get_id()),t.getFullName(),Integer.toString(t.getTenancy_id())};
				 Intent intent = new Intent(this,SetTenancyInActive.class);
				 intent.putExtra("test", extra);
				 this.startActivity(intent); 

		 		         
				    
			 	
			 
			 
		 }else if(menuItemName.equals("Rental Payments"))
		 {
			 int pos = menuInfo.position;
			 ActiveTenancy t=(ActiveTenancy)
				tenants.getItem(pos);
				
		       // get tenancyit
			 int tenancyid= t.getTenancy_id();
			 //get an intent
			 Intent i= new Intent(this,RentalPaymentsStaus.class);
			 i.putExtra("ID", tenancyid);
			 startActivity(i);		 
		 		         
				    
			 	
			 
			 
		 }else if(menuItemName.equals("Delete"))
		 {
			 int pos = menuInfo.position;
			 ActiveTenancy t=(ActiveTenancy)
				tenants.getItem(pos);
				
		       // get tenancyit
			 int tenancyid= t.getTenancy_id();
			 if(db.deleteTenancy(tenancyid))
			 {
				 tenants.remove(pos);
				 tenants.notifyDataSetChanged();
			 }else
			 {
				 Toast.makeText(getApplicationContext(), "Can not delete a tenancy with invoices", Toast.LENGTH_LONG).show();
			 }
				    
			 	
			 
			 
		  }else if(menuItemName.equals("View InActive"))
		 {
			 
			  Intent i= new Intent(this,InActiveTenancy.class);
			  startActivity(i);
		 }
		 
		 return super.onContextItemSelected(item);
	}   	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		tenants= new TenancyList(this);			
		listview.setAdapter(tenants);		
		super.onResume();
	}
	
}
