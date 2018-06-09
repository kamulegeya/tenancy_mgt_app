package com.example.metermanager;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class InActiveTenancy extends Activity {
 
 InActiveTenancyList tenants;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inactivetenants);
		//test();
			
				tenants= new InActiveTenancyList(this);
			ListView listview =(ListView)findViewById(R.id.lstalltenants);		
				listview.setAdapter(tenants);	
	}

}
