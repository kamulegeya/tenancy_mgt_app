package com.example.metermanager;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSettingsClass {
	
	 static SharedPreferences sharedpreferences;
	    private   Context cnxt;	 
	    static final String Name = "nameKey"; 
	    static final String Phone = "phoneKey"; 	   
	   static final String Place = "placeKey";
	   static final String Currency = "CurrencyKey";
	 public  static final String MyPREFERENCES = "MyPrefs" ;
	public UserSettingsClass(Context c) {
		
		this.cnxt=c;
		
	}
	
	public  String name()
	{
		 sharedpreferences =cnxt.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 String username="";
		 if (sharedpreferences.contains(Name))
	      {
	          username=sharedpreferences.getString(Name, "");

	      }
		return username;
		
	} 
	public  String phone()
	{
		 sharedpreferences =cnxt.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 String phone="";
		 if (sharedpreferences.contains(Phone))
	      {
	          phone=sharedpreferences.getString(Phone, "");

	      }
		return phone;
		
	}
	public  String place()
	{
		 sharedpreferences =cnxt.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 String address="";
		 if (sharedpreferences.contains(Place))
	      {
	          address=sharedpreferences.getString(Place, "");

	      }
		return address;
		
	}
	public  String currency()
	{
		 sharedpreferences =cnxt.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		 String address="";
		 if (sharedpreferences.contains(Currency))
	      {
	          address=sharedpreferences.getString(Currency, "");

	      }
		return address;
		
	}
}
