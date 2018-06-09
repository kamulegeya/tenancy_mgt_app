package com.example.metermanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class BackupTask extends AsyncTask<String, Void, String[]> {

	public interface CompletionListener {
		void onBackupComplete();
		void onRestoreComplete();
		void onError(int errorCode);
		}
		public static final int BACKUP_SUCCESS = 1;
		public static final int RESTORE_SUCCESS = 2;
		public static final int BACKUP_ERROR = 3;
		public static final int RESTORE_NOFILEERROR = 4;
		public static final String COMMAND_BACKUP = "backupDatabase";
		public static final String COMMAND_RESTORE = "restoreDatabase";
        public static final String COMMAND_RESTOREEXTERNAL = "restoreDatabaseexternal";

    private Context mContext;
		private CompletionListener listener;
		public BackupTask(Context context) {
		super();
		mContext = context;
		}
		public void setCompletionListener(CompletionListener aListener) {
		listener = aListener;
		}
		@Override
		protected String[] doInBackground(String... params) {
		//Get a reference to the database
		File dbFile = mContext.getDatabasePath("meterapp.sqlite");
		//Log.e("Path", dbFile.toString());
		//Get a reference to the directory location for the backup
		File exportDir =
		new File(Environment.getExternalStorageDirectory(), "MeterAppBackups");
		if (!exportDir.exists()) {
		exportDir.mkdirs();
		}
		File backup = new File(exportDir, dbFile.getName());
		//Check the required operation
		String command = params[0];
		if(command.equals(COMMAND_BACKUP)) {
		//Attempt file copy
		try {
		backup.createNewFile();
		fileCopy(dbFile, backup);
		return new String[]{Integer.toString(BACKUP_SUCCESS), Uri.fromFile(backup).toString()};
		} catch (IOException e) {
		return  new String[]{Integer.toString(BACKUP_ERROR)};
		}
		} else if(command.equals(COMMAND_RESTORE)) {
		//Attempt file copy
		try {
		if(!backup.exists()) {
		return new String[]{Integer.toString( RESTORE_NOFILEERROR)};
		}
		dbFile.createNewFile();
		fileCopy(backup, dbFile);
		return new String[]{Integer.toString( RESTORE_SUCCESS)};
		} catch (IOException e) {
		return  new String[]{Integer.toString(BACKUP_ERROR)};
		}
		} else {
		return  new String[]{Integer.toString(BACKUP_ERROR)};
		}
		}

		@Override
		protected void onPostExecute(String[] result) {
		switch(Integer.parseInt(result[0])) {
		case BACKUP_SUCCESS:
		if(listener != null) {
			listener.onBackupComplete();
			//send email with attachment
            String emailbody ="Email this backup file to your self. It will act as safe copy of your data in case of loss of phone";
			Intent emailintent= new Intent(Intent.ACTION_SEND);
            emailintent .setType("vnd.android.cursor.dir/email");
            emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
            emailintent.putExtra(Intent.EXTRA_SUBJECT, "Database Backup File Copy");
            emailintent.putExtra(Intent.EXTRA_TEXT, emailbody);
			emailintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            //Log.v("Backupfile",result[1]);
            emailintent.putExtra(Intent.EXTRA_STREAM,Uri.parse(result[1]));
            mContext.startActivity(Intent.createChooser(emailintent,"Email backup file"));

		}
		break;
		case RESTORE_SUCCESS:
		if(listener != null) {
		listener.onRestoreComplete();
		}
		break;
		case RESTORE_NOFILEERROR:
		if(listener != null) {
		listener.onError(RESTORE_NOFILEERROR);
		}
		break;
		default:
		if(listener != null) {
		listener.onError(BACKUP_ERROR);
		}
		}
		}
		private void fileCopy(File source, File dest) throws IOException {
		@SuppressWarnings("resource")
		FileChannel inChannel = new FileInputStream(source).getChannel();
		@SuppressWarnings("resource")
		FileChannel outChannel = new FileOutputStream(dest).getChannel();
		try {
		inChannel.transferTo(0, inChannel.size(), outChannel);
		} 
		finally {
		if (inChannel != null)
		{
		inChannel.close();
		}
		if (outChannel != null)
		outChannel.close();
		}
		}
		}


