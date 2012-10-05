package com.plingnote;

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
public class ActivitySearch extends FragmentActivity {
	FragmentListViewSearch fragment;
	
	/**
	 * Initiate the activity ONCE.
	 */
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
	
	/**
	 * If this search activity already have been created this
	 * method will get called the other times if the user want
	 * to initiate a search. This way we just instantiate the
	 * activity once and just handle the intent the other times. 
	 */
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

	/**
	 * Make sure that once the back button is pressed
	 * that we finish this activity.
	 */
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
