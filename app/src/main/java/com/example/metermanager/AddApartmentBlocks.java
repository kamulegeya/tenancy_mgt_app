package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Locations;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddApartmentBlocks extends Activity {
private List<Locations> locations=  new ArrayList<Locations>();
private DatabaseHelperClass db;
Spinner  spinner ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_apartments_blocks);
		 db=  DatabaseHelperClass.getInstance(this);
		 locations=db.GetLocations();
		// create an array list of 
		 List<String> listlocation= new  ArrayList<String>();
		 
		 for( Locations l:locations)
		 {
			 listlocation.add(l.getLocation());
			 
		 }
		// Creating adapter for spinner
		 spinner = (Spinner) findViewById(R.id.location_id);
		 // here wrong. cross check
	     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listlocation);
	     dataAdapter
	     .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     spinner.setAdapter(dataAdapter);
	}
	public void Add(View v)
	{
		// get location object
		try
		{
		int pos = spinner.getSelectedItemPosition();
		if(pos==-1)
		{
			Toast.makeText(getApplicationContext(), "No Location  data entered", Toast.LENGTH_LONG).show();
			return;
		}
		Locations lo= (Locations)locations.get(pos);
		
		int location_id= lo.get_id();
		EditText block = (EditText)
				findViewById(R.id.editTextblock);
		String blocknumber= block.getText().toString().trim();
		if(blocknumber.length()==0)
		{
		Toast.makeText(getApplicationContext(), "You must add a block number.", Toast.LENGTH_LONG).show();
		return;
		}
		db.AddApartmentBlock(location_id, blocknumber);
		Toast.makeText(getApplicationContext(), "Record added.", Toast.LENGTH_LONG).show();
		
		block.setText("");
		}catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Failed to add apartment block", Toast.LENGTH_LONG).show();
			
			
		}
	}
	
	public void Cancel(View v)
	{
		this.finish();
	}

}