package com.plingnote;

import java.util.ArrayList;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class ActivityMain extends MapActivity implements OnTabChangeListener{

	private TabHost tabHost;
	private ListView listView;
	private MapView mapView;


	private ScrollableViewPager viewPager;
	private TabsAdapter tabsAdapter;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		


		setContentView(R.layout.activity_main);

		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		// setup must be called if you are not inflating the tabhost from XML
		tabHost.setup();
		tabHost.setOnTabChangedListener((OnTabChangeListener) this);

		
		// setup list view
		listView = (ListView) findViewById(R.id.list);
		listView.setEmptyView((TextView) findViewById(R.id.empty));

		// create some dummy coordinates to add to the list
		List<GeoPoint> pointsList = new ArrayList<GeoPoint>();
		pointsList.add(new GeoPoint((int)(32.864*1E6), (int)(-117.2353*1E6)));
		pointsList.add(new GeoPoint((int)(37.441*1E6), (int)(-122.1419*1E6)));
		listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, pointsList));
		
		// setup map view
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.postInvalidate();
		
		// add views to tab host
		tabHost.addTab(tabHost.newTabSpec("List").setIndicator("List").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return listView;
			}
		}));
		tabHost.addTab(tabHost.newTabSpec("Map").setIndicator("Map").setContent(new TabContentFactory() {
			public View createTabContent(String arg0) {
				return mapView;
			}
		}));
		
        tabHost.setCurrentTab(0);
        tabHost.setCurrentTab(1);
        

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
		searchView.setQueryHint(getString(R.string.search_hint));
		searchView.setIconifiedByDefault(true);
		    
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		
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

		public TabsAdapter(MapActivity activityMain, ScrollableViewPager pager) {
			super(((ActivityMain) activityMain).getSupportFragmentManager());
			this.context = activityMain;
			this.actionBar = activityMain.getActionBar();
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

	public FragmentManager getSupportFragmentManager() {
		// TODO Auto-generated method stub
		return null;
	}
}