package com.example.metermanager;

import meters.model.SearchResults;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SearchActivity extends Activity  implements OnItemClickListener{
	///get extal to pass from intent
	SearchActivityAdapter adapter;
	String query ;
	ListView list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_main);
		Intent intent = getIntent();		
		this.setDefaultKeyMode(Activity.DEFAULT_KEYS_SEARCH_LOCAL);
		//final Intent intent = getIntent();
		
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {

		 query = intent.getStringExtra(SearchManager.QUERY);
	}
		adapter=new SearchActivityAdapter(this, query);
		
		list=(ListView)
				findViewById(R.id.search_list);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		doSearchQuery(intent);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {
		// TODO Auto-generated method stub
		SearchResults r= (SearchResults)adapter.getItem(pos);
		// get the names
		String fname=r.getFirstname();
		String sname=r.getSurname();
		String type = r.getType();		
		Log.e("Name", fname +" "+ sname);
		if(type.equals("Invoices"))
		{
			// display invoices for that person/tenant
			// start activity to display invoices
		}
				
		
	}
	private void doSearchQuery(final Intent intent)
	{
		final String querystring=
				intent.getStringExtra(SearchManager.QUERY);
		SearchRecentSuggestions ss=new SearchRecentSuggestions (this,
				SimpleSuggestionProvider.AUTHORITY,
				SimpleSuggestionProvider.MODE);
		ss.saveRecentQuery(querystring, null);
				
		
				
				
				
	}
}
