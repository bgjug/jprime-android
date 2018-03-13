package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;
import static com.bgjug.jprime.persistance.DBStatements.DATABASE_VERSION;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime.tabs.fragments.SessionsFragment;
import com.bgjug.jprime.tabs.fragments.SpeakersFragment;

public class ReloadAsyncTask extends AsyncTask<String, Void, List<Session>> {

	private ProgressDialog progressD;
	private RestClient jPrimeRC;
	private List<Session> sessions = null;
	private DatabaseHelper dbHelper;
	private SessionsFragment agendaActivity = null;
	private SpeakersFragment speakersActivity = null;

	public ReloadAsyncTask(SessionsFragment agendaActivity) {
		dbHelper = new DatabaseHelper(agendaActivity.getActivity(), DATABASE_VERSION);
		this.jPrimeRC = RestClient.getInstance();
		this.agendaActivity = agendaActivity;
		progressD = new ProgressDialog(agendaActivity.getActivity());
		progressD.setTitle("Loading jPrime Agenda");
	}

	public ReloadAsyncTask(SpeakersFragment speakersActivity) {
		this.jPrimeRC = RestClient.getInstance();
		this.speakersActivity = speakersActivity;
		progressD = new ProgressDialog(speakersActivity.getActivity());
		progressD.setTitle("Loading jPrime Speakers");
		dbHelper = new DatabaseHelper(speakersActivity.getActivity(), DATABASE_VERSION);
	}

	@Override
	protected void onPreExecute() {
		jPrimeRC.reloadContent();
		progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		progressD.show();
	}

	@Override
	protected List<Session> doInBackground(String... params) {

		sessions = jPrimeRC.getSessions();
		return sessions;
	}

	@Override
	protected void onCancelled() {
		if (progressD != null) {
			progressD.dismiss();
		}
	}

	@Override
	protected void onPostExecute(List<Session> result) {

		if (!jPrimeRC.getSessions().isEmpty()
				&& !jPrimeRC.getSpeakers().isEmpty()) {

			dbHelper.addSessions(jPrimeRC.getSessions());
			dbHelper.addSpeakers(jPrimeRC.getSpeakers());

			if (agendaActivity != null) {
				
				agendaActivity.loadAgenda(result, 1);
				agendaActivity.loadAgenda(result, 2);
			} else {
				speakersActivity.loadSpeakers(jPrimeRC.getSpeakers());
			}
		}

		if (progressD != null) {
			progressD.dismiss();
		}
	}
}
