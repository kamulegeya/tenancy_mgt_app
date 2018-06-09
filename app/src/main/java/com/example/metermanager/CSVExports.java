package com.example.metermanager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.PowerPayments;
import meters.model.RentalPaymentStatus;
import meters.model.VMeterReadings;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class CSVExports extends Activity {
 private ProgressDialog pd;			
Spinner spinner;
String[] options= new String[]{"None","Invoice Payments","Power Payments","Meter Readings"};
@SuppressLint("SimpleDateFormat")
ArrayAdapter<String> dataAdapter1;
@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy");
private List<VMeterReadings> mreadings = new ArrayList<VMeterReadings>();
private List< RentalPaymentStatus> receipts = new ArrayList< RentalPaymentStatus>();
private List<PowerPayments> payments = new ArrayList<PowerPayments>();

private DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exports);
		
		spinner=(Spinner)
				findViewById(R.id.spexports);
		db= DatabaseHelperClass.getInstance(this);
		
		  dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,options);
		     dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter1); 
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub			
			 String s= options[arg2];
			 if(s.equals("Invoice Payments"))
			 {
				 paymenTask.execute();
			 }else if(s.equals("Power Payments"))
			 {
				 powerpaymenTask.execute();
				 
			 }else if(s.equals("Meter Readings"))
			 {
				 meterreadings.execute();
			 
			 }
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	 AsyncTask<Void, Void, String> paymenTask = new AsyncTask<Void, Void, String>()
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
					  String days=Long.toString( v.getDatelate()) + " "+ "Days";
					 String invoiceamount = Double.toString(v.getMonthly_rental());
					  String amountpaid= Double.toString(v.getAmoutpaid());
					 String room= v.getApartment();
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
				 pd = new ProgressDialog(CSVExports.this);
				 pd.setMessage("Creating CSV file...please wait..");
				 pd.show(); 
				super.onPreExecute();
			}
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				 pd.dismiss();
				 Toast.makeText(CSVExports.this,result,Toast.LENGTH_LONG).show();
				super.onPostExecute(result);
			}
		 
			 };
			 
			 
			 AsyncTask<Void, Void, String> meterreadings = new AsyncTask<Void, Void, String>()
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
			          
						 for(int number=0;number<mreadings.size();number++)
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
						 pd = new ProgressDialog(CSVExports.this);
						 pd.setMessage("Creating CSV file...please wait..");
						 pd.show(); 
						super.onPreExecute();
					}
					@Override
					protected void onPostExecute(String result) {
						// TODO Auto-generated method stub
						 pd.dismiss();
						 Toast.makeText(CSVExports.this,result,Toast.LENGTH_LONG).show();
						super.onPostExecute(result);
					}
				 
					 };
					 
					 AsyncTask<Void, Void, String> powerpaymenTask = new AsyncTask<Void, Void, String>()
							 {

								@Override
								protected String doInBackground(Void... params) {
									//folder for storing file
									File exportDir =
											new File(Environment.getExternalStorageDirectory(), "Power Payments");
											if (!exportDir.exists()) {
											exportDir.mkdirs();
											}
								 File file = null;
								
								try {
									file= new File(exportDir, "Power.csv");
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
								  String arrStr1[] = { "Tenant", "PaymentDate", "Amount","Narration"};
										 
					              csvWrite.writeNext(arrStr1);
					              //get all meter readings 
					 			 payments=db.GetPayment();	 		
					          
								 for(int number=0;number<payments.size();number++)
								 {
									 PowerPayments v = (PowerPayments)payments.get(number);
									 String name= v.getFullName();
									 String sdate= fm.format(v.getPaymentDate());								  
									 								  
									 String amount = Double.toString(v.getAmount());
									  String narration= v.getNarration();
									
									 String[]values={name,sdate,amount,narration};
											 
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
								 pd = new ProgressDialog(CSVExports.this);
								 pd.setMessage("Creating CSV file...please wait..");
								 pd.show(); 
								super.onPreExecute();
							}
							@Override
							protected void onPostExecute(String result) {
								// TODO Auto-generated method stub
								 pd.dismiss();
								 Toast.makeText(CSVExports.this,result,Toast.LENGTH_LONG).show();
								super.onPostExecute(result);
							}
						 
							 };				 		 
}
