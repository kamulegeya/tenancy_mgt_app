package com.example.metermanager;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import meter.manager.helper.DatabaseHelperClass;
import meters.model.*;
import meters.model.RoomInvoicingStatus;

public class RoomInvoiceStatusAdapter extends BaseAdapter {
	@SuppressWarnings("unused")
	private Context context;
	private DatabaseHelperClass db;
	List<meters.model.RoomInvoicingStatus>list=new ArrayList<RoomInvoicingStatus>();

	public RoomInvoiceStatusAdapter(Context context1) {
		this.context=context1;
		 db=  DatabaseHelperClass.getInstance( context1);
         list=db.InvoicingStatus();
			//Log.e("year", myyear);

	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}


	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return list.get(index);
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
				      view =inflater.inflate(R.layout.room_invoicing_status,parent,false);
				      
			}
            RoomInvoicingStatus string=list.get(arg0);
			TextView editText=(TextView) view.findViewById(R.id.txtroominvoicingstatus);
			editText.setText(Html.fromHtml(string.getToString()));
		
			}catch(Exception e)
			{
				Log.e("listbox",e.toString());
			}
			return view;
	}
}
