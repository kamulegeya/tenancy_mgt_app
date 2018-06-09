package com.example.metermanager;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_image);
		TouchImageView image = (TouchImageView)
				findViewById((R.id.homeimage));	
		Resources res = this.getResources();		
		
		
		int id = R.drawable.home;
		Bitmap m= BitmapFactory.decodeResource(res, id);
		  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
		  
		image.setImageBitmap(m);
		
		TextView txt= (TextView)
				findViewById(R.id.txtimagecaption);
		txt.setText(Html.fromHtml(getString(R.string.txthome)));
		//Intent i= new Intent(this,UnInvoicedRoomsActivity.class);
		//startActivity(i);
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch(item.getItemId()){
			
					   
			case R.id.view_tenants:
				 intent = new Intent(this,TenantList.class);
				this.startActivity(intent);
			   break;
			case R.id.taxcompute:
				intent = new Intent(this,RentalTaxComputation.class);
				this.startActivity(intent);
				break;
			
			case R.id.add_meter:
				 intent = new Intent(this,MeterList.class);
				this.startActivity(intent);
			   break;
			case R.id.history:
				 intent = new Intent(this,Payments.class);
				this.startActivity(intent);
			   break;
			case R.id.bill:
				 intent = new Intent(this,Bills.class);
				this.startActivity(intent);
			   break;
			case R.id.editpayments:
				 intent = new Intent(this,EditPayments.class);
				this.startActivity(intent);
			   break;
			case R.id.add_new_tenant:
				 intent = new Intent(this,AddNewTenant.class);
				this.startActivity(intent);
			   break;
			   
			case R.id.settings:
				 intent = new Intent(this,UserSettingsActivity.class);
				this.startActivity(intent);
			   break;   
			   
			case R.id.add_new_meter:
				 intent = new Intent(this,AddNewMeter.class);
				this.startActivity(intent);
			   break;
			case R.id.tenancy:
				 intent = new Intent(this,ActiveTenancyList.class);
				this.startActivity(intent);
			   break;	   
			   
					case R.id.serach:
						 intent = new Intent(this,SearchMain.class);
						this.startActivity(intent);
					   break;
					case R.id.back_up:
						 intent = new Intent(this,BackupActivity.class);
						this.startActivity(intent);
					   break;  
					case R.id.readings:
						 intent = new Intent(this,MeterReadings.class);
						this.startActivity(intent);
					   break;  	   
					case R.id.help:
						 intent = new Intent(this,Help.class);
						this.startActivity(intent);
					   break; 
					case R.id.exports:
						 intent = new Intent(this,CSVExports.class);
						this.startActivity(intent);
					   break; 
			   default:
			  return super.onOptionsItemSelected(item);
				}
		return true;
	}
}
