package com.plingnote;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Our content provider class that is needed for 
 * recent query suggestions.
 */
public class SearchSuggestionsRecentProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = "com.plingnote.SearchSuggestionsRecentProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionsRecentProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}
}
