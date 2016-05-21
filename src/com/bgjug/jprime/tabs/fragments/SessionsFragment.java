package com.bgjug.jprime.tabs.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.tabs.fragments.asynctasks.AgendaAsyncTask;
import com.bgjug.jprime.tabs.fragments.utils.ImageStarView;
import com.bgjug.jprime2016.R;

public class SessionsFragment extends Fragment {

	private View rootView;
	private Button btnDay1;
	private Button btnDay2;
	private BaseAdapter adapterAgenda;
	private List<Session> allSessions;
	private DatabaseHelper dbHelper;
	private boolean fav;

	public SessionsFragment(boolean fav) {
		this.fav = fav;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_agenda, container, false);

		btnDay1 = (Button) rootView.findViewById(R.id.buttonDay1);
		btnDay2 = (Button) rootView.findViewById(R.id.buttonDay2);
		changeButtonClicked(btnDay1, btnDay2);
		btnDay1.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// btnDay1.setBackgroundResource(R.drawable.button_clicked);
				// btnDay2.setBackgroundResource(0);
				changeButtonClicked(btnDay1, btnDay2);
				loadAgenda(allSessions, 1, false);
			}

		});

		btnDay2.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// btnDay1.setBackgroundResource(0);
				// setBackgroundResource(R.drawable.button_clicked);
				changeButtonClicked(btnDay2, btnDay1);
				loadAgenda(allSessions, 2, false);
			}

		});

		dbHelper = new DatabaseHelper(this.getActivity(), 5);
		allSessions = dbHelper.getSessions(fav);

		if ((allSessions == null || allSessions.isEmpty()) && !fav) {
			// show alert dialog that shows to reload content
			
			AgendaAsyncTask agendaTask = new AgendaAsyncTask(
					SessionsFragment.this);
			agendaTask.execute("");
			
		} else{
			loadAgenda(allSessions, 1, false);
		}
		
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void loadAgenda(List<Session> result, int dayRequest,
			boolean dbInsert) {
		allSessions = result;

		final List<Session> sessionsDay = getSessionsDay(result, dayRequest);

		ListView listViewAgenda = (ListView) rootView
				.findViewById(R.id.agendaListView);
		TextView noItem = (TextView) rootView
				.findViewById(R.id.textView_noItems);
		if (sessionsDay == null || sessionsDay.isEmpty()) {
			noItem.setVisibility(View.VISIBLE);
			noItem.setText("There are no sessions");
			listViewAgenda.setAdapter(null);
			return;
		}
		noItem.setVisibility(View.GONE);

		if (dbInsert)
			dbHelper.addSessions(result);

		adapterAgenda = new BaseAdapter() {
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
				final Session session = sessionsDay.get(position);

				ImageStarView imageFav = (ImageStarView) agendaItemLayout
						.findViewById(R.id.imageFavorite);
				imageFav.setImageResource(session.getIsFavorite() == 0 ? R.drawable.star_empty512
						: R.drawable.star_fill512);
				imageFav.setActive(session.getIsFavorite() != 0);
				imageFav.setOnClickListener(new ImageView.OnClickListener() {

					@Override
					public void onClick(View v) {
						ImageStarView image = ((ImageStarView) v);
						image.changeState();
						if (image.isActivated()) {
							image.setImageResource(R.drawable.star_fill512);
							dbHelper.updateIsFavotire(session, 1);
							session.setIsFavorite(1);
						} else {
							image.setImageResource(R.drawable.star_empty512);
							dbHelper.updateIsFavotire(session, 0);
							session.setIsFavorite(0);
						}
					}

				});

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
						getSpeakerFullName(session), Typeface.ITALIC);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSessionInfo),
						getSessionShortDscr(session), Typeface.NORMAL);

				agendaItemLayout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(v.getContext(),
								SessionDetails.class);
						intent.putExtra("currentSession", session);
						startActivity(intent);
					}

				});

				return agendaItemLayout;
			}

			private String getSpeakerFullName(final Session session) {
				if (session.getSpeaker().getlastName() == null)
					return session.getSpeaker().getfirstName();
				return session.getSpeaker().getfirstName() + " "
						+ session.getSpeaker().getlastName();
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
				String minutes = calendar.get(Calendar.MINUTE) == 0 ? "00"
						: Integer.toString(calendar.get(Calendar.MINUTE));
				return Integer.toString(hours) + ":" + minutes;
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

		listViewAgenda.setAdapter(adapterAgenda);
		listViewAgenda.setSelection((Integer) adapterAgenda.getItem(0));
	}

	private List<Session> getSessionsDay(List<Session> result, int dayRequest) {

		List<Session> resultSessions = new ArrayList<Session>();
		if (result == null || result.isEmpty())
			return resultSessions;
		int firstDay = getFirstConferenceDay(result);
		for (Session session : result) {
			if (dayRequest == 1 && session.getStartTime().getDay() == firstDay)
				resultSessions.add(session);
			else if (dayRequest == 2
					&& session.getStartTime().getDay() != firstDay)
				resultSessions.add(session);
		}
		return resultSessions;
	}

	private void changeButtonClicked(Button clickedButton, Button otherButton) {
		clickedButton.setTextColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		otherButton.setTextColor(Color.GRAY);
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
