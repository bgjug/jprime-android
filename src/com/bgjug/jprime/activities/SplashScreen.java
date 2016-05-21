package com.bgjug.jprime.activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.tabs.JPMainActivity;
import com.bgjug.jprime.tabs.fragments.asynctasks.StartupAsyncTask;
import com.bgjug.jprime2016.R;

public class SplashScreen extends Activity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);

		DatabaseHelper dbHelper = new DatabaseHelper(this, 5);
		List<Session> sessions = dbHelper.getSessions(false);
		if (sessions == null || sessions.isEmpty()) {
			
			StartupAsyncTask startupTask = new StartupAsyncTask(new Intent(
					this, JPMainActivity.class), this);
			startupTask.execute("");
		} else {
			this.startActivity(new Intent(this, JPMainActivity.class));
		}
	}

}
