package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
import meters.model.Meters;
import meters.model.UnPaidInvoices;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UnInvoicedListAdapter extends BaseAdapter {
	private Context context;
	private DatabaseHelperClass db;		
	private List<ActiveTenancy> invoices =new ArrayList<ActiveTenancy>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public UnInvoicedListAdapter(Context context1,String myd) {
		try{
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			invoices=db.getUnInvoicedRooms(myd);
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

	public List<ActiveTenancy> getList()
	{
		return invoices;
		
	}
	@Override
	public View getView(int id, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.un_invoiced_list,parent,false);
				      
			}
			ActiveTenancy m =invoices.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView4);
			
			idTextView.setText( (m.getFullName()));
			
			TextView t =(TextView)
					view.findViewById(R.id.textView5);		
			t.setText(m.getApartment());
			
			TextView lastdate =(TextView)
					view.findViewById(R.id.textView6);	
			String s=null;
			if(m.getLastInvoiceDate()!=null)
			{
				s= fm.format(m.getLastInvoiceDate());
				Log.e("t", s);
			}else
			{
				s= "Never Invoiced";
			}
			lastdate.setText(s);
			//Log.e("t", s);
			}catch(Exception e)
			{
				Log.e("Error loading Meter  data in listview",e.toString());
			}
			return view;
	}

}
