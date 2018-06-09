package com.example.metermanager;



import meter.manager.helper.DatabaseHelperClass;
import android.app.Activity;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SearchMain extends Activity {
	DatabaseHelperClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		db=   DatabaseHelperClass.getInstance(this);
		db.createSearchTable();
		setContentView(R.layout.main_search);
		Button b= (Button)
				findViewById(R.id.btnclear);
		b.setOnClickListener( new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SearchRecentSuggestions ss=new SearchRecentSuggestions (SearchMain.this,
						SimpleSuggestionProvider.AUTHORITY,
						SimpleSuggestionProvider.MODE);
				ss.clearHistory();
				
				
			}
		});
	}
public void StartSearch(View v)
{
	 onSearchRequested();
}

}
