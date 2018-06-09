package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentBlocks;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddApartments extends Activity {
	private List<ApartmentBlocks> blocks=  new ArrayList<ApartmentBlocks>();
	private DatabaseHelperClass db;
	Spinner  spinner ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_appartments);
		db=  DatabaseHelperClass.getInstance( this);
		 blocks=db.GetApartmentblocks();
		// create an array list of 
		 List<String> listlocation= new  ArrayList<String>();
		 
		 for(ApartmentBlocks l:blocks)
		 {
			 String use= "Location:" + l.getLocation() + " Block:"+ l.getBlocknumber();
			 listlocation.add(use);
			 
		 }
		// Creating adapter for spinner
		 spinner = (Spinner) findViewById(R.id.block_id);
		 // here wrong. cross check
	     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,listlocation);
	     dataAdapter
	     .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	     spinner.setAdapter(dataAdapter);
		
	}
	public void Add(View v)
	{
		// get location object
		int pos = spinner.getSelectedItemPosition();
		if(pos==-1)
		{
			Toast.makeText(getApplicationContext(), "No block   data entered", Toast.LENGTH_LONG).show();
			return;
		}
		ApartmentBlocks lo= (ApartmentBlocks)blocks.get(pos);
		
		int block_id= lo.get_id();
		EditText block = (EditText)
				findViewById(R.id.editTextnumber);		
		
		String number= block.getText().toString().trim();
		if(number.length()==0)
		{
		Toast.makeText(getApplicationContext(), "You must enter a room/apartment number.", Toast.LENGTH_LONG).show();
		return;
		}
		db.AddApartment(block_id, number);
		Toast.makeText(getApplicationContext(), "Record added.", Toast.LENGTH_LONG).show();		
		block.setText("");
	}
	
	public void Cancel(View v)
	{
		this.finish();
	}

}
