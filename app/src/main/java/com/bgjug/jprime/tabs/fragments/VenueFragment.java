package com.bgjug.jprime.tabs.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bgjug.jprime.model.VenueHall;
import com.bgjug.jprime2018.R;

public class VenueFragment extends Fragment {
	private View rootView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = (ScrollView) inflater.inflate(R.layout.fragment_venuehall,
				container, false);

		TextView title = (TextView) rootView
				.findViewById(R.id.textViewVenueHallDetailsTitle);
		title.setText(VenueHall.TITLE);

		TextView urlGoogleMaps = (TextView) rootView
				.findViewById(R.id.textViewVenueHallDetailsMapURL);
		urlGoogleMaps.setMovementMethod(LinkMovementMethod.getInstance());
		urlGoogleMaps.setText(Html.fromHtml("<a href=\""
				+ VenueHall.GOOGLE_MAPS_URL_SEC + "\" >" + VenueHall.LOCATION
				+ "</a>"));
		removeURLUnderline(urlGoogleMaps);
		
		TextView jprimeEmail = (TextView) rootView.findViewById(R.id.textViewVenueHallJprimeEmail);
		jprimeEmail.setText(VenueHall.EMAIL);
		
		TextView jprimeDescription = (TextView) rootView.findViewById(R.id.textViewVenueHallJprimeDescription);
		jprimeDescription.setText(VenueHall.JPRIME_DESCRIPTION);

		return rootView;
	}
	
	
	private void removeURLUnderline(TextView url)
	{
		Spannable spannable = (Spannable) url.getText();
		for (URLSpan urlSpan: spannable.getSpans(0, spannable.length(), URLSpan.class)) {
		    spannable.setSpan(new UnderlineSpan() {
		        public void updateDrawState(TextPaint tp) {
		            tp.setUnderlineText(false);
		        }
		    }, spannable.getSpanStart(urlSpan), spannable.getSpanEnd(urlSpan), 0);
		}
		
		url.setText(spannable);
	}

}
