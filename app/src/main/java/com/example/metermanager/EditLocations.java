package com.example.metermanager;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Locations;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditLocations  extends Activity{
	EditText txt;
	Button b;
	int id;
	DatabaseHelperClass db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_locations);
		
		txt=(EditText)
				findViewById(R.id.editTextlocation);
		b=(Button)
				findViewById(R.id.btnEdit);
	    db= DatabaseHelperClass.getInstance(getApplicationContext());
	    // intent starting this Activity has an extra carrying the Locations Object
		Intent i= getIntent();
		Locations myl=(Locations)i.getSerializableExtra("L");
		txt.setText(myl.getLocation());
		id=myl.get_id();
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String s= txt.getText().toString().trim();
				// if nothing??
				if(s.length()==0)
				{
					Toast.makeText(getApplicationContext(), "Location can not be empty", Toast.LENGTH_LONG).show();
					return;
				}
				db.EditLocation(id, s);
				Toast.makeText(getApplicationContext(), "Edits Saved", Toast.LENGTH_LONG).show();
				
				
			}
		});
	}

}
