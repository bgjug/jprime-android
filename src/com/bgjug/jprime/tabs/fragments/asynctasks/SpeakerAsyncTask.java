package com.bgjug.jprime.tabs.fragments.asynctasks;

import java.util.List;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.bgjug.jprime.model.Speaker;
import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime.tabs.fragments.SpeakersFragment;

public class SpeakerAsyncTask extends AsyncTask<String, Void, List<Speaker>> {

	private SpeakersFragment speakerFragment;
	private RestClient jPrimeRC;
	private ProgressDialog progressD;
	private List<Speaker> speakers;

	public SpeakerAsyncTask(SpeakersFragment speakerFragment) {
		this.speakerFragment = speakerFragment;
		this.jPrimeRC = RestClient.getInstance();
	}

	@Override
	protected void onPreExecute() {
		progressD = new ProgressDialog(speakerFragment.getActivity());
		progressD.setTitle("Loading jPrime Speakers");
		progressD.setProgressStyle(ProgressDialog.STYLE_SPINNER);

		progressD.show();
	}
	
	@Override
	protected List<Speaker> doInBackground(String... params) {
		if (speakers == null){
			//List<Session> sessions = jPrimeRC.getSessions();
			speakers = jPrimeRC.getSpeakers(); //getSpeakers(sessions);
		}
		return speakers;
	}
	
	@Override
	protected void onCancelled() {
		if (progressD != null) {
			progressD.dismiss();
		}
	}

	@Override
	protected void onPostExecute(List<Speaker> result) {
		if (progressD != null) {
			progressD.dismiss();
		}

		if (result != null) {
			SpeakersFragment.allSpeakers = result;
			if(speakerFragment != null) speakerFragment.loadSpeakers(result);
		}
	}
}
