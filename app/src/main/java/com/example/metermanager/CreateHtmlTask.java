package com.example.metermanager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import meters.model.HtmlFile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class CreateHtmlTask extends AsyncTask<String, Integer, HtmlFile>{
	  SharedPreferences sharedpreferences;
	  private Context cnxt;
	  public static final String Name = "nameKey"; 
	   public static final String Phone = "phoneKey"; 	   
	   public static final String Place = "placeKey";
	  public static final String MyPREFERENCES = "MyPrefs" ;
	  
    // This is an interface so whoever started this task can be informed when it is finished
    public interface OnTaskFinishedListener {
        void onHtmlCreated(HtmlFile html);
    }
 
    // The finished listener
    private final OnTaskFinishedListener taskFinishedListener;
    private final File folder;
 
    // Let the listener be set in the constructor (making it obvious to anyone using this class they can be informed when it is finished)
    // Note they can still pass null to not listen
    public CreateHtmlTask(File storageFolder, OnTaskFinishedListener taskFinishedListener,Context c) {
        this.folder = storageFolder;
        this.taskFinishedListener = taskFinishedListener;
        this.cnxt=c;
    }
   
  
   
    
    
    @Override
    protected HtmlFile doInBackground(String... params) {
        // We are wrapping the File in our own domain object so we can add some convenience methods to it
        HtmlFile htmlFile;
        try {
            // Create whatever HTML you want here - don't forget to escape strings
            String name = params[0];
            String h= " Acknowledgement of Receipt of Rent";
       	    sharedpreferences =cnxt.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
       	 String  username=null;
       	String number=null;
       	String aa=null;
       	 if (sharedpreferences.contains(Name))
	      {
	          username=sharedpreferences.getString(Name, "");

	      }
	      if (sharedpreferences.contains(Phone))
	      {
	         number=sharedpreferences.getString(Phone, "");

	      }
	      if (sharedpreferences.contains(Place))
	      {
	    	   aa=sharedpreferences.getString(Place, "");
           	                
       	    
	      }
	     	      
            StringBuilder builder = new StringBuilder();
            builder.append("<html>");
            builder.append("<head>");
            builder.append("</head>");
            builder.append("<body>");
            builder.append("<h1>");
            builder.append(h);
            builder.append("</h1>");
            builder.append("<p>");          
            builder.append(name);
            builder.append("</p>");
            builder.append("<p>"); 
            builder.append("Yours:");
  	        builder.append("<br>");
  	        builder.append(username);
  	        builder.append("<br>");
  	        builder.append( "Tel Number:");
  	        builder.append(number);        
  	        builder.append("<br>");
  	        builder.append( "Address:");
  	        builder.append(aa);       
                      
            builder.append("</p>"); 
            
            builder.append("</body>");
            builder.append("</html>");
            String content = builder.toString();
            // Store the file
            File file = createTempFile(folder, "receipt.html", content);
            // Create our domain object wrapping the file
            htmlFile = new HtmlFile(file);
        } catch (IOException e) {
            Log.e("IOException - creating safe HtmlFile", e.toString());
            // Create a 'NullSafe' HtmlFile object if an error occurs
            htmlFile = new HtmlFile(null);
        }
        return htmlFile;
    }
 
    /**
     * Creates a file - doesn't do any clean up
     * @param folder - the folder to save the file in
     * @param filename - the file name
     * @param fileContent - the content to put in the file
     * @return the created File
     * @throws IOException - if anything goes wrong <img src="http://blog.blundell-apps.com/wp-includes/images/smilies/icon_sad.gif" alt=":-(" class="wp-smiley">
     */
    private static File createTempFile(File folder, String filename, String fileContent) throws IOException {
        File f = new File(folder, filename);
        f.createNewFile();
        BufferedWriter buf = new BufferedWriter(new FileWriter(f));
        buf.append(fileContent);
        buf.close();
        return f;
    }
 
    @Override
    protected void onPostExecute(HtmlFile result) {
        super.onPostExecute(result);
        // Inform the class listening we have finished - sending back the completed Html File
        if(this.taskFinishedListener != null)
            this.taskFinishedListener.onHtmlCreated(result);
    }
}
