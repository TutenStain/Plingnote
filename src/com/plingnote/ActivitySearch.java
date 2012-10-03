package com.plingnote;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

/**
 * The searcher class. This class gets 
 * called once the user have pressed
 * the search button.
 */
public class ActivitySearch extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.search_activity);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		handleIntent(getIntent());
	}

	@Override
	protected void onNewIntent(Intent intent) {
		handleIntent(intent);
	}

	/**
	 * Handle the search intent and add the
	 * query to the recent searched items 
	 * for later suggestions on next search.
	 * @param intent the intent to handle
	 */
	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this, SearchSuggestionsRecentProvider.AUTHORITY, SearchSuggestionsRecentProvider.MODE);
			suggestions.saveRecentQuery(query, null);
			Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
			//TODO Do search here
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
		return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
