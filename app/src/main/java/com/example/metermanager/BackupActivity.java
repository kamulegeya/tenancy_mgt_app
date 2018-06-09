package com.example.metermanager;

import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;

import android.os.Bundle;
import android.os.Environment;

import android.view.View;

import android.widget.Toast;



public class BackupActivity extends Activity implements BackupTask.CompletionListener {

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backup);

	}

	@Override
	public void onBackupComplete() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Backup Successfull",Toast.LENGTH_LONG).show();
		
	}

	@Override
	public void onRestoreComplete() {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Restore Successfull",Toast.LENGTH_LONG).show();
		
		
	}

	@Override
	public void onError(int errorCode) {
		if(errorCode == BackupTask.RESTORE_NOFILEERROR) {
			Toast.makeText(this, "No Backup Found to Restore",
			Toast.LENGTH_SHORT).show();
			} else {
			Toast.makeText(this, "Error During Operation: "+errorCode,
			Toast.LENGTH_SHORT).show();
			}
		
	}
	public void BackUp(View v)
	{
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm file Backup");
				String msg="Are sure you want to backup  the app data."+"\n"+
					     "This may  overwrite existing backup  data and can not be undone";
				b.setMessage(msg
						     );
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						BackupTask task= new BackupTask(BackupActivity.this);
						task.setCompletionListener(BackupActivity.this);
						task.execute(BackupTask.COMMAND_BACKUP);
							
						
					}
				});
			b.show();
		}else
		{
			Toast.makeText(this, "No SD Card mounted. Can not backup",Toast.LENGTH_LONG).show();  
			
		}
	}
   public void Restore(View v)
   
   {
	   if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) 
		{
			
			 AlertDialog.Builder b= new AlertDialog.Builder(this);
				b.setTitle("Confirm file restoration");
				String msg="Are sure you want to restore the file."+"\n"+
					     "This will overwrite all app data and can not be undone";
				b.setMessage(msg
						     );
				b.setNegativeButton("cancel", null);
				b.setIcon(R.drawable.ic_launcher);
				b.setPositiveButton("OK", new  DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						BackupTask task= new BackupTask(BackupActivity.this);
						task.setCompletionListener(BackupActivity.this);
						task.execute(BackupTask.COMMAND_RESTORE);
						//Toast.makeText(this, "Restore Successful",Toast.LENGTH_LONG).show();  
						
						
					}
				});
			b.show();
		} else
		{
			Toast.makeText(getApplicationContext(), "No Sd card/memory card mounted", Toast.LENGTH_LONG).show();
		}
	   
  }



}
