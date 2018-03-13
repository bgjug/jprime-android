package com.bgjug.jprime.tabs.fragments;

import java.util.List;
import static com.bgjug.jprime.persistance.DBStatements.DATABASE_VERSION;
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

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime.rest.RestClient;
import com.bgjug.jprime.tabs.fragments.asynctasks.ReloadAsyncTask;
import com.bgjug.jprime.tabs.fragments.utils.ImageStarView;
import com.bgjug.jprime.tabs.fragments.utils.ModelUtil;
import com.bgjug.jprime2016.R;

public class SessionsFragment extends Fragment {

	private View rootView;
	private Button btnDay1;
	private Button btnDay2;
	private Button retryButton;
	private TextView errorText;
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
				loadAgenda(allSessions, 1);
			}

		});

		btnDay2.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				// btnDay1.setBackgroundResource(0);
				// setBackgroundResource(R.drawable.button_clicked);
				changeButtonClicked(btnDay2, btnDay1);
				loadAgenda(allSessions, 2);
			}

		});

		dbHelper = new DatabaseHelper(this.getActivity(), DATABASE_VERSION);
		allSessions = dbHelper.getSessions(fav);

		if (allSessions.isEmpty() && !fav) {

			reloadContent();

		} else {
			loadAgenda(allSessions, 1);
		}

		return rootView;
	}

	private void reloadContent() {

		errorText = (TextView) rootView.findViewById(R.id.errorMessageAgenda);
		errorText.setText(RestClient.getInstance().getStatusMessage());
		retryButton = (Button) rootView.findViewById(R.id.retryButtonAgenda);
		retryButton.setVisibility(0);

		retryButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				ReloadAsyncTask reloadtask = new ReloadAsyncTask(
						SessionsFragment.this);
				reloadtask.execute("");
			}
		});
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void loadAgenda(List<Session> result, int dayRequest) {
		allSessions = result;

		final List<Session> sessionsDay = ModelUtil.getSessionsDay(result,
				dayRequest, dbHelper);

		ListView listViewAgenda = (ListView) rootView
				.findViewById(R.id.agendaListView);

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
						ModelUtil.getSessionTimeAsString(session.getStartTime())
								+ "-"
								+ ModelUtil.getSessionTimeAsString(session
										.getEndTime()), Typeface.BOLD);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSessionName),
						session.getName(), Typeface.BOLD);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewHall),
						session.getHall(), Typeface.NORMAL);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSpeaker),
						ModelUtil.getFullSpeakerName(session), Typeface.ITALIC);

				applyTextViewFormat(
						getTextView(agendaItemLayout, R.id.textViewSessionInfo),
						ModelUtil.getSessionShortDscr(session), Typeface.NORMAL);

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

			void applyTextViewFormat(TextView textView, String textContent,
					int typeFace) {
				textView.setText(textContent);
				textView.setTypeface(null, typeFace);
			}

			TextView getTextView(View view, int id) {
				return (TextView) view.findViewById(id);
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

	private void changeButtonClicked(Button clickedButton, Button otherButton) {
		clickedButton.setTextColor(getResources().getColor(
				android.R.color.holo_blue_dark));
		otherButton.setTextColor(Color.GRAY);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
}
