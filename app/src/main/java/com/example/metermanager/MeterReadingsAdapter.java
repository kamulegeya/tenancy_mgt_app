package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.VReadings;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MeterReadingsAdapter extends BaseAdapter {
	private DatabaseHelperClass db;	
	List<VReadings> meterreadings = new ArrayList<VReadings>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");	
	SimpleDateFormat fm1 =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
    private Context ct;
	public MeterReadingsAdapter(Context cnt) {
		this.ct=cnt;
		db= DatabaseHelperClass.getInstance(cnt);
		meterreadings= db.getReadings();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return meterreadings.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return meterreadings.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		if (view ==null){
			LayoutInflater inflater=
					LayoutInflater.from(parent.getContext());
			      view =inflater.inflate(R.layout.readings,parent,false);
		}
		
		VReadings t= (VReadings)meterreadings.get(arg0);
		
		
		TextView v= (TextView)
				  view.findViewById(R.id.amount);
		v.setText(df.format(t.getAmount()));

		//Log.e("amount", df.format(t.getAmount()));
		TextView v1= (TextView)
				  view.findViewById(R.id.units_used);
		v1.setText(df.format(t.getReading()));
		
		
		TextView v2= (TextView)
				  view.findViewById(R.id.unit_rate);
		v2.setText(df.format(t.getRate()));
		
		TextView v3= (TextView)
				  view.findViewById(R.id.reading_date);
		v3.setText(fm1.format(t.getReadingDate()));		
		
		return view;
	}

}
