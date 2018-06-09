package com.example.metermanager;

import java.util.ArrayList;

import android.telephony.SmsManager;

public class Utilities {
	
	
public void SendSms(String number, String message)	{
	
	
	
		SmsManager smsManager = SmsManager.getDefault();
	    ArrayList<String> parts = smsManager.divideMessage(message); 
	    smsManager.sendMultipartTextMessage(number, null, parts, null, null);
	}

public Utilities() {
	
}
	
}
