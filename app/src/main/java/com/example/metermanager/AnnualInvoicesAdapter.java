package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.AnnualTotals;
import meters.model.ApartmentInvoices;
import meters.model.ApartmentPayments;
import meters.model.PowerPayments;
import meters.model.VReadings;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AnnualInvoicesAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	List<AnnualTotals> c= new ArrayList<AnnualTotals>();
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");		
	
	public AnnualInvoicesAdapter(Context context1,String  myyear) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			c=db.getAnnualInvoices(myyear);
			//Log.e("year", myyear);
			
	}
	public AnnualInvoicesAdapter(Context context1) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			c=db.getAnnualInvoices();
			
			
	}
	
	public AnnualInvoicesAdapter(Context context1,  int myid) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			c=db.getLocationBlockSales(myid);
			//Log.e("year", myyear);
			
	}
	public AnnualInvoicesAdapter(Context context1,  int myid,int locationid) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			c=db.getLocationBlockSales(myid,locationid);
			//Log.e("year", myyear);
			
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return c.size();
	}

	
	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return c.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.annualsales,parent,false);
				      
			}
				
			AnnualTotals l= c.get(arg0);
			
			TextView roomTextView =(TextView)
					view.findViewById(R.id.txtV4);		
			roomTextView.setText(l.getNumber());
			//Log.e("room11111",l.getRoom());

			TextView invoicesTextView =(TextView)
					view.findViewById(R.id.txtV5);		
			invoicesTextView.setText(df.format(l.getInvoices()));
			//Log.e("Invoices", df.format(l.getInvoices()));
			
			TextView amountTextView =(TextView)
					view.findViewById(R.id.txtV6);		
			amountTextView.setText(df.format(l.getReceipts()));							
			
		
			}catch(Exception e)
			{
				Log.e("Error  listbox",e.toString());
			}
			return view;
	}
}
