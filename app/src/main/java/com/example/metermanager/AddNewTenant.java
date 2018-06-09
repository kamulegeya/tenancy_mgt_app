package com.example.metermanager;

import java.text.SimpleDateFormat;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Tenants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddNewTenant extends Activity {
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
	Context context;		
	private DatabaseHelperClass db;
	EditText fname;
	 EditText sname;
	 EditText oname;
	 EditText m1;
	 EditText m2;
	 EditText email;
	 Button contacts;
	 final int RQS_PICKCONTACT = 1;
	  private Uri uriContact;
	   
		
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_new_tenant);
	 fname= (EditText)findViewById(R.id.editText1);	
	  sname= (EditText)findViewById(R.id.editText2);		
	  oname= (EditText)findViewById(R.id.editText3);		
	  m1= (EditText)findViewById(R.id.editText4);	
	  m2= (EditText)findViewById(R.id.editText5);	
	  email=(EditText)findViewById(R.id.editText6);	
	  contacts=(Button)findViewById(R.id.contact);

	db=   DatabaseHelperClass.getInstance( this);
	 context = this.getApplicationContext();
	 Button b= (Button)
			 findViewById(R.id.cancel);
	 b.setOnClickListener( new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			fname.setText("");
			sname.setText("");
			oname.setText("");
			m1.setText("0");
			m2.setText("0");
			
		}
	});
	 contacts.setOnClickListener( new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			 // Start activity to get contact
		    //uriContact = ContactsContract.Contacts.CONTENT_URI;
		    Intent intentPickContact = new Intent(Intent.ACTION_PICK);
		   intentPickContact.setType(CommonDataKinds.Phone.CONTENT_TYPE);   
		  	
		    startActivityForResult(intentPickContact, RQS_PICKCONTACT);
			
		}
	});
}


public void AddNew(View view)
{
	try{
		String firstname =fname.getText().toString();
	String surname =sname.getText().toString();
		String othername =oname.getText().toString();	
		String mobile1=m1.getText().toString();
		String mobile2 =m2.getText().toString();	
	if(mobile1.length()==0  &&  mobile2.length()==0) 
	{
		// get a toast						
					CharSequence msg ="You must provide atleast one mobile number";			
					int duration =Toast.LENGTH_LONG;
					Toast toast = Toast.makeText(context, msg, duration);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					return;
	}
	
	if(firstname.length()==0 && surname.length()==0)
		
	{
		// get a toast						
		CharSequence msg ="You must enter a first name or a surname";			
		int duration =Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(context, msg, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		return;
		
	}
	if (firstname.length()==0 )
	{
		firstname="";
	}
		else if(surname.length()==0)
		{
			surname="";
			
		}
		else if(othername.length()==0)
		{
			othername="";
		}
	
		else if(mobile1.length()==0)
		{
			mobile1="00";
		}
		else if(mobile2.length()==0)
		{
			mobile2="00";
		}
			
	// check email if null
	String  myemail=email.getText().toString().trim();
	if(myemail.length()!=0)
	{
	if (!android.util.Patterns.EMAIL_ADDRESS.matcher(myemail).matches() ) {
	    email.setError("Invalid Email");
	    email.requestFocus();
	    return;
	}
	}
	Tenants t= new Tenants();
	t.setFirstName(firstname);
	t.setSurName(surname);
	t.setOtherNames(othername);
	t.setMobile1(mobile1);
	t.setMobile2(mobile2);
	t.setEmail(myemail);
	db.AddTenant( t);	
	// clear textboxes
	 fname.getText().clear();
	 sname.getText().clear();
	 oname.getText().clear();
	 m1.getText().clear();
	 m2.getText().clear();
     email.getText().clear();
	} catch(Exception e)
	{
		Log.e("Adding Tenant Err", e.toString());
	}

	
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) 
{
    if (resultCode == RESULT_OK) {
    	if(requestCode==RQS_PICKCONTACT) {
    		uriContact=data.getData();
    		retrieveContactName(uriContact);
            retrieveContactNumber(uriContact);
            retrieveContactEmail(uriContact);
           
    		
    	}
       
    }
}
private void retrieveContactNumber(Uri uri) {	 
	
    String[] projection = new String[]{CommonDataKinds.Phone.NUMBER};
	
    Cursor cursor = getContentResolver().query(uri, projection,
            null, null, null);
    // If the cursor returned is valid, get the phone number
    if (cursor != null && cursor.moveToFirst()) {
        int numberIndex = cursor.getColumnIndex(CommonDataKinds.Phone.NUMBER);
      
        
        String number = cursor.getString(numberIndex);
        //Log.e("number", number);
        m1.setText(number);
        cursor.close();
    }
        
}

private void retrieveContactEmail(Uri uri) {	 
	
    String[] projection = new String[]{CommonDataKinds.Email.ADDRESS};
    
    Cursor cursor = getContentResolver().query(uri, projection,
            null, null, null);
    // If the cursor returned is valid, get the phone number
    if (cursor != null && cursor.moveToFirst()) {
        int numberIndex = cursor.getColumnIndex(CommonDataKinds.Email.ADDRESS);      
        
        String number = cursor.getString(numberIndex);
        Log.e("email", number);
        m1.setText(number);
        cursor.close();
    }
        
}

private void retrieveContactName(Uri uri) {

	 String[] projection = new String[]{CommonDataKinds.Phone.DISPLAY_NAME};
	    Cursor cursor = getContentResolver().query(uri, projection,
	            null, null, null);
	    // If the cursor returned is valid, get the phone number
	    if (cursor != null && cursor.moveToFirst()) {
	        int numberIndex = cursor.getColumnIndex(CommonDataKinds.Phone.DISPLAY_NAME);
	        String number = cursor.getString(numberIndex);
	        //Log.e("name", number);
	        fname.setText(number);
	        cursor.close();
	    }
}

}
