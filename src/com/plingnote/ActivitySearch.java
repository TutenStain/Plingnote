package com.plingnote;

import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * The searcher class. This class gets 
 * called once the user have pressed
 * the search button.
 */
public class ActivitySearch extends FragmentActivity{
	FragmentListViewSearch fragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout fragmentContainer = new FrameLayout(this);
		fragmentContainer.setId(R.id.fragmentContainerSearch);
		fragment = new FragmentListViewSearch();
		setContentView(fragmentContainer, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerSearch, fragment).commit(); 
		
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
		
			fragment.setSearchQuery(query);
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
