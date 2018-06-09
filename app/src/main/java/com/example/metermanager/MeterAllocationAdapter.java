package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.MeterAllocation;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class MeterAllocationAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	DatabaseHelperClass db;		
	private List<MeterAllocation> readings =new ArrayList<MeterAllocation>();	
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
		public MeterAllocationAdapter(Context context1) {
				this.context = context1;
				 db=  DatabaseHelperClass.getInstance( context1);
					readings=db.GetMeterAllocation();
					//close it
				 				//db.close();
		
	}
	@Override
	
	public int getCount() {
		// TODO Auto-generated method stub
		return readings.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return readings.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
 public void Remove(int id)
 {
	 readings.remove(id);
 }
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.meters_allocations_details,parent,false);
				      
			}
			MeterAllocation  allocation =readings.get(position);		
			TextView systemidTextView =(TextView)
					view.findViewById(R.id.textView5);
			
			systemidTextView.setText(Integer.toString(allocation.get_id()));
			
			TextView tenantTextView =(TextView)
					view.findViewById(R.id.textView6);
			
			tenantTextView .setText(allocation.getFullName());
			
			TextView meterTextView =(TextView)
					view.findViewById(R.id.textView7);		
			meterTextView  .setText(allocation.getmeternumber());
			
			 TextView sDate =(TextView)
						view.findViewById(R.id.textView8);		
			 sDate.setText(fm.format(allocation.getStartDate()));
			 TextView reading =(TextView)
						view.findViewById(R.id.textView9);		
			 reading.setText(df.format((allocation.getReading())))	; 
			 TextView endreading =(TextView)
						view.findViewById(R.id.textView14);		
			 endreading.setText(df.format((allocation.getEndreading()))	);        
			 TextView enddate =(TextView)
						view.findViewById(R.id.textView13);	
			 if(allocation.getEnddate()==null)
			 {
				 enddate.setText("Current user");
				 
			 }else
			 {
				 enddate.setText(fm.format(allocation.getEnddate()));
			 }
			 TextView endreadingdate =(TextView)
						view.findViewById(R.id.textView15);	
			 
			 endreadingdate.setText(fm.format(allocation.getEndreadingdate()));
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			return view;
		}

}
