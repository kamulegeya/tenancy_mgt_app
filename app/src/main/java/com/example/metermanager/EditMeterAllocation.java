package com.example.metermanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.CurrentTenants;
import meters.model.MeterAllocation;
import meters.model.MeterReading;
import meters.model.Meters;
import meters.model.TenantList;
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
import android.widget.TextView;
import android.widget.Toast;

public class EditMeterAllocation extends Activity {
	MeterAllocation mallocation;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	private DatabaseHelperClass db;
	private List<TenantList> tenants =new ArrayList<TenantList>();	
	private List<Meters> meters =new ArrayList<Meters>();	
	//SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");

	// tenants list
	Spinner spinner;
	// meter list
	Spinner spinner1;	
	 List<String> metelist;
	 EditText startdate;
	 EditText reading;
	 EditText rate;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editmeterallocation);
		db= DatabaseHelperClass.getInstance(this);
		Intent i = getIntent();
		 mallocation = (MeterAllocation)i.getSerializableExtra("MeterAllocation");
		tenants=db.GetTenantIDs();
		 // create an array list of 
		 List<String> tenantlist= new  ArrayList<String>();
		 
		 for( TenantList m:tenants)
		 {
			 tenantlist.add(m.getFullName());
		 }
		 //Creating adapter for spinner
		 spinner = (Spinner) findViewById(R.id.tenant_allocate_m);
		 // add event listening			 
		 
	     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
	     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// attaching data adapter to spinner
	spinner.setAdapter(dataAdapter); 
	//set default as current tenant
	int  sele= dataAdapter.getPosition(mallocation.getFullName());
	spinner.setSelection(sele);
	/// spinner 2
	meters=db.GetMeterIDs();
	 // create an array list of 
	  metelist= new  ArrayList<String>();
	 
	 for( Meters m:meters)
	 {
		 metelist.add(m.getMeterNumber());
	 }
	 //Creating adapter for spinner
	 spinner1 = (Spinner) findViewById(R.id.choose_meter);
	 // add event listening			 
	 
    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,metelist);
    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// attaching data adapter to spinner
spinner1.setAdapter(dataAdapter1); 
// set default selected items
int  sele1= dataAdapter1.getPosition(mallocation.getmeternumber());
spinner1.setSelection(sele1);	
	// get tenant name
	TextView Tenant= (TextView)
			findViewById(R.id.txtViewCurrentTenat);
	Tenant.setText(mallocation.getFullName());
	TextView meter= (TextView)
			findViewById(R.id.txtViewMeter);
	meter.setText(mallocation.getmeternumber());
	startdate= (EditText)
			findViewById(R.id.txt_date_allocated);
	startdate.setText(fm.format(mallocation.getStartDate()));
	 reading= (EditText)
			findViewById(R.id.meterReading);
	reading.setText(Double.toString((mallocation.getReading())));
	
	 rate= (EditText)
			findViewById(R.id.txtRate);
	rate.setText(Double.toString((mallocation.getRate())));
	Button button=(Button)
			findViewById(R.id.btnEdit);
	button.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// has he choosen another meter?
			// it is free?
			//get meterid
			int pos= spinner1.getSelectedItemPosition();
			Meters m=(Meters)meters.get(pos);
			int meterid= m.get_id();
			// get tenantid from spinner
			int pos1= spinner.getSelectedItemPosition();
			TenantList t=(TenantList)tenants.get(pos1);
			int tenantid= t.get_id();
			
			// get old tenant-meterid for allocation
			int tenant_meterid=  mallocation.get_id();
			if(db.canRellocateMeter(meterid, tenantid, tenant_meterid)==false)
			{
				String msg="Either Meter or Tenant already taken";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				return;
			}
			
			if(db.IsvalidDate(startdate.getText().toString().trim())==false)
			{
				String msg="Enter Dates in Format yyyy-mm-yy";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				return;
			
			}
			// every thing good...
			//edit allocation
			String date=startdate.getText().toString().trim();
			///
			db.EditMeterAllocation(tenant_meterid, tenantid, meterid, date);
			// edit meterreadings
			MeterReading r= new MeterReading();
			Double myrate=Double.parseDouble(rate.getText().toString().trim());
			Double myreading= Double.parseDouble(reading.getText().toString().trim());
			r.setTenantMeter_id(tenant_meterid);
			r.setReading(myreading);
			r.setRate(myrate);
			try {
				r.setReadingDate(fm.parse(date));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			r.set_id(mallocation.getReading_id());
			db.editMeterReading(r);
			String msg="Edits saved";
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}
	});
	
	}
}
