package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.RentalPaymentStatus;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;


public class RentalPaymentsStatusAdapter extends BaseAdapter implements Filterable {

	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<RentalPaymentStatus> invoices =new ArrayList<RentalPaymentStatus>();
	private List<RentalPaymentStatus> readingsfilter =new ArrayList<RentalPaymentStatus>();
	private filterValues fValues;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public RentalPaymentsStatusAdapter(Context context1) {
		try{
		this.context=context1;
		 db= DatabaseHelperClass.getInstance( context1);
			invoices=db.GetRentalPaymentsStatus();
			readingsfilter=invoices;
			//db.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public RentalPaymentsStatusAdapter(Context context1, int tid) {
		try{
		this.context=context1;
		 db= DatabaseHelperClass.getInstance( context1);
			invoices=db.GetRentalPaymentsStatus(tid);
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
				      view =inflater.inflate(R.layout.rental_payments_status,parent,false);
				      
			}
			RentalPaymentStatus m =invoices.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView9);
			
			idTextView.setText(m.getFullName());
			
			TextView startdate =(TextView)
					view.findViewById(R.id.textView10);		
			startdate.setText(fm.format(m.getStart_date()));
			
			TextView endadte =(TextView)
					view.findViewById(R.id.textView11);		
			endadte.setText(fm.format(m.getEnd_date()));
			
			TextView dd =(TextView)
					view.findViewById(R.id.textView12);		
			dd.setText(fm.format(m.getDuedate()));
		
			
			TextView datepaid =(TextView)
					view.findViewById(R.id.textView13);	
			if(m.getDatepaid()==null)
			{
				datepaid.setText("Not Paid");
				
			}else
			{
			
			datepaid.setText(fm.format(m.getDatepaid()));
			}			
			
			
			
			 TextView invoiceAmount=(TextView)
						view.findViewById(R.id.textView14);		
			 invoiceAmount.setText(df.format(m.getMonthly_rental()));
			 
			 
			 TextView amountpaid=(TextView)
						view.findViewById(R.id.textView16);		
			 amountpaid.setText(df.format((m.getAmoutpaid())));	
			 			 
			 TextView room =(TextView)					   
					 view.findViewById(R.id.textView18);	
		      room.setText(m.getApartment().toString());
	 			 
				 TextView days =(TextView)					   
						 view.findViewById(R.id.textView20);
				 if(Math.round(m.getDatelate())>=5)
				 {
					 days.setTextColor((context.getResources().getColor(R.color.red)));
				 }else
				 {
					 days.setTextColor((context.getResources().getColor(R.color.green)));
				 }
				 
				 String mydays=Long.toString(m.getDatelate())+ " "+ " Days";
			      days.setText(mydays);
			      
			}catch(Exception e)
			{
				Log.e("Error loading Meter  data in listview",e.toString());
			}
			return view;
	}
	@Override
	public Filter getFilter() {
		  if (fValues == null) {
			  fValues = new filterValues();
	        }
	        return fValues;
	}
private class filterValues  extends Filter {
		
		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// TODO Auto-generated method stub
			invoices=(ArrayList<RentalPaymentStatus>)results.values;
			notifyDataSetChanged();
			
		}
		
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
	         FilterResults results = new FilterResults();
	         if (constraint != null && constraint.length() > 0)
	         {
	        	   ArrayList<RentalPaymentStatus> filterList = new ArrayList<RentalPaymentStatus>();
	        	 
	        	   for(int i=0;i<readingsfilter.size();i++)
	        	   {
	        		   String f=readingsfilter.get(i).getFullName().toUpperCase();
	        		   //String s=readingsfilter.get(i).getSurName().toUpperCase();
	        		  // Log.e("fName", s);
	        		   if((f.contains(constraint.toString().toUpperCase())))
	        				   
	        				   {
	        			   RentalPaymentStatus t= new RentalPaymentStatus();        			   
	        			 String fname=readingsfilter.get(i).getFirstName();
	        			   String sname=readingsfilter.get(i).getSurname();
	        			   t.setFirstName(fname);
	        			   t.setSurname(sname);       			     			   	        			
	   	        		    t.setFullName(fname, sname);
	   	        		    t.setStart_date(readingsfilter.get(i).getStart_date());
	   	        		    t.setEnd_date(readingsfilter.get(i).getEnd_date());
	   	        		    t.setDuedate(readingsfilter.get(i).getDuedate());
	   	        		    t.setDatepaid(readingsfilter.get(i).getDatepaid());
	   	        		    t.setMonthly_rental(readingsfilter.get(i).getMonthly_rental());
	   	        		    t.setAmoutpaid(readingsfilter.get(i).getAmoutpaid());
	   	        		    t.setApartment(readingsfilter.get(i).getApartment());	   	        		   
		        			  filterList.add(t);
	        			   
	        				   }
	        	   }
	        	   results.count=filterList.size();
	        	   results.values=filterList;
	         }else
	         {
	        	 results.count=readingsfilter.size();
	        	   results.values=readingsfilter;
	         }
			return results;
		}
	}

}
