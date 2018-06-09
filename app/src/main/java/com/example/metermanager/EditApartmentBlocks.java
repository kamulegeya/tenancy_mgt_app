package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentBlocks;
import meters.model.Locations;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditApartmentBlocks extends Activity {
private List<Locations> locations=  new ArrayList<Locations>();
private DatabaseHelperClass db;
ApartmentBlocks bb;
Spinner  spinner ;
EditText block;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_apartments_blocks);
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
	     Intent i = getIntent();
	      bb=(ApartmentBlocks)i.getSerializableExtra("Block");
	     String use= bb.getLocation();
		 int pos=dataAdapter.getPosition(use);
		 spinner.setSelection(pos);
		  block = (EditText)
					findViewById(R.id.editTextblock);
		  block.setText(bb.getBlocknumber());
	}
	public void Add(View v)
	{
		// get location object
		try
		{
		int pos = spinner.getSelectedItemPosition();
		int id= bb.get_id();
		Locations lo= (Locations)locations.get(pos);
		int location_id= lo.get_id();		
		String blocknumber= block.getText().toString().trim();
		if(blocknumber.length()==0)
		{
			Toast.makeText(getApplicationContext(), "Block Number can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
		db.EditBlock(id, location_id, blocknumber);
		Toast.makeText(getApplicationContext(), "Edits Saved", Toast.LENGTH_LONG).show();
		
		//block.setText("");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void Cancel(View v)
	{
		this.finish();
	}

}