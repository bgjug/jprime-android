package com.bgjug.jprime.tabs.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime2016.R;

public class SessionDetails extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_details);

		Bundle bundle = getIntent().getExtras();
		Session session = bundle.getParcelable("currentSession");

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

	}
}
