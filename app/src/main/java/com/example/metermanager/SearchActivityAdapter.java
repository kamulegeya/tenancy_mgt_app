package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.SearchResults;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SearchActivityAdapter extends BaseAdapter {
private List<SearchResults > results= new ArrayList<SearchResults >();
	 DatabaseHelperClass db;
	Context context;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	 @SuppressLint("SimpleDateFormat")
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy");
	
	public SearchActivityAdapter( Context context,String qrysting) {
		db= DatabaseHelperClass.getInstance(context);
		results=db.GetSearchList(qrysting);
		//db.close();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return results.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return results.get(arg0);
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
				      view =inflater.inflate(R.layout.search_list,parent,false);
				      
			}
			SearchResults s= results.get(arg0)	;	
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView3);
			idTextView.setText(s.getFullname());
			
			TextView typeTextView =(TextView)
					view.findViewById(R.id.textView05);
			typeTextView.setText(s.getType());
			
			TextView myDate=(TextView)
					view.findViewById(R.id.textView4);
			myDate.setText(fm.format(s.getDate()));
		    TextView amount =(TextView)		    		
		    		view.findViewById(R.id.textView04);
			amount.setText(df.format(s.getAmount()));
			}catch(Exception e)
			{
				Log.e("Error loading data in listbox",e.toString());
			}
			return view;
		}
		
	

}
