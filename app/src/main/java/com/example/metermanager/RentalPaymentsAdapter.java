package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.RentalPayments;
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


public class RentalPaymentsAdapter extends BaseAdapter implements Filterable {
	private Context context;
	private DatabaseHelperClass db;	
	private filterValues fValues;
	private List<RentalPayments> payments =new ArrayList<RentalPayments>();
	private List<RentalPayments> paymentsfilter =new ArrayList<RentalPayments>();
	
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public RentalPaymentsAdapter(Context context1,String startdate) {
			try{
			this.context=context1;
			 db=  DatabaseHelperClass.getInstance(context1);
				payments=db.GetRentalPayments(startdate);
				paymentsfilter=payments;
				//db.close();
			}catch(Exception e)
			{
				e.printStackTrace();
			}
			
		
	}
	public RentalPaymentsAdapter(Context context1) {
		try{
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance(context1);
			payments=db.GetRentalPayments();
			paymentsfilter=payments;
			//db.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	
}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return payments.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return payments.get(arg0);
	}
public void Remove(int id)
{
	payments.remove(id);
}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int id, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.rental_payments_view,parent,false);
				      
			}
			RentalPayments m =payments.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView5);
			
			idTextView.setText( Integer.toString(m.getPayment_id()));
			
			TextView t =(TextView)
					view.findViewById(R.id.textView6);		
			t.setText(m.getFullName());
			
			TextView paymentdate =(TextView)
					view.findViewById(R.id.textView7);		
			paymentdate.setText(fm.format(m.getPayment_date()));
			
			 TextView amount=(TextView)
						view.findViewById(R.id.textView8);		
			 amount.setText(df.format((m.getAmount())));	

			 TextView narration=(TextView)
						view.findViewById(R.id.textView9);		
			 narration.setText(m.getNarration());	
			 TextView period=(TextView)
						view.findViewById(R.id.textView12);		
			 period.setText(m.getPeriod());	
			 
			 
			}catch(Exception e)
			{
				Log.e("Error loading payments  data in listview",e.toString());
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
			payments=(ArrayList<RentalPayments>)results.values;
			notifyDataSetChanged();
			
		}
		
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
	         FilterResults results = new FilterResults();
	         if (constraint != null && constraint.length() > 0)
	         {
	        	   ArrayList<RentalPayments> filterList = new ArrayList<RentalPayments>();
	        	 
	        	   for(int i=0;i<paymentsfilter.size();i++)
	        	   {
	        		   if((paymentsfilter.get(i).getFullName().toUpperCase().contains(constraint.toString().toUpperCase())))
	        				   {
	        			   RentalPayments t= new RentalPayments();	        			   
	        			   Log.e("fName", paymentsfilter.get(i).getFirstName());
	        			   String fname=paymentsfilter.get(i).getFirstName();
	        			   String sname=paymentsfilter.get(i).getSurname();
	        			   t.setPayment_id(paymentsfilter.get(i).getPayment_id());
	        			   t.setFirstName(fname);
	        			   t.setSurname(sname);
	        			   t.setFullName(fname,sname);    
	        			   t.setPayment_date(paymentsfilter.get(i).getPayment_date());
	        			   t.setAmount(paymentsfilter.get(i).getAmount());
	   	        		   t.setNarration(paymentsfilter.get(i).getNarration());	   	        		 
	   	        		   t.setPeriod(paymentsfilter.get(i).getPeriod());
	        			    
		        			  filterList.add(t);
	        			   
	        				   }
	        	   }
	        	   results.count=filterList.size();
	        	   results.values=filterList;
	         }else
	         {
	        	 results.count=paymentsfilter.size();
	        	   results.values=paymentsfilter;
	         }
			return results;
		}
	}

}
