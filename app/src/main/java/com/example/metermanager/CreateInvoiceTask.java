package com.example.metermanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import meters.model.HtmlFile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

public class CreateInvoiceTask extends AsyncTask<String, Integer, HtmlFile>{
	  static SharedPreferences sharedpreferences;
	  private  Context cnxt;
	  private ProgressDialog pd;
	  public static final String Name = "nameKey"; 
	   public static final String Phone = "phoneKey"; 	   
	   public static final String Place = "placeKey";
	  public static final String MyPREFERENCES = "MyPrefs" ;
	
		  private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 10,
		      Font.NORMAL);
		  private static Font smallBold1 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
			      Font.BOLD);
    // This is an interface so whoever started this task can be informed when it is finished
    public interface OnTaskFinishedListener {
        void onHtmlCreated(HtmlFile html);
    }
 
    // The finished listener
    private final OnTaskFinishedListener taskFinishedListener;
    private final File folder;
 
    // Let the listener be set in the constructor (making it obvious to anyone using this class they can be informed when it is finished)
    // Note they can still pass null to not listen
    public CreateInvoiceTask(File storageFolder, OnTaskFinishedListener taskFinishedListener,Context c) {
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
           
            // Store the file
            File file = createTempFile(folder, "invoice.pdf");
            Document document = new Document();
            try {
				PdfWriter.getInstance(document, new FileOutputStream(file));
				 document.open();
			     
				 addTitlePage(document,name);
			      document.close();
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            // Create our domain object wrapping the file
            htmlFile = new HtmlFile(file);
        } catch (IOException e) {
            Log.e(" creating safe HtmlFile", e.toString());
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
    private static File createTempFile(File folder, String filename) throws IOException {
        File f = new File(folder, filename);
        f.createNewFile();
       
        return f;
    }
 
    @Override
    protected void onPostExecute(HtmlFile result) {
        super.onPostExecute(result);
        // Inform the class listening we have finished - sending back the completed Html File
      
        if(this.taskFinishedListener != null)
            this.taskFinishedListener.onHtmlCreated(result);
        pd.dismiss();
        
    }
    private  void addTitlePage(Document document,String name)
    	      throws DocumentException {
    	    Paragraph preface = new Paragraph();
    	    // We add one empty line
    	    addEmptyLine(preface, 1);
    	    // Lets write a big header
    	    preface.add(new Paragraph("Invoice",smallBold1));
    	     preface.setAlignment(Element.ALIGN_CENTER);
    	    addEmptyLine(preface, 1);   	   
       	     preface.add(new Paragraph(name, smallBold));    	     	   

    	    addEmptyLine(preface, 1);

    	    preface.add(new Paragraph(returnAddress(),smallBold));
    	    preface.setAlignment(Element.ALIGN_CENTER);

    	    document.add(preface);
    	    // Start a new page
    	   // document.newPage();
    	  }
    private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
          paragraph.add(new Paragraph(" "));
        }
      }
    
    private String returnAddress()
    
    
    {
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
            builder.append("Yours Sincerely");
            builder.append("\r\n");
  	        builder.append(username); 
  	        builder.append("\r\n");
  	        builder.append( "Tel Number:");  	       
  	        builder.append(number);  
  	        builder.append("\r\n");
  	        builder.append( "Address:");
  	        builder.append(aa);                      
            String content = builder.toString();    	    
    	    // Will create: Report generated by: _name, _date
		return content;
    	
    }
    @Override
    protected void onPreExecute() {
    	// TODO Auto-generated method stub
    	// TODO Auto-generated method stub
    			 pd = new ProgressDialog(cnxt);
    			 pd.setMessage("Creating attachment...please wait..");
    			 pd.show();
    	super.onPreExecute();
    }
}
