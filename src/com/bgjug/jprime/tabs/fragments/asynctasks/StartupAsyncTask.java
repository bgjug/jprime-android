package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;

import com.bgjug.jprime.activities.SplashScreen;
import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.rest.RestClient;

public class StartupAsyncTask extends AsyncTask<String, Void, List<Session>> {
	private RestClient jPrimeRC;
	private Intent jpMainActivity;
	private SplashScreen splashScreen;
	private DatabaseHelper dbHelper;

	public StartupAsyncTask(Intent intent, SplashScreen splashScreen) {
		this.jpMainActivity = intent;
		this.splashScreen = splashScreen;
		this.jPrimeRC = RestClient.getInstance();
		dbHelper = new DatabaseHelper(splashScreen, 5);
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected List<Session> doInBackground(String... params) {

		return jPrimeRC.getSessions();
	}

	@Override
	protected void onPostExecute(List<Session> result) {

		if (!jPrimeRC.getSessions().isEmpty()) {
			dbHelper.addSessions(jPrimeRC.getSessions());
			dbHelper.addSpeakers(jPrimeRC.getSpeakers());
		}
		splashScreen.startActivity(jpMainActivity);
	}
}
