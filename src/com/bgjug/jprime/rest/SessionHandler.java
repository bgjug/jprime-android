package com.bgjug.jprime.rest;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.bgjug.jprime.model.Session;

public class SessionHandler
{
    private JSONObject sessionJSON = null;
    private JSONObject submissionJSON = null;

    public SessionHandler(JSONObject session, JSONObject submission)
    {
        this.sessionJSON = session;
        this.submissionJSON = submission;
    }

    public Session parseSession() throws JSONException
    {
        Session session = new Session();

        session.setDescription(submissionJSON.getString("description"));
        session.setName(submissionJSON.getString("title"));

        Date startTime = handleDate(sessionJSON.getJSONObject("startTime"));
        Date endTime = handleDate(sessionJSON.getJSONObject("endTime"));

        session.setStartTime(startTime);
        session.setEndTime(endTime);

        return session;
    }

    @SuppressWarnings("deprecation")
	private Date handleDate(JSONObject dateObject) throws JSONException
    {
        Date date = new Date();
        date.setYear(dateObject.getInt("year"));
        date.setDate(dateObject.getInt("dayOfMonth"));
        date.setHours(dateObject.getInt("hourOfDay"));
        date.setMinutes(dateObject.getInt("minuteOfHour"));

        return date;
    }

}
