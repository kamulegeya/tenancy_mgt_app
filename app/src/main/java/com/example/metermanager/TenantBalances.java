package com.example.metermanager;
import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import meters.model.Balances;
public class TenantBalances extends Activity  implements OnItemClickListener{
	ListView listview;
	GetBalances  balance;
	DecimalFormat df = new DecimalFormat("#,###,###,###");
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.balances_main);
		balance= new GetBalances(this);
		listview =(ListView)findViewById(R.id.balances);		
		listview.setAdapter(balance);
		listview.setOnItemClickListener(this);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		final int id=pos;
		 AlertDialog.Builder b= new AlertDialog.Builder(this);
			b.setTitle("Confirm Sending Sms");
			b.setMessage("Are sure you want to send an sms of this bill?."+"\n"+
					     "Applicable Service Provider SMS charges applies");
			b.setNegativeButton("cancel", null);
			b.setIcon(R.drawable.ic_launcher);
			b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){
				@Override
		 public void onClick(DialogInterface dialog, int which) {	
		Balances b=(Balances)balance.getItem(id);
		String name=b.getFullName();
		// get mobile number
		String mobileNumber=null;
		if(Integer.parseInt(b.getMobile())==0)
		{
			mobileNumber=b.getMobile2().trim();
			
		}
		
		else
		{
			mobileNumber=b.getMobile().trim();
		}
				
		// get balance due
	   String tbalance= df.format(b.getAmount_due());
	   
	   // get the message
	   String smsmsg= " Dear "+ name +","+ " you have  UGX "+ tbalance  + "\n"+
	                   " balance unpaid on your power bill."+ "\n"+
			           " Please clear the amount before power is disconnected ";
			  // SendSms(mobileNumber, smsmsg);
	   
	   String sms=smsmsg;
		Intent foward= new Intent(Intent.ACTION_VIEW);
		foward.setData(Uri.parse("smsto"));
		foward.putExtra("sms_body", sms);
		foward.setType("vnd.android-dir/mms-sms");
		foward.putExtra("address",mobileNumber); 
		startActivity(foward);
				}
			});
			b.show();
	   
	   
	}
}
