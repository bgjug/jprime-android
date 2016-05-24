package com.bgjug.jprime.tabs.fragments.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.persistance.DatabaseHelper;

public class ModelUtil {
	@SuppressWarnings("deprecation")
	public static List<Session> getSessionsDay(List<Session> result, int dayRequest, DatabaseHelper dbHelper) {

		List<Session> resultSessions = new ArrayList<Session>();
		if (result == null || result.isEmpty())
			return resultSessions;
		int firstDay = getFirstConferenceDay(dbHelper);
		for (Session session : result) {
			if (dayRequest == 1 && session.getStartTime().getDay() == firstDay)
				resultSessions.add(session);
			else if (dayRequest == 2
					&& session.getStartTime().getDay() != firstDay)
				resultSessions.add(session);
		}
		return resultSessions;
	}


	@SuppressWarnings("deprecation")
	public static int getFirstConferenceDay(DatabaseHelper dbHelper) {
		List<Session> sessions = dbHelper.getSessions(false);
		int firstConfDay = sessions.get(0).getStartTime().getDay();
				
		for (Session session : sessions) {
			if (firstConfDay > session.getStartTime().getDay())
				firstConfDay = session.getStartTime().getDay();
		}
		return firstConfDay;
	}
	
	public static String getSessionTimeAsString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		String minutes = calendar.get(Calendar.MINUTE) == 0 ? "00"
				: Integer.toString(calendar.get(Calendar.MINUTE));
		return Integer.toString(hours) + ":" + minutes;
	}
	
	public static String getFullSpeakerName(Session session) {
		if (session.getSpeaker().getlastName() == null)
			return session.getSpeaker().getfirstName();
		return session.getSpeaker().getfirstName() + " "
				+ session.getSpeaker().getlastName();
	}

	public static String getSessionShortDscr(Session session) {
		int index = session.getDescription().indexOf(" ", 55);
		return session.getDescription().substring(0, index) + " ...";
	}
}
