package com.example.metermanager;

import meter.manager.helper.DatabaseHelperClass;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocations extends Activity {
	private DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_locations);
		db=  DatabaseHelperClass.getInstance(this);
	}
public void Add(View v)
{
	try
	{
	EditText location = (EditText)
			findViewById(R.id.editTextlocation);
	String locationname= location.getText().toString().trim();
	if(locationname.length()==0)
	{
		Toast.makeText(getApplicationContext(), "You must add location ", Toast.LENGTH_LONG).show();
		return;
	}
	
	
	//Log.e("Location", locationname);
	if(db.existsLocation(locationname)==false)
	{
	db.AddLocation(locationname);
	Toast.makeText(getApplicationContext(), "Location added", Toast.LENGTH_LONG).show();
	
	location.setText("");
	}else
	{
		Toast.makeText(getApplicationContext(), "This Location already added.", Toast.LENGTH_LONG).show();
		return;
	}
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
