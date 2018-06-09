package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
import meters.model.Apartments;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditTenancy extends Activity {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	private DatabaseHelperClass db;	
	Context context;
	private  ActiveTenancy t;
	List <Apartments> apartment = new ArrayList<Apartments>();
	 EditText id;
	 EditText sDate ;
	 EditText enddate ;
	 EditText rental;
	 Spinner spinner;
	 String apartmentnumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.edit_tenancy);
	context = this.getApplicationContext();	
	db= DatabaseHelperClass.getInstance( this);
	 // get spinner
	  spinner =(Spinner)findViewById(R.id.edit_tenancy_dilog);
			
	// where is spinner data?
   
   // get them
   apartment= db.GetApartments();
   List<String> s= new ArrayList<String>();
   
   for(Apartments a :apartment)
	   
   {
	   String ss=a.getLocation() + " "+ a.getBlock() +" "+  "Room:"+ a.getNumber();
	   s.add(ss);
	 
   }						   
     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,s);
     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// attaching data adapter to spinner
spinner.setAdapter(dataAdapter);
		
	Intent i = getIntent();
	 t = (ActiveTenancy)i.getSerializableExtra("Tenancy");
	 //Log.e("test here",Integer.toString(t.getApartmentid()));
	  id =(EditText)
			findViewById(R.id.editText1);
	// Log.e("t", t.getFullName());
	id.setText(t.getFullName());
	  sDate =(EditText)
			findViewById(R.id.editText2);
	sDate.setText(fm.format(t.getStart_date()));
	  enddate =(EditText)
			findViewById(R.id.editText3);
	enddate.setText(fm.format(t.getEnd_date()));
	  rental =(EditText)
			findViewById(R.id.editText4);
	rental.setText(df.format(t.getMonthly_rental()));
	apartmentnumber= t.getApartment();
	String test=null;
	for(int k=0;k<s.size();k++)
	{
		if(s.get(k).contains(apartmentnumber))
		{
			test= s.get(k);
		}
	}
	 int pos=dataAdapter.getPosition(test);
	 spinner.setSelection(pos);
	}
public void EditRecord(View v) throws ParseException, Exception
{
	// get tenancy id
	int myid= t.getTenancy_id();
	// pick dates but in string format.
	// test pattern matching
	String tomatch=sDate.getText().toString();
	String tomatch2 =enddate.getText().toString();
	if((db.IsvalidDate(tomatch)==false)||(db.IsvalidDate(tomatch2)==false))
	{
		CharSequence msg ="Enter Dates in format yyyy-mm-dd";			
		int duration =Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, msg, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		return;
	}
	Date sdate = null;
	try {
		sdate = fm.parse(sDate.getText().toString());
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	String newstartDate=fm.format(sdate);	
	Date edate = null;
	try {
		edate = fm.parse(enddate.getText().toString());
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	String newendDate=fm.format(edate);
	
	double newrental =Double.parseDouble(rental.getText().toString().replace(",", ""));
	//Log.e("New Rental", Double.toString(newrental));
	// get apartment id
	int apartmentpos= spinner.getSelectedItemPosition();
	Apartments apart=(Apartments)apartment.get(apartmentpos);
	int myapartmentid=apart.get_id();
	// has he selected a different apartment?
	int oldid= t.getApartmentid();
	//Log.e("Old", Integer.toString(oldid));
	//Log.e("new", Integer.toString( myapartmentid));
	
	if(oldid!=myapartmentid)
	  {
		
	if(db.ApartmentNotFreeEdit(myapartmentid,myid)==true)	{
		CharSequence msg ="appartment in use. Edit not saved";			
		int duration =Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, msg, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		return;
	}
		
	}
	try{
	db.EditTenancy(myid, myapartmentid, newstartDate, newendDate, newrental);
	CharSequence msg ="Edits saved";			
	int duration =Toast.LENGTH_LONG;
	Toast toast = Toast.makeText(context, msg, duration);
	toast.setGravity(Gravity.CENTER, 0, 0);
	toast.show();
	
	}catch(Exception e)
	{
		Log.e("Meter Reading error", e.toString());
	}
}
public void Cancel(View v)
{
	this.finish();
}

}
