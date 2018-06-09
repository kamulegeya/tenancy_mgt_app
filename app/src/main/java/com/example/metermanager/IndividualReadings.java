package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.VMeterReadings;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class IndividualReadings extends BaseAdapter {
	
	@SuppressWarnings("unused")
	private Context context;
	private int id;
	
	private DatabaseHelperClass db;		
	private List<VMeterReadings> readings =new ArrayList<VMeterReadings>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	SimpleDateFormat fm =new SimpleDateFormat("dd/MM/yyyy");
public IndividualReadings(Context context1, int myid) {		
		this.context=context1;
		this.id=myid;
		 db=  DatabaseHelperClass.getInstance( context1);
		readings=db.GetIndividualReading(myid);
		//db.close();
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return readings.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return readings.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup parent) {
		try{
			
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.individual_reading,parent,false);			      
			
			}		
					
			VMeterReadings reading =readings.get(index);				
			
			TextView readingdateTextView =(TextView)
					view.findViewById(R.id.textView8);
			
			readingdateTextView.setText(fm.format(reading.getReadingDate()));
			
			TextView currentreadingTextView =(TextView)
					view.findViewById(R.id.textView9);		
			currentreadingTextView.setText( df.format((reading.getCurrentReading())));
			
			
			TextView previoustreadingdateTextView =(TextView)
					view.findViewById(R.id.textView10);		
			previoustreadingdateTextView.setText(fm.format((reading.getPreviousReadingDate())));
			
			 TextView previousmeterReading =(TextView)
						view.findViewById(R.id.textView11);		
			 previousmeterReading.setText(df.format((reading.getPreviousMeterReading())));
			 
			 TextView units =(TextView)
						view.findViewById(R.id.textView12);		
				 units.setText(df.format((((reading.getUnits())))));		
				 
				 TextView rate =(TextView)
							view.findViewById(R.id.textView13);		
					 rate.setText(Double.toString(reading.getRate()));						 
						 TextView amountTextView =(TextView)
									view.findViewById(R.id.textView14);		
							 amountTextView.setText(df.format((reading.getAmount()))); 
			 
		
			}catch(Exception e)
			{
				Log.e("Error loading data in listbox",e.getMessage());
			}
			return view;
	}
	
	
}
