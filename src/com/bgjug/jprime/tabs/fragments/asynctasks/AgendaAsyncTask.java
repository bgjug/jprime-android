package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime.tabs.fragments.SessionsFragment;

public class AgendaAsyncTask extends AsyncTask<String, Void, List<Session>> {

	private SessionsFragment agendaActivity;
	private ProgressDialog progressD;
	private RestClient jPrimeRC;
	private List<Session> sessions = null;

	public AgendaAsyncTask(SessionsFragment agendaActivity) {
		this.agendaActivity = agendaActivity;
		this.jPrimeRC = RestClient.getInstance();
	}

	@Override
	protected void onPreExecute() {
		progressD = new ProgressDialog(agendaActivity.getActivity());
		progressD.setTitle("Loading jPrime Agenda");
		progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		progressD.show();

	}

	@Override
	protected List<Session> doInBackground(String... params) {
		if (sessions == null)
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
		if (progressD != null) {
			progressD.dismiss();
		}

		if (result != null) {
			agendaActivity.loadAgenda(result, 1);
			// SpeakersFragment.allSpeakers = getSpeakers(result);
		}
	}

}
