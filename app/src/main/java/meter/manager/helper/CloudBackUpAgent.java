package meter.manager.helper;

import java.io.File;

import android.app.backup.BackupAgentHelper;
import android.app.backup.FileBackupHelper;

public class CloudBackUpAgent extends BackupAgentHelper {
	private static final String DB_NAME = "meterapp.sqlite";

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		 FileBackupHelper dbs= new FileBackupHelper(getApplicationContext(), DB_NAME);
		 addHelper("dbs", dbs);
				
	}
	@Override
	public File getFilesDir()
	{
		File path = getDatabasePath(DB_NAME);
	      return path.getParentFile();
	}
}
