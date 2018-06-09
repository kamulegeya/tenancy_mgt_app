package com.example.metermanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.TenantList;
import meters.model.VMeterReadings;
import android.R.color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;
public class Bills extends Activity  implements OnItemSelectedListener {		
	// list for dropdownlist
	private List<TenantList> readings =new ArrayList<TenantList>();
	//dropdown 
	Spinner spinner;
	// list of meter readings
	  List<VMeterReadings> mreadings = new ArrayList<VMeterReadings>();
	  private ProgressDialog pd;
	@SuppressLint("SimpleDateFormat")
	private SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	// get handle to db helper class
	
	private DatabaseHelperClass db;
	 String[] menuItems = {"SMS Bill","Export CSV","Send Email"};
	 IndividualReadings individualreadings;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		db= DatabaseHelperClass.getInstance( this);	
		try{
			readings=db.GetTenantIDs();
			 // create an array list of 
			 List<String> tenantlist= new  ArrayList<String>();
			 
			 for( TenantList m:readings)
			 {
				 tenantlist.add(m.getFullName());
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.spSelectedTenant);
			 // add event listening
			 
			 
		     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);  		
		spinner.setOnItemSelectedListener(this);
			
	}catch(Exception e)
	{
		Log.e("Main Activity Err", e.toString());
	}		
		
	}
  
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	


	
		
	
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		try{
			int pos = spinner.getSelectedItemPosition();
			// get the tenant selected  in the list
			TenantList tenant=(TenantList)readings.get(pos);
			// get the ID
			int id =tenant.get_id();
			// instantiate the get individual readings class and pass the id as the parameter
			individualreadings= new IndividualReadings(this,id);
			ListView listview =(ListView)findViewById(R.id.reading_list);			
			listview.setBackgroundColor(color.holo_orange_dark);	
			listview.setAdapter(individualreadings);
			registerForContextMenu(listview);
			} catch(Exception e)
			{
				Log.e("Spinner Error", e.toString());
			}
		
	}
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.reading_list) {		  
		    menu.setHeaderTitle("Choose a menu item");			   
		    for (int i = 0; i<menuItems.length; i++) {
		        menu.add(Menu.NONE, i, i, menuItems[i]);
		    }
		}
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem i) {
		// TODO Auto-generated method stub
		 int menuItemIndex = i.getItemId();
		 String menuItemName =menuItems[menuItemIndex];
		 final AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) i 
		            .getMenuInfo(); 
		 final int pos = menuInfo.position;
		 if(menuItemName.equals("SMS Bill"))
			 
		 {
			 // we grab reading details
			 ///we grab tenant ID
			 /// 
			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm Sending Sms");
				b.setMessage("Are sure you want to send an sms of this bill?."+"\n"+
						     "Applicable Service Provider SMS Charges applies.");
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
					@Override
			 public void onClick(DialogInterface dialog, int which) {			 	 
			 
			 try{
			 int t_id = spinner.getSelectedItemPosition();
			 TenantList t=(TenantList)readings.get(t_id);
				// get the ID
				//int id =t.get_id();
								
				 String mobile = null;
				
				if(Integer.parseInt(t.getMobile())==0)
				{
					mobile= t.getMobile2().trim();
				}
								
				else
				{
					mobile= t.getMobile().trim();
				}
				
				
				String fName= t.getFullName();
				
				// get reading details
				VMeterReadings v=(VMeterReadings ) individualreadings.getItem(pos);
				// get values 
				
				String ReadingDate= fm.format(v.getReadingDate());				
				String Amount= df.format(v.getAmount());				
				String msg ;
				msg=  "Dear "+ fName + ","+
				      " your current power bill as at " + ReadingDate + " is  UGX" + Amount + "."+"\n"+
						" Please arrange to clear the bill before Umeme disconnects us";
						
				
			// SendSms(mobile, msg);
				
				String sms= msg;
				Intent foward= new Intent(Intent.ACTION_VIEW);
				foward.setData(Uri.parse("smsto"));
				foward.putExtra("sms_body", sms);
				foward.setType("vnd.android-dir/mms-sms");
				foward.putExtra("address",mobile); 
				startActivity(foward);
			
			 }catch(Exception e)
			 {
				 e.printStackTrace();
			 }
					}
				});
				b.show();		
			 
		 }if(menuItemName.equals("Export CSV"))
		 {
				 AsyncTask<Void, Void, String> simpleGetTask = new AsyncTask<Void, Void, String>()
					 {

						@Override
						protected String doInBackground(Void... params) {
							//folder for storing file
							File exportDir =
									new File(Environment.getExternalStorageDirectory(), "Meter Readings");
									if (!exportDir.exists()) {
									exportDir.mkdirs();
									}
						 File file = null;
						
						try {
							file= new File(exportDir, "MeterReadings.csv");
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
						  String arrStr1[] = { "Tenant", "ReadingDate", "CurrentReading", "PreviousReadingDate",
								  "PreviousReading","Units Used","Unit Rate","Amount" };
			              csvWrite.writeNext(arrStr1);
			              //get all meter readings 
			 			 mreadings= db.GetReadings();			 		
			          
						 for(int number=0;number<readings.size();number++)
						 {
							 VMeterReadings v= (VMeterReadings)(mreadings.get(number));
							 String name= v.getTenant();
							 String readingdate= fm.format(v.getReadingDate());
							 String currentreading = Double.toString(v.getCurrentReading());
							 String previousreadingdate= fm.format(v.getPreviousReadingDate());
							 String previousmeterreading = Double.toString(v.getPreviousMeterReading());
							 String units= Double.toString(v.getUnits());
							 String rate= Double.toString(v.getRate());
							 String amount= Double.toString(v.getAmount());
							 String[]values={name,readingdate,currentreading,previousreadingdate,
								        previousmeterreading,units,rate,amount};
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
						 pd = new ProgressDialog(Bills.this);
						 pd.setMessage("Creating CSV file...please wait..");
						 pd.show(); 
						super.onPreExecute();
					}
					@Override
					protected void onPostExecute(String result) {
						// TODO Auto-generated method stub
						 pd.dismiss();
						 Toast.makeText(Bills.this,result,Toast.LENGTH_LONG).show();
						super.onPostExecute(result);
					}
				 
					 };
					 
				simpleGetTask.execute();
			 
		 }if(menuItemName.equals("Send Email"))
		 {
			 // get selected tenant from the list
			 int pos1 = spinner.getSelectedItemPosition();				
			 TenantList tenant=(TenantList)readings.get(pos1);
				// get the ID
				int id =tenant.get_id();
				// get email
				String myemail= tenant.getEmail();
				String name= tenant.getFullName();
				// get a list of that person's readings
				 List<VMeterReadings> myreadings = new ArrayList<VMeterReadings>();
				 
					File exportDir =
							new File(Environment.getExternalStorageDirectory(), "EmailedReadings");
							if (!exportDir.exists()) {
							exportDir.mkdirs();
							}
				 File file = null;
				
				try {
					file= new File(exportDir, name+".csv");
					file.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "Failed to create file", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}		
					
				CSVWriter csvWrite=null;
				try {
					csvWrite	 = new CSVWriter( new  FileWriter(file));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "Can not read file", Toast.LENGTH_LONG).show();
					
					e.printStackTrace();
				}
				  String arrStr1[] = { "Tenant", "ReadingDate", "CurrentReading", "PreviousReadingDate",
						  "PreviousReading","Units Used","Unit Rate","Amount" };
	              csvWrite.writeNext(arrStr1);
				 
				 myreadings= db.GetIndividualReading(id);
				 // iterate through and make write to file.
				 
				 for(int number=0;number<myreadings.size();number++)
				 {
					 VMeterReadings v= (VMeterReadings)(myreadings.get(number));
					 String myname= v.getTenant();
					 String readingdate= fm.format(v.getReadingDate());
					 String currentreading = Double.toString(v.getCurrentReading());
					 String previousreadingdate= fm.format(v.getPreviousReadingDate());
					 String previousmeterreading = Double.toString(v.getPreviousMeterReading());
					 String units= Double.toString(v.getUnits());
					 String rate= Double.toString(v.getRate());
					 String amount= Double.toString(v.getAmount());
					 String[]values={myname,readingdate,currentreading,previousreadingdate,
						        previousmeterreading,units,rate,amount};
					  csvWrite.writeNext(values);

					 
				 }
				try {
					csvWrite.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String[] TO = {myemail};			    
				Uri u1  =   null;
				u1  =   Uri.fromFile(file);
				Intent sendIntent = new Intent(Intent.ACTION_SEND);
				 sendIntent.setData(Uri.parse("mailto:"));
			     sendIntent.setType("text/plain");
			     sendIntent.putExtra(Intent.EXTRA_EMAIL, TO);			     
				sendIntent.putExtra(Intent.EXTRA_SUBJECT, "MeterReadings");
				sendIntent.putExtra(Intent.EXTRA_TEXT, "Here your meter readings");
				sendIntent.putExtra(Intent.EXTRA_STREAM, u1);				
				startActivity(sendIntent);	
				
		 }
		 
	
		 
		return super.onMenuItemSelected(featureId, i);
	}
	
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
