package com.example.metermanager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import meter.manager.helper.DatabaseHelperClass;
import meters.model.ActiveTenancy;
import meters.model.UnPaidInvoices;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

public class NotificationsReceiver extends BroadcastReceiver {
private DatabaseHelperClass db;
private int NTID_1=1;
private int NTID_2=2;
private List<UnPaidInvoices> invoices = new ArrayList<UnPaidInvoices>();
private List<ActiveTenancy> uninvoiced = new ArrayList<ActiveTenancy>();

@SuppressLint("SimpleDateFormat")
SimpleDateFormat fm =new SimpleDateFormat("yyyy-MM-dd");
private NotificationManager mymanager;
NotificationCompat.Builder mBuilder;
NotificationCompat.Builder mBuilder1;

	@SuppressLint("NewApi")
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
			
			try
			{
		  mymanager=(NotificationManager)context.getSystemService((Context.NOTIFICATION_SERVICE));
					
			db = DatabaseHelperClass.getInstance(context);
			
			Date mydate = new Date();
			String date = fm.format(mydate);
			invoices= db.GetUnPaidInvoices(date);
			
			if(invoices.size()>0)
			{
				 mBuilder=
				    new NotificationCompat.Builder(context)
				    .setSmallIcon(R.drawable.ic_launcher)
				    .setContentTitle("UnPaid Invoices")				    
				    .setContentText("Overdue Invoices Notification")
				    .setTicker("Overdue Invoices Notification");			
			NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
			  style.setBigContentTitle("More details");
			  
			  for(int i=0;i<invoices.size();i++)
			  {
				  UnPaidInvoices myi= (UnPaidInvoices)invoices.get(i);
				  String add= "Tenant:" + myi.getFullName() + "DueDate:" + myi.getDatedue();
				  style.addLine(add);
			  }
			
			  mBuilder.setStyle(style);
			  mBuilder.setNumber(NTID_1);
			  mBuilder.setAutoCancel(true);
			  Intent i = new Intent(context,OverDueInvoices.class);
			
			  
			  PendingIntent pIntent = PendingIntent.getActivity(context, NTID_1, i,  PendingIntent.FLAG_UPDATE_CURRENT);
			 
			  mBuilder.setContentIntent(pIntent);
			  mymanager.notify(NTID_1,mBuilder.build());
			  
			 
			}
			 // get uninvoiced
			  
			  uninvoiced=db.getUnInvoicedRooms(date);
			  if(uninvoiced.size()>0)
			  {
				  
				  mBuilder1 =
						    new NotificationCompat.Builder(context)
						    .setSmallIcon(R.drawable.ic_launcher)
						    .setContentTitle("Rooms not invoiced")				    
						    .setContentText("None Invoicing Notification")
						    .setTicker("None Invoiced rooms notification");	
				  NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
					NotificationCompat.InboxStyle style1 = new NotificationCompat.InboxStyle();
					  style.setBigContentTitle("More details");
					  
					  for(int k=0;k<uninvoiced.size();k++)
					  {
						  ActiveTenancy myi= (ActiveTenancy)uninvoiced.get(k);
						  String add= "Tenant:" + myi.getFullName() + "Room:" + myi.getApartment();
						  Log.e("tttt", add);
						  style1.addLine(add);
					  }
					
					  mBuilder1.setStyle(style1);
					  mBuilder1.setNumber(NTID_2);
					  mBuilder1.setAutoCancel(true);					 
					  Intent p = new Intent(context,UnInvoicedRoomsActivity.class);
					 // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					 // i.putExtra("D", date);
					  //Log.e("dtae", date);					  
					  PendingIntent pIntent1 = PendingIntent.getActivity(context, NTID_2, p, PendingIntent.FLAG_UPDATE_CURRENT);					 
					  mBuilder1.setContentIntent(pIntent1);
				  
				  			  
			  }			  	    
			  
			 
			  mymanager.notify(NTID_2,mBuilder1.build());
			
			}catch(Exception e)
			{
				Toast.makeText(context, "err", Toast.LENGTH_LONG).show();
				e.printStackTrace();
				
			}
		}
	}

}
