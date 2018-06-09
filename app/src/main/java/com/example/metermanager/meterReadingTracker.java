package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.VMeterReadings;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class meterReadingTracker extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	DatabaseHelperClass db;	
	private List<VMeterReadings> readings =new ArrayList<VMeterReadings>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
public meterReadingTracker(Context context1) {		
		this.context=context1;
		DatabaseHelperClass db=  DatabaseHelperClass.getInstance(context1);
		readings=db.GetDetailedReading();
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
		return  readings.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
		if (view ==null){
			LayoutInflater inflater=
					LayoutInflater.from(parent.getContext());
			      view =inflater.inflate(R.layout.get_readings,parent,false);
			      
		}
		VMeterReadings reading =readings.get(arg0);		
		TextView tenantTextView =(TextView)
				view.findViewById(R.id.textView9);
		
		tenantTextView.setText(reading.getTenant());
		
		TextView readingdateTextView =(TextView)
				view.findViewById(R.id.textView10);		
		readingdateTextView.setText(fm.format(((reading.getReadingDate()))));
		TextView currentreadingTextView =(TextView)
				view.findViewById(R.id.textView11);		
		 currentreadingTextView.setText(df.format((reading.getCurrentReading())));
		
		 TextView previousReadingDate =(TextView)
					view.findViewById(R.id.textView12);		
		 previousReadingDate.setText(fm.format(reading.getPreviousReadingDate()));
		 TextView previousreadingTextView =(TextView)
					view.findViewById(R.id.textView13);		
			 previousreadingTextView.setText(df.format(((reading.getPreviousMeterReading()))));		
			 
			 TextView unitsTextView =(TextView)
						view.findViewById(R.id.textView14);		
				 unitsTextView.setText(Double.toString(reading.getUnits()));		
				 TextView rateTextView =(TextView)
							view.findViewById(R.id.textView15);		
					 rateTextView.setText(df.format((reading.getRate()))); 
					 TextView amountTextView =(TextView)
								view.findViewById(R.id.textView16);		
						 amountTextView.setText(df.format((reading.getAmount()))); 
		 
	
		}catch(Exception e)
		{
			Log.e("Error loading",e.toString());
		}
		return view;
	}
	
	
	  
}
