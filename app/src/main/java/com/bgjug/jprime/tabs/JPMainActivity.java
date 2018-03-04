package com.bgjug.jprime.tabs;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.bgjug.jprime.tabs.adapter.TabPagerAdapter;
import com.bgjug.jprime2016.R;

public class JPMainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	private ViewPager viewTabPager;
	private TabPagerAdapter tabAdapter;
	private ActionBar actionBar;

	private String[] tabsArray = { "Agenda", "Speakers", "Venue", "Favorites" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_jpmain);
		viewTabPager = (ViewPager) findViewById(R.id.tabs);
		actionBar = getActionBar();
		tabAdapter = new TabPagerAdapter(getSupportFragmentManager());

		viewTabPager.setAdapter(tabAdapter);
		actionBar.setHomeButtonEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		actionBar.addTab(actionBar.newTab().setText(tabsArray[0])
				.setIcon(android.R.drawable.ic_menu_agenda)
				.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(tabsArray[1])
				.setIcon(android.R.drawable.ic_btn_speak_now)
				.setTabListener(this));
		actionBar
				.addTab(actionBar.newTab().setText(tabsArray[2])
						.setIcon(android.R.drawable.ic_dialog_map)
						.setTabListener(this));
		actionBar.addTab(actionBar.newTab().setText(tabsArray[3])
				.setIcon(android.R.drawable.ic_menu_my_calendar)
				.setTabListener(this));

		// adding swipe listener
		viewTabPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int navItem) {
						actionBar.setSelectedNavigationItem(navItem);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewTabPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	public void onBackPressed() {

	}
}
