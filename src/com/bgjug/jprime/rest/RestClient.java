package com.bgjug.jprime.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.model.Speaker;

public class RestClient {
	private short statusCode = 0;
	private static JSONArray sessionsArray = null;
	private VolleyClient volleyClient = null;
	private List<Session> sessionsList = null;
	private List<Speaker> speakersList = null;
	private static RestClient instance = null;

	private RestClient() {
		sessionsList = new ArrayList<Session>();
		speakersList = new ArrayList<Speaker>();
		volleyClient = new VolleyClient();
	}
	
	public static RestClient getInstance()
	{
		if(instance == null){
			instance = new RestClient();
		}
		
		return instance;
	}

	private JSONArray initSessions() {

		JSONObject sessionsEntity = null;
		try {

			sessionsEntity = volleyClient
					.requestJSON(ResourceConstants.JPRIME_URL + "/"
							+ ResourceConstants.SESSIONS_RESOURCE);

			sessionsArray = sessionsEntity.getJSONObject(
					ResourceConstants.EMBEDDED).getJSONArray(
					ResourceConstants.SESSIONS_RESOURCE);

		} catch (Exception e) {
			statusCode = 1;
		}

		return sessionsArray;
	}

	public List<Speaker> getSpeakers() {

		if (speakersList.isEmpty()) {
			getSessions();
		}

		return speakersList;
	}

	public List<Session> getSessions() {
		Session session = null;
		Speaker speaker = null;
		SessionHandler sessionHandler = null;
		SpeakerHandler speakerHandler = null;

		if (!sessionsList.isEmpty()) {
			return sessionsList;
		}
		
		initSessions();

		try {

			for (int i = 0; i < sessionsArray.length(); i++) {

				String submissionURL = sessionsArray.getJSONObject(i)
						.getJSONObject(ResourceConstants.LINKS)
						.getJSONObject(ResourceConstants.SUBMISSION_RESOURCE)
						.getString("href");

				JSONObject submissionObject = volleyClient
						.requestJSON(submissionURL);

				sessionHandler = new SessionHandler(
						sessionsArray.getJSONObject(i), submissionObject);
				session = sessionHandler.parseSession();

				JSONObject speakerObject = getSpeakerContent(submissionObject);
				speakerHandler = new SpeakerHandler(speakerObject);
				speaker = speakerHandler.parseSpeaker();
				session.setSpeaker(speaker);
				speakersList.add(speaker);

				String hallURL = sessionsArray.getJSONObject(i)
						.getJSONObject(ResourceConstants.LINKS)
						.getJSONObject("hall").getString("href");
				String hallName = getHallName(hallURL);
				session.setHall(hallName);

				sessionsList.add(session);
			}
		} catch (Exception e) {
			statusCode = 1;
		}

		return sessionsList;
	}

	public void reloadContent() {
		sessionsList = new ArrayList<Session>();
		speakersList = new ArrayList<Speaker>();
	}

	private JSONObject getSpeakerContent(JSONObject submissionObject)
			throws InterruptedException, ExecutionException, TimeoutException,
			JSONException {

		String speakerURL = "";
		JSONObject speaker = null;

		speakerURL = submissionObject.getJSONObject(ResourceConstants.LINKS)
				.getJSONObject(ResourceConstants.SPEAKER_RESOURCE)
				.getString("href");

		speaker = volleyClient.requestJSON(speakerURL);

		return speaker;
	}

	private String getHallName(String hallURL) throws InterruptedException,
			ExecutionException, TimeoutException, JSONException {

		JSONObject hall;
		String hallName = null;

		hall = volleyClient.requestJSON(hallURL);
		hallName = hall.getString("name");

		return hallName;
	}

	public void stopVolleyRequestQueue()
	{
		volleyClient.stopReuestQueue();
	}
	
	public void startVolleyReuestQueue()
	{
		volleyClient.startReuestQueue();
	}
	
	public short getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(short statusCode) {
		this.statusCode = statusCode;
	}
}
