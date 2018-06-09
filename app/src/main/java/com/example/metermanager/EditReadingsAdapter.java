package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.MeterReading;
import meters.model.VMeterReadings;
import meters.model.VReadings;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EditReadingsAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<VReadings> readings =new ArrayList<VReadings>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
		
	public EditReadingsAdapter( Context context1, String myDate) {
		this.context=context1;
	 db=  DatabaseHelperClass.getInstance( context1);
		readings=db.GetMeterReadings(myDate);
		
			
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
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.edit_reading_details,parent,false);
				      
			}
			VReadings reading =readings.get(arg0);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView6);
			
			idTextView.setText( Integer.toString(reading.get_id()));
			
			TextView readingdateTextView =(TextView)
					view.findViewById(R.id.textView7);		
			readingdateTextView.setText(fm.format(((reading.getReadingDate()))));
			
			TextView readingTextView =(TextView)
					view.findViewById(R.id.textView8);		
			 readingTextView.setText(df.format((reading.getReading())));
			
			 TextView Rate =(TextView)
						view.findViewById(R.id.textView9);		
			Rate.setText(df.format(reading.getRate()));
			
			 TextView meterTextView =(TextView)
						view.findViewById(R.id.textView10);		
				 meterTextView.setText(reading.getMeter_number().toString());
				
			 
		
			}catch(Exception e)
			{
				Log.e("Error loading data in listbox",e.toString());
			}
			return view;
		}
		
	

}
