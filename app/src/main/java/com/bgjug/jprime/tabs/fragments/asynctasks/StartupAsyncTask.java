package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;

import com.bgjug.jprime.activities.SplashScreen;
import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.model.Speaker;
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

		List<Session> sessions = dbHelper.getSessions(false);
		List<Speaker> speakers = dbHelper.getSpeakers();
		if (sessions.isEmpty() || speakers.isEmpty()) 
			return jPrimeRC.getSessions();
		else
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return sessions;
	}

	@Override
	protected void onPostExecute(List<Session> result) {

		List<Session> sessions = dbHelper.getSessions(false);
		List<Speaker> speakers = dbHelper.getSpeakers();
		if (sessions.isEmpty() || speakers.isEmpty()) {
			dbHelper.addSessions(jPrimeRC.getSessions());
			dbHelper.addSpeakers(jPrimeRC.getSpeakers());
		}
		splashScreen.startActivity(jpMainActivity);
	}
}
