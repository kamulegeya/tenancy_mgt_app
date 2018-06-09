package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.UnPaidInvoices;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class EditInvoices extends Activity {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	private DatabaseHelperClass db;
	UnPaidInvoices u;
	 EditText sDate ;
	 EditText enddate ;
	 EditText duedate ;		
	 EditText rental;
	 Spinner spinner;
	 String apartmentnumber;
	 int tenancyid;
	 String[] my;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_rental_invoices);		
		db= DatabaseHelperClass.getInstance( this);
		 // get spinner
		  spinner =(Spinner)findViewById(R.id.tenancy);
		// get intent...it has extra in form of an invoice
		  Intent i= getIntent();			
			 my=i.getStringArrayExtra("invoice");	
			
	        String[] s= new String[]{my[6] + ":" + "Room:"+ my[7]};
	        // get tenancy id(for checking overlapping dates)
	   tenancyid= Integer.parseInt(my[0]);
	  				   
	     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,s);
	     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter);
	sDate= (EditText)
			findViewById(R.id.start_date);
	sDate.setText(my[2]);
	enddate= (EditText)
			findViewById(R.id.end_date);
	enddate.setText(my[3]);
	
	duedate= (EditText)
			findViewById(R.id.due_date);
	duedate.setText(my[4]);
	
	rental=(EditText)
			findViewById(R.id.editTextRental);
	rental.setText(df.format(Double.parseDouble(my[5])));
	 rental.addTextChangedListener(new NumberTextWatcher(rental));
	int pos=dataAdapter.getPosition(my[6]);
	 spinner.setSelection(pos);
	
	 Button b=(Button)
			 findViewById(R.id.Edits);
	 b.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try{
				int id= Integer.parseInt(my[1]);	
				String sdate=sDate.getText().toString();
				//Log.e("date",Integer.toString(id)); 
				String edate =enddate.getText().toString();
				String dd= duedate.getText().toString();
				if(db.IsvalidDate(edate)==false||db.IsvalidDate(edate)==false || db.IsvalidDate(dd)==false)
				{
					Toast.makeText(getApplicationContext(), "You must enter Dates in format yyyy-mm-dd", Toast.LENGTH_LONG).show();
					return;
					
				}
				double myrental = Double.parseDouble(rental.getText().toString().replace(",", ""));
				if(db.InvoiceOverLap(tenancyid, sdate, edate,id)==false)
				{
				db.EditRentalInvoice(id, sdate, edate, myrental, dd);
				Toast.makeText(getApplicationContext(), "Invoiced edits Saved", Toast.LENGTH_LONG).show();
				}else
				{
					Toast.makeText(getApplicationContext(), "Over lapping invoice Dates. Edits not saved.", Toast.LENGTH_LONG).show();
					
				}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			
		}
	});
	 
	}
	
	
	
}
