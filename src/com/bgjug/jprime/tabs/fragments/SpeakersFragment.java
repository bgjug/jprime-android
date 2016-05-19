package com.bgjug.jprime.tabs.fragments;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bgjug.jprime.model.Speaker;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.tabs.fragments.asynctasks.SpeakerAsyncTask;
import com.bgjug.jprime.tabs.fragments.utils.BitmapUtils;
import com.bgjug.jprime2016.R;

public class SpeakersFragment extends Fragment {
	private View rootView;
	private BaseAdapter adapterSpeaker;
	private DatabaseHelper dbHelper;
	public static List<Speaker> allSpeakers;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_speakers, container,
				false);

		dbHelper = new DatabaseHelper(this.getActivity(), 5);
		allSpeakers = dbHelper.getSpeakers();
		// An AsyncTask is implemented and it uses getSpeakers from RestClient
		// but now the resource is empty.
		// Use a hack for now. Get the speakers from Sessions (getSessions)
		if (allSpeakers == null || allSpeakers.isEmpty()) {
			SpeakerAsyncTask speakersTask = new SpeakerAsyncTask(
					SpeakersFragment.this);
			speakersTask.execute("");
		} else
			loadSpeakers(allSpeakers, false);

		return rootView;
	}

	public void loadSpeakers(List<Speaker> allSpeakers, boolean dbInsert) {
		final List<Speaker> speakers = SpeakersFragment.allSpeakers = allSpeakers;
		if(dbInsert)
			dbHelper.addSpeakers(allSpeakers);
		
		final ListView listViewSpeaker = (ListView) rootView
				.findViewById(R.id.speakerListView);
		adapterSpeaker = new BaseAdapter() {
			Speaker currentSpeaker;

			@SuppressLint("ViewHolder")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View speakerItemLayout = inflater.inflate(
						R.layout.speaker_item, parent, false);

				final Speaker speaker = currentSpeaker = speakers.get(position);

				TextView speakerName = (TextView) speakerItemLayout
						.findViewById(R.id.textViewSpeakerName);
				speakerName.setText(speaker.getfirstName() + " " + speaker.getlastName());
				speakerName.setTypeface(null, Typeface.BOLD);

				TextView speakerBio = (TextView) speakerItemLayout
						.findViewById(R.id.textViewSpeakerBio);
				speakerBio.setText(getShortBio(speaker.getBio()));

				//set speaker's picture
				byte[] picByteArray = speaker.getPicture();
				Bitmap bmp = BitmapUtils.decodeSampledBitmapFromResource(
						picByteArray, 50, 50, 3);
				final ImageView speakerImageView = (ImageView) speakerItemLayout
						.findViewById(R.id.imageViewSpeakerPicture);
				speakerImageView.setImageBitmap(BitmapUtils
						.getRoundedCornerBitmap(bmp, 10));
				speakerImageView.setMinimumHeight(163);
				speakerImageView.setMinimumWidth(140);
				speakerImageView.post(new Runnable() {
					@Override
					public void run() {
						speakerImageView.setVisibility(View.GONE);
						speakerImageView.setVisibility(View.VISIBLE);
					}
				});
				// speakerImageView.setImageResource(R.drawable.star_fill512);

				speakerItemLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Speaker selSpeaker = speaker;
						Intent intent = new Intent(v.getContext(),
								SpeakerDetails.class);
						intent.putExtra("currentSpeaker", selSpeaker);
						startActivity(intent);
					}

				});

				return speakerItemLayout;
			}

			private String getShortBio(String bio) {
				if (bio.length() < 55)
					return bio;
				int index = bio.indexOf(" ", 55);
				return bio.substring(0, index) + " ...";
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return currentSpeaker;
			}

			@Override
			public int getCount() {
				return speakers.size();
			}
		};

		listViewSpeaker.setAdapter(adapterSpeaker);
		listViewSpeaker.setSelection(0);

	}
}
