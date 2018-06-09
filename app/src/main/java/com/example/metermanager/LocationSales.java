package com.example.metermanager;

import java.text.DecimalFormat;

import meter.manager.helper.DatabaseHelperClass;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class LocationSales extends Activity {
ListView list;
Cursor c;
DecimalFormat df = new DecimalFormat("#,###,###,###");		

DatabaseHelperClass db;
private AnnualInvoicesAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationsales);
		
		list=(ListView)
				findViewById(R.id.invoices_list);
		
		Intent i= getIntent();
		
		db= DatabaseHelperClass.getInstance(getApplicationContext());
		// TEST EXTRALS
		int id= i.getIntExtra("ID", 0);
		int[]myis=i.getIntArrayExtra("BLOCKID");
		if(myis==null)
		{
			// then it was invonked from Location Activity!
		adapter= new AnnualInvoicesAdapter(getApplicationContext(), id);
		try{
			c= db.getAnnualTotals(id);
			if(c.moveToNext())
			{
			TextView invoices= (TextView)
					 findViewById(R.id.txtInvoices);
			 double d= Double.parseDouble(c.getString(c.getColumnIndex("Invoices")));
			invoices.setText(df.format(d));
			 double dd= Double.parseDouble(c.getString(c.getColumnIndex("Amount")));
			TextView receipts= (TextView)
					 findViewById(R.id.txtReceipts);
			receipts.setText(df.format(dd));
			}
			c.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		
		
		}else
		{
			// was invoked from Blocklist Activity.
			// add a dummy 0 for sql to select only block is
			// remember the extra was an array
			// get first element...it is the block id
			int bid= myis[0];			
			adapter= new AnnualInvoicesAdapter(getApplicationContext(), bid,0);
			try{
				c= db.getAnnualTotals(bid);
				if(c.moveToNext())
				{
				TextView invoices= (TextView)
						 findViewById(R.id.txtInvoices);
				 double d= Double.parseDouble(c.getString(c.getColumnIndex("Invoices")));
				invoices.setText(df.format(d));
				 double dd= Double.parseDouble(c.getString(c.getColumnIndex("Amount")));
				TextView receipts= (TextView)
						 findViewById(R.id.txtReceipts);
				receipts.setText(df.format(dd));
				}
				c.close();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
		}
		list.setAdapter(adapter);
		
	}

}
