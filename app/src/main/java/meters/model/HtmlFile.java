package meters.model;

import java.io.File;

import android.net.Uri;

public class HtmlFile {
	 
    private final File file;
 
    public HtmlFile(File file) {
        this.file = file;
    }
 
    /**
     * A convenience method to check that we are in a happy state
     * @return
     */
    public boolean isValid(){
        return this.file != null;
    }
 
    private String getFileName(){
        return file.getName();
    }
 
    /**
     * @return a uri that is a pointer to the html file we have created - this can be used by content providers
     */
    public Uri getFilePath(){
        return Uri.parse("content://"+ CacheFileProvider.AUTHORITY +"/"+ getFileName());
    }
}