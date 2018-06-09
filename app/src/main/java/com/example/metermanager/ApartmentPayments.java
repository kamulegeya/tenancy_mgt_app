package com.example.metermanager;

import java.text.DecimalFormat;

import meter.manager.helper.DatabaseHelperClass;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ApartmentPayments extends Activity {
   private ApartmentPaymentAdapter p;
   ListView list;
   DecimalFormat df = new DecimalFormat("#,###,###,###");
   private DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apartmentpayments_total);
		db=  DatabaseHelperClass.getInstance(this);
		list=(ListView)
				findViewById(R.id.invoices_list);
		Intent i= getIntent();
		
		int id= i.getIntExtra("ID", 0);
		
		p= new ApartmentPaymentAdapter(this, id);
		list.setAdapter(p);
		double d=0;		
		TextView text= (TextView)
				findViewById(R.id.txtview2);
		Cursor c= db.getTotalReceipts(id);
		if(c.moveToNext())
		{
			 d= Double.parseDouble(c.getString(c.getColumnIndex("Amount")));
		}
		text.setText(df.format(d));
		c.close();
	}
}
