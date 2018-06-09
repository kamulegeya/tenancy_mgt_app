package com.example.metermanager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserSettingsActivity extends Activity {
	
	   EditText name;
	   EditText tel;
	   EditText address;
	   EditText txcurrency;
	   Button b;
	   public static final String MyPREFERENCES = "MyPrefs" ;
	   public static final String Name = "nameKey"; 
	   public static final String Phone = "phoneKey"; 	   
	   public static final String Place = "placeKey";
	   public static final String Currency = "CurrencyKey";
	   SharedPreferences sharedpreferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usersettings);
		name=(EditText)
				findViewById(R.id.edittxtname);
		tel=(EditText)
				findViewById(R.id.edittxtNumber);
		 address=(EditText)
					findViewById(R.id.editTxtAddress);
		 txcurrency=(EditText)
					findViewById(R.id.edittxtCurrency);
		 
		 sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 if (sharedpreferences.contains(Name))
	      {
	         name.setText(sharedpreferences.getString(Name, ""));

	      }
	      if (sharedpreferences.contains(Phone))
	      {
	         tel.setText(sharedpreferences.getString(Phone, ""));

	      }
	      if (sharedpreferences.contains(Place))
	      {
	    	  address.setText(sharedpreferences.getString(Place, ""));
	    	  
	      }
	      
	    	  
	    if (sharedpreferences.contains(Currency))
		      {
	    		  txcurrency.setText(sharedpreferences.getString(Currency, ""));
	       }
	    
	      b=(Button)
	    		  findViewById(R.id.btnAddSave);
	      b.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String n= name.getText().toString().trim();
				String t= tel.getText().toString().trim();
				String a= address.getText().toString().trim();
				String c= txcurrency.getText().toString().trim();
				 Editor editor = sharedpreferences.edit();
			      editor.putString(Name, n);
			      editor.putString(Phone, t);			     
			      editor.putString(Place, a);
			      editor.putString(Currency, c);
			      editor.commit();
			      Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_LONG).show();
				// TODO Auto-generated method stub
				
			}
		});
	}
}
