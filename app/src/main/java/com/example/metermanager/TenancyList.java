package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
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

public class TenancyList extends BaseAdapter implements Filterable {

	@SuppressWarnings("unused")
	private Context context;
	private filterValues fValues;
	DatabaseHelperClass db;		
	private List<ActiveTenancy> tenancy =new ArrayList<ActiveTenancy>();
	private List<ActiveTenancy> tenancyfilter =new ArrayList<ActiveTenancy>();	
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	public TenancyList(Context context1) {		
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
		tenancy=db.GetActiveTenancy();
		tenancyfilter=tenancy;
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
			 
			 TextView enddateTextView =(TextView)
						view.findViewById(R.id.textView13);		
			 enddateTextView.setText(fm.format(reading.getEnd_date()));
				 
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
			          
		
			}catch(Exception e)
			{
				Log.e("Error loading listbox",e.toString());
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
			tenancy=(ArrayList<ActiveTenancy>)results.values;
			notifyDataSetChanged();
			
		}
		
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
	         FilterResults results = new FilterResults();
	         if (constraint != null && constraint.length() > 0)
	         {
	        	   ArrayList<ActiveTenancy> filterList = new ArrayList<ActiveTenancy>();
	        	 
	        	   for(int i=0;i<tenancyfilter.size();i++)
	        	   {
	        		   if((tenancyfilter.get(i).getFullName().toUpperCase().contains(constraint.toString().toUpperCase())))
	        				   {
	        			   ActiveTenancy t= new ActiveTenancy();	        			   
	        			  // Log.e("fName", tenancyfilter.get(i).getFirstName());
	        			   String fname=tenancyfilter.get(i).getFirstName();
	        			   String sname=tenancyfilter.get(i).getSurname();
	        			   t.setFirstName(fname);
	        			   t.setSurname(sname);
	        			    t.setFullName(fname,sname);    			   	        			
	   	        			
	        			     t.setMobile(tenancyfilter.get(i).getMobile());
	        			     t.setMobile2(tenancyfilter.get(i).getMobile2());
	        			     t.setStart_date(tenancyfilter.get(i).getStart_date());
	        			     t.setEnd_date(tenancyfilter.get(i).getEnd_date());
		        			 t.setMonthly_rental( tenancyfilter.get(i).getMonthly_rental());
		        			 t.setApartment(tenancyfilter.get(i).getApartment());
		        			 t.setBlock(tenancyfilter.get(i).getBlock());
		        			 t.setLocation(tenancyfilter.get(i).getLocation());
		        			  filterList.add(t);
	        			   
	        				   }
	        	   }
	        	   results.count=filterList.size();
	        	   results.values=filterList;
	         }else
	         {
	        	 results.count=tenancyfilter.size();
	        	   results.values=tenancyfilter;
	         }
			return results;
		}
	}

}
