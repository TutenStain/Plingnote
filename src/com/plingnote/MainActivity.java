package com.plingnote;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class MainActivity extends FragmentActivity{

	ViewPager viewPager;
	TabsAdapter tabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		viewPager = new ViewPager(this);
		viewPager.setId(1);
		setContentView(viewPager);

		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);

		tabsAdapter = new TabsAdapter(this, viewPager);
		tabsAdapter.addTab(actionBar.newTab().setText("Map"), FragmentMapView.class, null);
		tabsAdapter.addTab(actionBar.newTab().setText("List"), FragmentListView.class, null);

		if (savedInstanceState != null) {
			actionBar.setSelectedNavigationItem(savedInstanceState.getInt("tab", 0));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
	}

	public static class TabsAdapter extends FragmentPagerAdapter implements ActionBar.TabListener, ViewPager.OnPageChangeListener {

		private final Context context;
		private final ActionBar actionBar;
		private final ViewPager viewPager;
		private final ArrayList<TabInfo> tabs = new ArrayList<TabInfo>();

		static final class TabInfo {
			private final Class<?> clss;
			private final Bundle args;

			TabInfo(Class<?> clss, Bundle args) {
				this.clss = clss;
				this.args = args;
			}
		}

		public TabsAdapter(FragmentActivity activity, ViewPager pager) {
			super(activity.getSupportFragmentManager());
			context = activity;
			actionBar = activity.getActionBar();
			viewPager = pager;
			viewPager.setAdapter(this);
			viewPager.setOnPageChangeListener(this);
		}

		public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
			TabInfo info = new TabInfo(clss, args);
			tab.setTag(info);
			tab.setTabListener(this);
			tabs.add(info);
			actionBar.addTab(tab);
			notifyDataSetChanged();
		}

		public void onPageScrollStateChanged(int state) { }

		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

		public void onPageSelected(int position) {
			actionBar.setSelectedNavigationItem(position);
		}

		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) { }

		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			Object tag = tab.getTag();
			for (int i = 0 ; i < tabs.size(); i++) {
				if (tabs.get(i) == tag) {
					viewPager.setCurrentItem(i);
				}
			}
		}

		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) { }

		@Override
		public Fragment getItem(int position) {
			TabInfo info = tabs.get(position);
			return Fragment.instantiate(context, info.clss.getName(), info.args);
		}

		@Override
		public int getCount() {
			return tabs.size();
		}
	}
}