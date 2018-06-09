package com.example.metermanager;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.PowerPayments;
import meters.model.VReadings;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EditPaymentsAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;		
	private List<PowerPayments> payments =new ArrayList<PowerPayments>();
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	SimpleDateFormat fm =new SimpleDateFormat("dd-MM-yyyy",Locale.UK);
	
	
	public EditPaymentsAdapter(Context context1,String myDate) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
			payments=db.GetPayment(myDate);
			
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
  public void remove(int arg0)
  {
	  payments.remove(arg0);
  }
	@Override
	public View getView(int arg0, View view, ViewGroup parent) {
		try{		
			if (view ==null){
				LayoutInflater inflater=
						LayoutInflater.from(parent.getContext());
				      view =inflater.inflate(R.layout.payments_edit,parent,false);
				      
			}
			PowerPayments reading =payments.get(arg0);				
			
			TextView idTextView =(TextView)
					view.findViewById(R.id.textView5);
			
			idTextView.setText( Integer.toString(reading.getPaymentid()));
			
			TextView tenantTextView =(TextView)
					view.findViewById(R.id.textView6);		
			tenantTextView.setText(reading.getFullName().toString());			
					Log.e("Test Full Name",reading.getFullName().toString());
			TextView paymentdateTextView =(TextView)
					view.findViewById(R.id.textView7);		
			paymentdateTextView.setText(fm.format(((reading.getPaymentDate()))));
			
			TextView amountTextView =(TextView)
					view.findViewById(R.id.textView8);		
			amountTextView.setText(df.format(reading.getAmount()));
			
			 TextView narration= (TextView)
					 view.findViewById(R.id.textView9);
			 narration.setText(reading.getNarration());
		
			}catch(Exception e)
			{
				Log.e("Error loading data in listbox",e.toString());
			}
			return view;
	}
}
