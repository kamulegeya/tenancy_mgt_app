package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class InActiveTenancyList extends BaseAdapter {

	@SuppressWarnings("unused")
	private Context context;
	DatabaseHelperClass db;		
	private List<ActiveTenancy> tenancy =new ArrayList<ActiveTenancy>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public InActiveTenancyList(Context context1) {		
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
		tenancy=db.GetInActiveTenancy();
		//close it
		//db.close();
	}
	@Override
	public int getCount() {
		return tenancy.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return tenancy.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return   arg0;
	}
public void remove(int id)
{
	tenancy.remove(id);
}
	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.tenancy_list,parent,false);
				      
			}
			ActiveTenancy reading =tenancy.get(arg0);		
			TextView tenantTextView =(TextView)
					view.findViewById(R.id.textView9);
			
			tenantTextView.setText(reading.getFullName());
			
			TextView mobileTextView =(TextView)
					view.findViewById(R.id.textView10);		
			mobileTextView .setText(reading.getMobile());
			TextView mobile2TextView =(TextView)
					view.findViewById(R.id.textView11);		
			mobile2TextView .setText(reading.getMobile2());
			
			 TextView sdate =(TextView)
						view.findViewById(R.id.textView12);		
			 sdate.setText(fm.format(reading.getStart_date()));			 
				 
				 TextView rentTextView =(TextView)
							view.findViewById(R.id.textView14);		
				 rentTextView.setText(df.format(reading.getMonthly_rental()));
					 			
						 TextView roomTextView =(TextView)
									view.findViewById(R.id.textView16);		
						 roomTextView.setText(reading.getApartment());
						 
						 TextView blockTextView =(TextView)
									view.findViewById(R.id.textView20);		
						 blockTextView.setText(reading.getBlock());
						 
						 TextView lTextView =(TextView)
									view.findViewById(R.id.textView21);		
						 lTextView.setText(reading.getLocation());
			           TextView tenantid =(TextView)
								view.findViewById(R.id.textView17);
			           String test= Integer.toString(reading.get_id());
			           tenantid.setText(test);
			           TextView enddateTextView =(TextView)
								view.findViewById(R.id.textView13);		
					 enddateTextView.setText(fm.format(reading.getEnd_date()));
					  
		
			}catch(Exception e)
			{
				Log.e("Error loading data in All Tenants listbox",e.toString());
			}
			return view;
		
		
	}

}
