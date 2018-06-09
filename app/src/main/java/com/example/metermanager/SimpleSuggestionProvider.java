package com.example.metermanager;

import android.content.SearchRecentSuggestionsProvider;

public class SimpleSuggestionProvider extends SearchRecentSuggestionsProvider {
	  final static  String AUTHORITY="com.example.metermanager.SimpleSuggestionProvider";
	  final static int MODE= DATABASE_MODE_QUERIES;
	public SimpleSuggestionProvider() {
		super();
		setupSuggestions(AUTHORITY, MODE);
		// TODO Auto-generated constructor stub
	}

}