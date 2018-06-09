package com.example.metermanager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.Balances;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GetBalances extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<Balances> balance =new ArrayList<Balances>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	public GetBalances(Context context1)
	{
		this.context=context1;
		 db= DatabaseHelperClass.getInstance( context1);
			balance=db.GetBalances();
			//db.close();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated met
		return balance.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return balance.get(arg0);
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
				      view =inflater.inflate(R.layout.balances_details,parent,false);
				      
			}
			Balances b =balance.get(id);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView6);
			
			idTextView.setText( Integer.toString(b.get_id()));
			
			TextView tenant =(TextView)
					view.findViewById(R.id.textView7);		
			tenant.setText(b.getFullName().toString());
			
			TextView totalbill =(TextView)
					view.findViewById(R.id.textView8);		
			totalbill.setText(df.format(b.getTotal_bill()));
		
			 TextView payments =(TextView)
						view.findViewById(R.id.textView9);		
			 payments .setText(df.format(b.getTotal_payment()));			 
			 
			 TextView balance =(TextView)
						view.findViewById(R.id.textView10);		
			 balance  .setText(df.format(b.getAmount_due()));
		
			}catch(Exception e)
			{
				Log.e("Error loading balances  data in listview",e.toString());
			}
			return view;
		}

}
