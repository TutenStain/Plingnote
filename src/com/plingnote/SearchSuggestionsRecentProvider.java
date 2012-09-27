package com.plingnote;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsRecentProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.plingnote.SearchSuggestionsRecentProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionsRecentProvider() {
		super();
		setupSuggestions(AUTHORITY, MODE);
	}
}
