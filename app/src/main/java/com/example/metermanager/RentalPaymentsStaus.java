package com.example.metermanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.RentalPaymentStatus;
import meters.model.VMeterReadings;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SearchView.OnQueryTextListener;
import au.com.bytecode.opencsv.CSVWriter;

public class RentalPaymentsStaus extends Activity {
	private List<RentalPaymentStatus> receipts =new ArrayList<RentalPaymentStatus>();
	private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");	
	 private ProgressDialog pd;
	private RentalPaymentsStatusAdapter  invoicelist ;
	String[] menuItems= {"Export CSV"};
	private DatabaseHelperClass db;
	SearchView search;
	ListView list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invoices1);	
		db=  DatabaseHelperClass.getInstance(this);
		Intent i= getIntent();
		//get the intent....if it has an extral ID..use a different stuff
		int id= i.getIntExtra("ID", 0);
		if(id!=0)
		{
			invoicelist= new RentalPaymentsStatusAdapter (this,id);
		}else
		{
			
		
		invoicelist= new RentalPaymentsStatusAdapter (this);	
		}
		
		list= (ListView) findViewById(R.id.invoices_list);			
			    list.setAdapter(invoicelist); 
			    registerForContextMenu(list);
			    
			  
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
				invoicelist.getFilter().filter(newText);
				return false;
			}
		});
		return true;
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.invoices_list) {		  
		    menu.setHeaderTitle("Choose a menu item");			   
		    for (int i = 0; i<menuItems.length; i++) {
		        menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		int menuItemIndex = item.getItemId();
		 String menuItemName =menuItems[menuItemIndex];
		 //final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item 
		           //.getMenuInfo(); 
		// final int pos = menuInfo.position;
     if(menuItemName.equals("Export CSV"))
			 
		 {
    	 AsyncTask<Void, Void, String> simpleGetTask = new AsyncTask<Void, Void, String>()
				 {

					@Override
					protected String doInBackground(Void... params) {
						//folder for storing file
						File exportDir =
								new File(Environment.getExternalStorageDirectory(), "Rental Payments");
								if (!exportDir.exists()) {
								exportDir.mkdirs();
								}
					 File file = null;
					
					try {
						file= new File(exportDir, "Payments.csv");
						file.createNewFile();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "Failed to create file";
					
					}		
						
					CSVWriter csvWrite=null;
					try {
						csvWrite	 = new CSVWriter( new  FileWriter(file));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return "Can not read file";
						
					
					}
					  String arrStr1[] = { "Tenant", "StartDate", "EndDate","Due Date","Date Paid", "InvoiceAmount",
							  "AmountPaid","Room/Apartment","Days overdue" };
		              csvWrite.writeNext(arrStr1);
		              //get all meter readings 
		 			 receipts=db.GetRentalPaymentsStatus();		 		
		          
					 for(int number=0;number<receipts.size();number++)
					 {
						 RentalPaymentStatus v = (RentalPaymentStatus)receipts.get(number);
						 String name= v.getFullName();
						 String sdate= fm.format(v.getStart_date());
						  String enddate= fm.format(v.getEnd_date());
						  
						  String duedate=null;
						  if(v.getDuedate()!=null)
						  {
						  duedate=  fm.format(v.getDuedate());
						  }
						  String datepaid=null;
						  if(v.getDatepaid()!=null)
						  {
						   datepaid= fm.format(v.getDatepaid());
						  }else
						  {
							  datepaid="Not Paid";
						  }
						  
						 String invoiceamount = Double.toString(v.getMonthly_rental());
						  String amountpaid= Double.toString(v.getAmoutpaid());
						 String room= v.getApartment();
						 String days=Long.toString( v.getDatelate()) + " "+ "Days";
						 String[]values={name,sdate,enddate,duedate,datepaid,invoiceamount,
								 amountpaid,room,days};
						  csvWrite.writeNext(values);

						 
					 }
					try {
						csvWrite.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return "Data exported to file:" + file.toString() ;
					}
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					// TODO Auto-generated method stub
					 pd = new ProgressDialog(RentalPaymentsStaus.this);
					 pd.setMessage("Creating CSV file...please wait..");
					 pd.show(); 
					super.onPreExecute();
				}
				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					 pd.dismiss();
					 Toast.makeText(RentalPaymentsStaus.this,result,Toast.LENGTH_LONG).show();
					super.onPostExecute(result);
				}
			 
				 };
				 
			simpleGetTask.execute();
			
		 
			 
			 
		 }
		return super.onMenuItemSelected(featureId, item);
	}

}
