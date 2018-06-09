package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class AnnualFigures extends Activity  {
   private AnnualInvoicesAdapter p;
   ListView list;
   Cursor c;
   Spinner spinner;
   TextView v1;
   TextView v2;
   
   @SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
   ArrayAdapter<String> dataAdapter ;
   List<String> years= new ArrayList<String>();
   DecimalFormat df = new DecimalFormat("#,###,###,###");
   private DatabaseHelperClass db;
	@SuppressLint("UseValueOf")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.annualfigures);
		db=  DatabaseHelperClass.getInstance(this);
		list=(ListView)
				findViewById(R.id.invoices_list);
		spinner = (Spinner)
				findViewById(R.id.spyear);
		c= db.getYear();
		if( c.moveToNext())
		{
			do
			{
				years.add(c.getString(c.getColumnIndex("year")));
				
			}while( c.moveToNext());
			
		}
		c.close();
		   dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,years);
		 Date myStartDate =new Date();			
		String s = fm.format(myStartDate).substring(0, 4);
		Character t= new Character((char) 39);
		String ss= t+ s + t;	
		spinner.setAdapter(dataAdapter);
		p= new AnnualInvoicesAdapter(this, ss);
		list.setAdapter(p);	
		// updatevalues(ss);
		spinner.setOnItemSelectedListener( new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				String y= arg0.getItemAtPosition(arg2).toString();
				Character t= new Character((char) 39);
				String ss= t+ y + t;	
				//Log.e("y", y);
				//if all selected....bring all data not a for a perticular year
				if(ss.equals("\'All\'"))
					
				{
					p= new AnnualInvoicesAdapter( AnnualFigures.this);
					list.setAdapter(p);
					 updatevalues();	
				}
				else
				{
				p= new AnnualInvoicesAdapter( AnnualFigures.this, ss);
				list.setAdapter(p);
				 updatevalues(ss);
				}
				//Log.e("String", ss);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	private void updatevalues(String yy)
	{
		v1= (TextView)
				findViewById(R.id.txtInvoices);
		v2= (TextView)
				findViewById(R.id.txtReceipts);
		c= db.getAnnualTotals(yy);
		if (c.moveToNext())
		{
			
			v1.setText(df.format(Double.parseDouble(c.getString(c.getColumnIndex("I")))));
			v2.setText(df.format(Double.parseDouble(c.getString(c.getColumnIndex("A")))));
			
		}
			c.close();	
	}
	private void updatevalues()
	{
		v1= (TextView)
				findViewById(R.id.txtInvoices);
		v2= (TextView)
				findViewById(R.id.txtReceipts);
		c= db.getAnnualTotals();
		if (c.moveToNext())
		{
			
			v1.setText(df.format(Double.parseDouble(c.getString(c.getColumnIndex("I")))));
			
			v2.setText(df.format(Double.parseDouble(c.getString(c.getColumnIndex("A")))));
			
		}
			c.close();	
	}
}
