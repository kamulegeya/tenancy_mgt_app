package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.Tenants;
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

public class allTenants extends BaseAdapter implements Filterable {

	@SuppressWarnings("unused")
	private Context context;
	private filterValues fValues;
	DatabaseHelperClass db;		
	private List<Tenants> readings =new ArrayList<Tenants>();
	private List<Tenants> readingsfilter =new ArrayList<Tenants>();

	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
public allTenants(Context context1) {		
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
		readings=db.GetAllTenants();
		readingsfilter=readings;
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return readings.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return  readings.get(index);
	}

	@Override
	public long getItemId(int index) {
		// TODO Auto-generated method stub
		return index;
	}
    public void remove(int id)
    {
    	readings.remove(id);
    }
	
	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
		if (view ==null){
			LayoutInflater inflater=
					LayoutInflater.from(parent.getContext());
			      view =inflater.inflate(R.layout.tenants_listview,parent,false);
			      
		}
		Tenants tenant =readings.get(arg0);		
		TextView tenantTextView =(TextView)
				view.findViewById(R.id.textView9);
		
		tenantTextView.setText(tenant.getFirstName());
		
		TextView surnameTextView =(TextView)
				view.findViewById(R.id.textView10);		
		surnameTextView .setText(tenant.getSurName());
		TextView othernamesTextView =(TextView)
				view.findViewById(R.id.textView11);		
		othernamesTextView .setText(tenant.getOtherNames());
		
		 TextView mobile1 =(TextView)
					view.findViewById(R.id.textView12);		
		 mobile1.setText(tenant.getMobile1());
		 
		 TextView mobile2TextView =(TextView)
					view.findViewById(R.id.textView13);		
		 mobile2TextView.setText(tenant.getMobile2());	
			 
			 TextView dateaddedTextView =(TextView)
						view.findViewById(R.id.textView14);		
			 dateaddedTextView.setText(fm.format((tenant.getDateAdded())));
				 			
					 TextView inactivedateTextView =(TextView)
								view.findViewById(R.id.textView16);	
					 if(tenant.getDateInActive()!=null)
					 {
					 inactivedateTextView.setText(fm.format(tenant.getDateInActive()));
					 }else
					 {
						 inactivedateTextView.setText("Active Tenant");
					 }
					 
		           TextView tenantid =(TextView)
							view.findViewById(R.id.textView17);
		           String test= Integer.toString(tenant.get_id());
		           tenantid.setText(test);
		           
		  		 TextView emailTextView =(TextView)
		  					view.findViewById(R.id.textView19);		
		  		 emailTextView.setText(tenant.getEmail());	
		  			   
	
		}catch(Exception e)
		{
			Log.e(" in All Tenants listbox",e.toString());
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
			readings=(ArrayList<Tenants>)results.values;
			notifyDataSetChanged();
			
		}
		
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			// TODO Auto-generated method stub
	         FilterResults results = new FilterResults();
	         if (constraint != null && constraint.length() > 0)
	         {
	        	   ArrayList<Tenants> filterList = new ArrayList<Tenants>();
	        	 
	        	   for(int i=0;i<readingsfilter.size();i++)
	        	   {
	        		   String f=readingsfilter.get(i).getFirstName().toUpperCase();
	        		   String s=readingsfilter.get(i).getSurName().toUpperCase();
	        		  // Log.e("fName", s);
	        		   if((f.contains(constraint.toString().toUpperCase())||s.contains(constraint.toString().toUpperCase())))
	        				   
	        				   {
	        			   Tenants t= new Tenants();	        			   
	        			 
	        			   String fname=readingsfilter.get(i).getFirstName();
	        			   String sname=readingsfilter.get(i).getSurName();
	        			   t.setFirstName(fname);
	        			   t.setSurName(sname);       			     			   	        			
	   	        			
	        			     t.setMobile1(readingsfilter.get(i).getMobile1());
	        			     t.setMobile2(readingsfilter.get(i).getMobile2());
	        			     t.setDateAdded((readingsfilter.get(i).getDateAdded()));
	        			     t.setOtherNames(readingsfilter.get(i).getOtherNames());
		        			 t.setDateInActive( readingsfilter.get(i).getDateInActive());
		        			 t.setEmail(readingsfilter.get(i).getEmail());		        			 
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
