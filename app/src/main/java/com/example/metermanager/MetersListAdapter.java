package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Meters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MetersListAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<Meters> meters =new ArrayList<Meters>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public MetersListAdapter(Context context1) {
		try{
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			meters=db.GetMeters();
			//db.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public int getCount() {
		return meters.size();
	}

	@Override
	public Object getItem(int position) {
	
		return meters.get(position);
	}

	@Override
	public long getItemId(int position) {
	
		return  position;
	}

	public void removeItem(int id)
	{
		 meters.remove(id);
	}
	@Override
	public View getView(int id, View view, ViewGroup parent) {
		
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.meter_list,parent,false);
				      
			}
			Meters m =meters.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.editText1);
			
			idTextView.setText( Integer.toString(m.get_id()));
			
			TextView serialnumber =(TextView)
					view.findViewById(R.id.editText2);		
			serialnumber.setText(m.getSerialNumber().toString());
			
			TextView meternumber =(TextView)
					view.findViewById(R.id.editText3);		
			meternumber.setText(m.getMeterNumber().toString());
			
			 TextView DateAdded =(TextView)
						view.findViewById(R.id.editText4);		
			 DateAdded .setText(fm.format((m.getDateAdded())));			 
			 
			 TextView DateInActive =(TextView)
						view.findViewById(R.id.editText5);
			 if(m.getDateInActive()!=null)
			 {
			 DateInActive.setText(fm.format(m.getDateInActive()));
			 }else
			 {
				 DateInActive.setText("In Use");
			 }
		
			}catch(Exception e)
			{
				Log.e("Error loading Meter  data in listview",e.toString());
			}
			return view;
		}

}
