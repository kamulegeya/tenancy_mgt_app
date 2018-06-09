package com.example.metermanager;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class ImagesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.images);
		
		String text= getIntent().getDataString();
		//Log.e("link", text);
		int start= text.lastIndexOf("/");
		String end = text.substring(start+1, text.length());
		//Log.e("end", end);
		
		TouchImageView image = (TouchImageView)
				findViewById((R.id.item_single_new));	
		Resources res = this.getResources();		
		
		if(end.equals("meters"))
		{
		int id = R.drawable.addmeters;
		Bitmap m= BitmapFactory.decodeResource(res, id);
		  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
		  
		image.setImageBitmap(m);
		}else if(end.equals("tenants"))
		{
			int id = R.drawable.addtenants;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);			
			
		}else if(end.equals("allocation"))
		{
			int id = R.drawable.meters_contextmenu;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		
		}else if(end.equals("main"))
		{
			int id = R.drawable.mainmenu;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		}else if(end.equals("tmenu"))
		{
			int id = R.drawable.tenancymenu;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		}else if(end.equals("tenancy"))
		{
			int id = R.drawable.tenancy;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		}else if(end.equals("tenantslist"))
		{
			int id = R.drawable.tenants;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		}else if(end.equals("_meters"))
		{
			int id = R.drawable.meterslist;
			Bitmap m= BitmapFactory.decodeResource(res, id);
			  //Bitmap out = Bitmap.createScaledBitmap(m, 520, 680, false);
			  
			image.setImageBitmap(m);
		}
	}
}
