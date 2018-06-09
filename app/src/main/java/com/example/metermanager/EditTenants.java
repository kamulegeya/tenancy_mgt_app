package com.example.metermanager;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.TenantList;
import meters.model.Tenants;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class EditTenants extends Activity implements OnItemSelectedListener{
	
private DatabaseHelperClass db;
Tenants tenant;
//list for dropdownlist
	private List<TenantList> readings =new ArrayList<TenantList>();
	//dropdown 
	Spinner spinner;
	Intent i;
     String fullname;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_tenants);
		 db=  DatabaseHelperClass.getInstance( this);
		 i= getIntent();
		 Tenants tenant= (Tenants)i.getSerializableExtra("Tenant");
		 fullname= tenant.FullName(tenant.getFirstName(),tenant.getSurName());
		 //Log.e("fname", fullname);
		try{
			readings=db.GetTenantIDs();
			 // create an array list of 
			 List<String> tenantlist= new  ArrayList<String>();
			 
			 for( TenantList m:readings)
			 {
				 tenantlist.add(m.getFullName());
			 }
			 //Creating adapter for spinner
			 spinner = (Spinner) findViewById(R.id.editTenants);
			 // add event listening		 
			 		     ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tenantlist);
		     dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// attaching data adapter to spinner
		spinner.setAdapter(dataAdapter);  		
		spinner.setOnItemSelectedListener(this);
		int pos = dataAdapter.getPosition(fullname);
		spinner.setSelection(pos);
		}catch(Exception e)
		{
			Log.e("Edit Tenanants Err", e.toString());
		}	
		
		
		
	}
	
	

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {	
		try{
		int pos = spinner.getSelectedItemPosition();
		// get the tenant selected  in the list
		TenantList tenants=(TenantList)readings.get(pos);
		// get the ID
		int id =tenants.get_id();	
		// pass the id as arguments 
		tenant=db.GetEditTenants(id);
		EditText textfirstname=(EditText)findViewById(R.id.editText1);		
		textfirstname.setText(tenant.getFirstName());
		EditText textsurname=(EditText)findViewById(R.id.editText2);
		textsurname.setText(tenant.getSurName());	
		EditText textother=(EditText)findViewById(R.id.editText3);
		textother.setText(tenant.getOtherNames());	
		EditText texmobile1=(EditText)findViewById(R.id.editText4);
		texmobile1.setText(tenant.getMobile1());
		EditText texmobile2=(EditText)findViewById(R.id.editText5);
		
		texmobile2.setText(tenant.getMobile2());
		
         EditText email=(EditText)findViewById(R.id.editText6);
		
		email.setText(tenant.getEmail());
		
		}catch (Exception e)
		{
			Log.e("Edit Tenants err",e.toString());
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	public void SaveChanges(View view)
	{
		try{
			int pos = spinner.getSelectedItemPosition();
			// get the tenant selected  in the list
			TenantList tenants=(TenantList)readings.get(pos);
			// get the ID
			int id =tenants.get_id();
			EditText fname= (EditText)findViewById(R.id.editText1);	
			String firstname =fname.getText().toString();
			EditText sname= (EditText)findViewById(R.id.editText2);	
			String surname =sname.getText().toString();
			EditText oname= (EditText)findViewById(R.id.editText3);	
			String othername =oname.getText().toString();			
			EditText m1= (EditText)findViewById(R.id.editText4);	
			String mobile1=m1.getText().toString();
			EditText m2= (EditText)findViewById(R.id.editText5);	
			EditText email= (EditText)findViewById(R.id.editText6);				
			String myemail=email.getText().toString();
			String mobile2 =m2.getText().toString();			
			// is there some data?...it can be left null
			if(myemail.length()!=0)
			{
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(myemail).matches() ) {
			    email.setError("Invalid Email");
			    email.requestFocus();
			    return;
				}
			}
			Tenants t= new Tenants();
			t.set_id(id);
			t.setFirstName(firstname);
			t.setSurName(surname);
			t.setOtherNames(othername);
			t.setMobile1(mobile1);
			t.setMobile2(mobile2);
			t.setEmail(myemail);
			db.EditTenantRecords(t);	
			// inform user
			Toast.makeText(getApplicationContext(), "Edits Saved", Toast.LENGTH_LONG).show();
				
			} catch(Exception e)
			{
				Log.e("Adding Tenant Err", e.toString());
			}
		
		
	}
	public void CancelAdd(View v)
	{
		this.finish();
	}
	
	
}
