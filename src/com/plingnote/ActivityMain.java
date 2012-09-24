package com.plingnote;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.widget.SearchView;

public class ActivityMain extends FragmentActivity{

	private ScrollableViewPager viewPager;
	private TabsAdapter tabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.viewPager = new ScrollableViewPager(this);
		this.viewPager.setId(R.id.viewPager);
		setContentView(viewPager);
		
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		tabsAdapter = new TabsAdapter(this, viewPager);
		tabsAdapter.addTab(actionBar.newTab().setIcon(android.R.drawable.ic_menu_mapmode), FragmentMapView.class, null);
		tabsAdapter.addTab(actionBar.newTab().setIcon(android.R.drawable.ic_menu_sort_by_size), FragmentListView.class, null);

		if (savedInstanceState != null) {
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(true);
		    
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ScrollableViewPager.OnPageChangeListener {

		private final Context context;
		private final ActionBar actionBar;
		private final ScrollableViewPager viewPager;
		private final ArrayList<TabInfo> tabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> clss, Bundle args) {
				this.clss = clss;
				this.args = args;
			}
		}

		public TabsAdapter(FragmentActivity activity, ScrollableViewPager pager) {
			super(activity.getSupportFragmentManager());
			this.context = activity;
			this.actionBar = activity.getActionBar();
			this.viewPager = pager;
			this.viewPager.setAdapter(this);
			this.viewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			this.tabs.add(info);
			this.actionBar.addTab(tab);
			notifyDataSetChanged();
		}

		public void onPageScrollStateChanged(int state) { }

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

		public void onPageSelected(int position) {
			this.actionBar.setSelectedNavigationItem(position);
		}

		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) { }

		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0 ; i < this.tabs.size(); i++) {
				if (this.tabs.get(i) == tag) {
					this.viewPager.setCurrentItem(i);
				}
			}
		}

		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) { }

		@Override
		public Fragment getItem(int position) {
			TabInfo info = this.tabs.get(position);
			return Fragment.instantiate(this.context, info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			return this.tabs.size();
		}
	}
}