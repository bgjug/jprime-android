package com.bgjug.jprime.tabs.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.tabs.fragments.asynctasks.AgendaAsyncTask;
import com.bgjug.jprime2016.R;

public class AgendaFragment extends Fragment {

	private View rootView;
	private Button btnDay1;
	private Button btnDay2;
	private BaseAdapter adapterDay1;
	private List<Session> allSessions;

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
				loadAgenda(allSessions, 1);
			}

		});

		btnDay2.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnDay1.setEnabled(true);
				btnDay2.setEnabled(false);
				loadAgenda(allSessions, 2);
			}

		});
		if (allSessions == null || allSessions.isEmpty()) {
			AgendaAsyncTask agendaTask = new AgendaAsyncTask(
					AgendaFragment.this);
			agendaTask.execute("");
		} else
			loadAgenda(allSessions, 1);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void loadAgenda(List<Session> result, int dayRequest) {
		allSessions = result;
		final List<Session> sessionsDay = getSessionsDay(result, dayRequest);
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
				Session session = sessionsDay.get(position);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewTime),
						getSessionTime(session.getStartTime()) + "-"
								+ getSessionTime(session.getEndTime()),
						Typeface.BOLD);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSessionName),
						session.getName(), Typeface.BOLD);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewHall),
						session.getHall(), Typeface.NORMAL);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSpeaker),
						session.getSpeaker().getfirstName() + " "
								+ session.getSpeaker().getlastName(),
						Typeface.ITALIC);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSessionInfo),
						getSessionShortDscr(session), Typeface.NORMAL);

				agendaItemLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

					}

				});

				return agendaItemLayout;
			}

			private String getSessionShortDscr(Session session) {
				int index = session.getDescription().indexOf(" ", 55);
				return session.getDescription().substring(0, index) + " ...";
			}

			void applyTextViewFormat(TextView textView, String textContent,
					int typeFace) {
				textView.setText(textContent);
				textView.setTypeface(null, typeFace);
			}

			TextView getTextView(View view, int id) {
				return (TextView) view.findViewById(id);
			}

			private String getSessionTime(Date date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				int hours = calendar.get(Calendar.HOUR_OF_DAY);
				int minutes = calendar.get(Calendar.MINUTE);
				return String.valueOf(hours) + ":" + String.valueOf(minutes);
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
				return sessionsDay.size();
			}
		};

		listViewAgenda.setAdapter(adapterDay1);
		listViewAgenda.setSelection((Integer) adapterDay1.getItem(0));
	}

	private List<Session> getSessionsDay(List<Session> result, int dayRequest) {

		int firstDay = getFirstConferenceDay(result);
		List<Session> resultSessions = new ArrayList<>();
		for (Session session : result) {
			if (dayRequest == 1 && session.getStartTime().getDay() == firstDay)
				resultSessions.add(session);
			else if (dayRequest == 2
					&& session.getStartTime().getDay() != firstDay)
				resultSessions.add(session);
		}
		return resultSessions;
	}

	private int getFirstConferenceDay(List<Session> result) {
		int firstConfDay = result.get(0).getStartTime().getDay();
		for (Session session : result) {
			if (firstConfDay > session.getStartTime().getDay())
				firstConfDay = session.getStartTime().getDay();
		}
		return firstConfDay;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

}
