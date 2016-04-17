package com.bgjug.jprime.tabs.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime2016.R;

public class AgendaFragment extends Fragment {

	private View rootView;
	private Button btnDay1;
	private Button btnDay2;
	private BaseAdapter adapterDay1;
	private BaseAdapter adapterDay2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_agenda, container, false);

		btnDay1 = (Button) rootView.findViewById(R.id.buttonDay1);
		btnDay2 = (Button) rootView.findViewById(R.id.buttonDay2);
		btnDay1.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnDay1.setEnabled(false);
				btnDay2.setEnabled(true);
				loadAgenda();
			}

		});

		btnDay2.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnDay1.setEnabled(true);
				btnDay2.setEnabled(false);
				loadAgenda2();
			}

		});
		loadAgenda();
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	private void loadAgenda() {
		ListView listViewAgenda = (ListView) rootView
				.findViewById(R.id.agendaListView);

		adapterDay1 = new BaseAdapter() {
			int pointPosition = 0;

			public int getPointerPosition() {
				return pointPosition;
			}

			@SuppressLint("ViewHolder")
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View agendaItemLayout = inflater.inflate(R.layout.agenda_item,
						parent, false);

				TextView sessionTime = (TextView) agendaItemLayout
						.findViewById(R.id.textViewTime);
				TextView sessionName = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSessionName);
				TextView sessionHall = (TextView) agendaItemLayout
						.findViewById(R.id.textViewHall);
				TextView sessionSpeaker = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSpeaker);
				TextView sessionInfo = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSessionInfo);

				int testIndex = position + 8;

				sessionTime.setText(testIndex + ".00 - " + (testIndex + 1)
						+ ".00");
				sessionTime.setTypeface(null, Typeface.BOLD);

				sessionName.setText("Coding Culture ");
				sessionName.setTypeface(null, Typeface.BOLD);

				sessionHall.setText("Hall " + position + 1);
				sessionSpeaker.setText("Sven Peters");
				sessionSpeaker.setTypeface(null, Typeface.ITALIC);
				sessionInfo
						.setText("Imagine a culture where the input of the whole organization turns an individual idea into a user story in just a couple...");

				agendaItemLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}

				});

				return agendaItemLayout;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return getPointerPosition();
			}

			@Override
			public int getCount() {
				return 3;
			}
		};

		listViewAgenda.setAdapter(adapterDay1);
		listViewAgenda.setSelection((Integer) adapterDay1.getItem(0));
	}

	private void loadAgenda2() {
		ListView listViewAgenda = (ListView) rootView
				.findViewById(R.id.agendaListView);

		adapterDay2 = new BaseAdapter() {
			int pointPosition = 0;

			public int getPointerPosition() {
				return pointPosition;
			}

			@SuppressLint("ViewHolder")
			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				View agendaItemLayout = inflater.inflate(R.layout.agenda_item,
						parent, false);

//              TEST PURPOSES
//				RestClient client = new RestClient();
//				client.getSessions();

				TextView sessionTime = (TextView) agendaItemLayout
						.findViewById(R.id.textViewTime);
				TextView sessionName = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSessionName);
				TextView sessionHall = (TextView) agendaItemLayout
						.findViewById(R.id.textViewHall);
				TextView sessionSpeaker = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSpeaker);
				TextView sessionInfo = (TextView) agendaItemLayout
						.findViewById(R.id.textViewSessionInfo);

				int testIndex = position + 9;

				sessionTime.setText(testIndex + ".00 - " + (testIndex + 1)
						+ ".00");
				sessionTime.setTypeface(null, Typeface.BOLD);

				sessionName.setText("The Secrets of Concurrency");
				sessionName.setTypeface(null, Typeface.BOLD);

				sessionHall.setText("Hall " + position + 1);
				sessionSpeaker.setText("Heinz Kabutz");
				sessionSpeaker.setTypeface(null, Typeface.ITALIC);

				sessionInfo
						.setText("From the first version of Java, we have been able to create multiple threads. Initially, this was mostly used for making our GUIs more responsive. For example, we would read a file using...");

				agendaItemLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}

				});

				return agendaItemLayout;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return getPointerPosition();
			}

			@Override
			public int getCount() {
				return 3;
			}
		};

		listViewAgenda.setAdapter(adapterDay2);
		listViewAgenda.setSelection((Integer) adapterDay2.getItem(0));

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}
