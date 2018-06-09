package com.example.metermanager;

import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentBlocks;
import meters.model.Apartments;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditApartments extends Activity {
	private List<ApartmentBlocks> blocks=  new ArrayList<ApartmentBlocks>();
	private DatabaseHelperClass db;
	Spinner  spinner ;
	EditText number;
	 Apartments apartment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_appartments);
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
	     Intent i= getIntent();
	      apartment=(Apartments)i.getSerializableExtra("Room");
	      String use= "Location:" + apartment.getLocation() + " Block:"+ apartment.getBlock();
		 int pos=dataAdapter.getPosition(use);
		spinner.setSelection(pos);
		number = (EditText)
				findViewById(R.id.editTextnumber);
		number.setText(apartment.getNumber());
	}
	public void Add(View v)
	{
		// get location object
		int pos = spinner.getSelectedItemPosition();
		ApartmentBlocks lo= (ApartmentBlocks)blocks.get(pos);
		int block_id= lo.get_id();
		int apartmentid=   apartment.get_id();
		EditText block = (EditText)
				findViewById(R.id.editTextnumber);
		String roomnumber= block.getText().toString().trim();
		if(roomnumber.length()==0)
		{
			Toast.makeText(getApplicationContext(), "Room/Apartment  Number can not be empty", Toast.LENGTH_LONG).show();
			return;
		}
	    db.EditApartment(apartmentid, block_id, roomnumber);
		block.setText("");
	}
	
	public void Cancel(View v)
	{
		this.finish();
	}

}
