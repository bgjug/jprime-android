package com.bgjug.jprime.rest;

import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Base64;

import com.bgjug.jprime.model.Speaker;

public class SpeakerHandler {
	private JSONObject speakerJSON = null;

	public SpeakerHandler(JSONObject speaker) {
		this.speakerJSON = speaker;
	}

	public Speaker parseSpeaker() throws JSONException, MalformedURLException {
		Speaker speaker = new Speaker();
		speaker.setfirstName(speakerJSON.getString("firstName"));
		speaker.setlastName(speakerJSON.getString("lastName"));
		speaker.setEmail(speakerJSON.getString("email"));
		speaker.setBio(speakerJSON.getString("bio"));
		speaker.setHeadline(speakerJSON.getString("headline"));
		speaker.setTwitterURL(speakerJSON.getString("twitter"));
		speaker.setPicture(Base64.decode(speakerJSON.getString("picture"),
				Base64.DEFAULT));

		return speaker;
	}
}
