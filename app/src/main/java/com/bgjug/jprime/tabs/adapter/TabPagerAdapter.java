package com.bgjug.jprime.tabs.adapter;

/*import info.androidhive.tabsswipe.GamesFragment;
import info.androidhive.tabsswipe.MoviesFragment;
import info.androidhive.tabsswipe.TopRatedFragment;*/
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bgjug.jprime.tabs.fragments.SessionsFragment;
import com.bgjug.jprime.tabs.fragments.SpeakersFragment;
import com.bgjug.jprime.tabs.fragments.VenueFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

	public TabPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Agenda fragment activity
			return new SessionsFragment(false);
		case 1:
			// Speakers fragment activity
			return new SpeakersFragment();
		case 2:
			// Venue fragment activity
			return new VenueFragment();
		case 3:
			// My Agenda fragment activity
			return new SessionsFragment(true);
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
