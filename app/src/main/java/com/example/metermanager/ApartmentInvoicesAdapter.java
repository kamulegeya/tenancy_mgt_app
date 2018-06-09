package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ApartmentInvoices;
import meters.model.ApartmentPayments;
import meters.model.PowerPayments;
import meters.model.VReadings;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ApartmentInvoicesAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<ApartmentInvoices> payments =new ArrayList<ApartmentInvoices>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	
	
	public ApartmentInvoicesAdapter(Context context1,int id) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			payments=db.getApartmentInvoices(id);
			
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return payments.size();
	}

	
	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return payments.get(index);
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
				      view =inflater.inflate(R.layout.apartmentinvoices,parent,false);
				      
			}
			ApartmentInvoices reading =payments.get(arg0);				
			
			TextView tenantTextView =(TextView)
					view.findViewById(R.id.textView5);		
			tenantTextView.setText(reading.FullName(reading.getFname(),reading.getSname()));
			

			TextView sdTextView =(TextView)
					view.findViewById(R.id.textView6);		
			sdTextView.setText(fm.format(reading.getStartdate()));
			
			
			TextView edTextView =(TextView)
					view.findViewById(R.id.textView7);		
			edTextView.setText(fm.format(reading.getEnddate()));
		
			
			 TextView amount= (TextView)
					 view.findViewById(R.id.textView8);
			 amount.setText(df.format(reading.getRentalamout()));
		
			}catch(Exception e)
			{
				Log.e("Error loading data in listbox",e.toString());
			}
			return view;
	}
}
