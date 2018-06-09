package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.Meters;
import meters.model.UnPaidInvoices;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OverDueInvoiceListAdapter extends BaseAdapter {
	private Context context;
	private DatabaseHelperClass db;		
	private List<UnPaidInvoices> invoices =new ArrayList<UnPaidInvoices>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public OverDueInvoiceListAdapter(Context context1,String mydate) {
		try{
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			invoices=db.GetUnPaidInvoices(mydate);
			//db.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return invoices.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return invoices.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	public void Remove(int id)
	{
		invoices.remove(id);
	}

	@Override
	public View getView(int id, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.invoices_list,parent,false);
				      
			}
			UnPaidInvoices m =invoices.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView9);
			
			idTextView.setText( Integer.toString(m.getInvoice_id()));
			
			TextView t =(TextView)
					view.findViewById(R.id.textView10);		
			t.setText(m.getFullName());
			
			TextView period =(TextView)
					view.findViewById(R.id.textView11);		
			period.setText(m.Period());
			
			 TextView dd=(TextView)
						view.findViewById(R.id.textView12);		
			 dd.setText(fm.format((m.getDatedue())));	
			 
			 TextView amount=(TextView)
						view.findViewById(R.id.textView13);		
			 amount.setText(df.format((m.getMonthly_rental())));	
			 			 
			 TextView room =(TextView)					   
					 view.findViewById(R.id.textView14);	
		      room.setText(m.getApartment().toString());
			}catch(Exception e)
			{
				Log.e("Error loading Meter  data in listview",e.toString());
			}
			return view;
	}

}
