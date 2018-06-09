package com.example.metermanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class PowerPaymentsActivity extends Activity {
ListView list;
EditPaymentsAdapter1 adapter;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.invoices);
	Intent i= getIntent();
	int tenantid= i.getIntExtra("id", 0);
	adapter= new EditPaymentsAdapter1(getApplicationContext(), tenantid);
	list= (ListView)
			findViewById(R.id.invoices_list);
	list.setAdapter(adapter);
	
	
}

}
