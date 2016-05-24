package com.bgjug.jprime.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bgjug.jprime.tabs.JPMainActivity;
import com.bgjug.jprime.tabs.fragments.asynctasks.StartupAsyncTask;
import com.bgjug.jprime2016.R;

public class SplashScreen extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		StartupAsyncTask startupTask = new StartupAsyncTask(new Intent(this,
				JPMainActivity.class), this);
		startupTask.execute("");
	}

}
