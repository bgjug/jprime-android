package com.bgjug.jprime.tabs.fragments;

import static com.bgjug.jprime.persistance.DBStatements.DATABASE_VERSION;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.model.Speaker;
import com.bgjug.jprime.persistance.DatabaseHelper;
import com.bgjug.jprime2016.R;

public class SessionDetails extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_details);

		Bundle bundle = getIntent().getExtras();
		final Session session = bundle.getParcelable("currentSession");

		TextView textVSessionName = (TextView) findViewById(R.id.textViewSessionDetailsName);
		textVSessionName.setText(session.getName());
		textVSessionName.setTypeface(null, Typeface.BOLD);
		
		TextView textVSessionTime = (TextView) findViewById(R.id.textViewSessionDetailsTime);
		textVSessionTime.setText(session.getStartEndTime());
		textVSessionTime.setTypeface(null, Typeface.BOLD);
		
		TextView textVSessionHall = (TextView) findViewById(R.id.textViewSessionDetailsHall);
		textVSessionHall.setText(session.getHall());
		textVSessionHall.setTypeface(null, Typeface.BOLD);

		TextView textVSessionDescr = (TextView) findViewById(R.id.textViewSessionDetailsDescr);
		textVSessionDescr.setText(session.getDescription());

		TextView textVSessionWorkshopInfo = (TextView) findViewById(R.id.textViewWorkshopInfo);
		textVSessionWorkshopInfo.setText("( " + session.isWorkshop() + " )");
		textVSessionWorkshopInfo.setTypeface(null, Typeface.BOLD);
		
		TextView sessionSpeaker = (TextView) findViewById(R.id.textViewSpeakerSessionDetails);
		sessionSpeaker.setText(getSpeakerName(session));
		sessionSpeaker.setOnClickListener(new TextView.OnClickListener() {
		final DatabaseHelper dbHelper = new DatabaseHelper(SessionDetails.this, DATABASE_VERSION);
		
			@Override
			public void onClick(View v) {
				Speaker selSpeaker = dbHelper.getSpeaker(session.getSpeakerFirstName(), session.getSpeakerLastName());
				if(selSpeaker == null)
					return;
				Intent intent = new Intent(v.getContext(),
						SpeakerDetails.class);
				intent.putExtra("currentSpeaker", selSpeaker);
				startActivity(intent);
			}

		});
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	        case android.R.id.home:
	            finish();
	            return true;
	    }

	    return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
	    return true;
	}
	
	private String getSpeakerName(Session session){
		if(session.getSpeakerLastName() == null){
			return session.getSpeakerFirstName();
		}
		return session.getSpeakerFirstName() + " " + session.getSpeakerLastName();
	}
}
