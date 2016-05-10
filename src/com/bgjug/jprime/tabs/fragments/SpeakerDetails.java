package com.bgjug.jprime.tabs.fragments;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgjug.jprime.model.Speaker;
import com.bgjug.jprime.tabs.fragments.utils.BitmapUtils;
import com.bgjug.jprime2016.R;

public class SpeakerDetails extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speaker_details);
        
		Bundle bundle = getIntent().getExtras();
		Speaker speaker = bundle.getParcelable("currentSpeaker");
		
		TextView textVSpeakerName = (TextView) findViewById(R.id.textViewSpeakerDetailName);
		textVSpeakerName.setText(speaker.getfirstName() + " " + speaker.getlastName());
		textVSpeakerName.setTypeface(null, Typeface.BOLD);
		
		TextView textVSpeakerBio = (TextView) findViewById(R.id.textViewSpeakerDetailBio);
		textVSpeakerBio.setText(speaker.getBio());
		
		TextView textVSpeakerTwitter = (TextView) findViewById(R.id.textViewTwitter);
		String twitterURL = speaker.getTwitterURL();
		textVSpeakerTwitter.setText(twitterURL);
		if(twitterURL == null || twitterURL.isEmpty())
			((ImageView) findViewById(R.id.imageViewTwitter)).setVisibility(View.GONE);
		
		byte[] picByteArray = speaker.getPicture();
		Bitmap bmp = BitmapUtils.decodeSampledBitmapFromResource(
				picByteArray, 50, 50, 0);
		// BitmapFactory.decodeByteArray(picByteArray, 0,
		// picByteArray.length);
		final ImageView speakerImageView = (ImageView)findViewById(R.id.imageViewSpeakerDetailPicture);

		speakerImageView.setImageBitmap(BitmapUtils
				.getRoundedCornerBitmap(bmp, 10));
		speakerImageView.post(new Runnable() {
			@Override
			public void run() {
				speakerImageView.setVisibility(View.GONE);
				speakerImageView.setVisibility(View.VISIBLE);
			}
		});
		
		
		
	}
	

}
