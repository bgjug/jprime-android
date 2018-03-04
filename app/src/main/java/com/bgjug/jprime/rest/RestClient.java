package com.bgjug.jprime.rest;

import java.net.MalformedURLException;
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

	private static final String ERROR_GETTING_CONTENT = "Unfortunately error occured while trying to get conntent from Jprime";
	private static final String ERROR_NO_CONNECTION = "There is no network connection right now";
	private static final String ERROR_NO_CONTENT = "Can not get content from Jprime";
	private String statusMessage;
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

	public static RestClient getInstance() {
		if (instance == null) {
			instance = new RestClient();
		}

		return instance;
	}

	private JSONArray initSessions() throws InterruptedException,
			ExecutionException, TimeoutException, JSONException {

		JSONObject sessionsEntity = volleyClient.requestJSON(ResourceConstants.JPRIME_URL
				+ "/" + ResourceConstants.SESSIONS_RESOURCE);

		sessionsArray = sessionsEntity
				.getJSONObject(ResourceConstants.EMBEDDED).getJSONArray(
						ResourceConstants.SESSIONS_RESOURCE);

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

		try {
			initSessions();

			for (int i = 0; i < sessionsArray.length(); i++) {
				String submissionURL = sessionsArray.getJSONObject(i)
						.getJSONObject(ResourceConstants.LINKS)
						.getJSONObject(ResourceConstants.SUBMISSION_RESOURCE)
						.getString("href");

				JSONObject submissionObject;
				try {
					submissionObject = volleyClient
							.requestJSON(submissionURL);
				}catch(Exception e){
					System.out.println("Can not retrieve submission: " + submissionURL);
					continue;
				}

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

		} catch (InterruptedException e) {
			statusMessage = ERROR_GETTING_CONTENT;
		} catch (ExecutionException e) {
			statusMessage = ERROR_GETTING_CONTENT;
		} catch (TimeoutException e) {
			statusMessage = ERROR_NO_CONNECTION;
		} catch (JSONException e) {
			statusMessage = ERROR_GETTING_CONTENT;
		} catch (MalformedURLException e) {
			statusMessage = ERROR_GETTING_CONTENT;
		}

		if (sessionsList.isEmpty() && statusMessage == null) {
			statusMessage = ERROR_NO_CONTENT;
		}

		return sessionsList;
	}

	public void reloadContent() {
		statusMessage = null;
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

	public void stopVolleyRequestQueue() {
		volleyClient.stopReuestQueue();
	}

	public void startVolleyReuestQueue() {
		volleyClient.startReuestQueue();
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}
