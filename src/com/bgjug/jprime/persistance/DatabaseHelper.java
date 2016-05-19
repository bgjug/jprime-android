package com.bgjug.jprime.persistance;

import static com.bgjug.jprime.persistance.DBStatements.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.model.Speaker;

public class DatabaseHelper extends SQLiteOpenHelper {

	public DatabaseHelper(Context context, int version) {
		super(context, "jprime2016", null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_SESSION_TABLE);
		db.execSQL(CREATE_SPEAKER_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE + TABLE_SESSION);
		db.execSQL(DROP_TABLE + TABLE_SPEAKER);

		db.execSQL(CREATE_SESSION_TABLE);
		db.execSQL(CREATE_SPEAKER_TABLE);
	}

	public void addSessions(List<Session> result) {
		SQLiteDatabase db = getReadableDatabase();
		for (Session session : result) {
			ContentValues cv = getSessionContentValue(session);
			db.insert(TABLE_SESSION, null, cv);
		}

	}

	public void addSpeakers(List<Speaker> result) {
		SQLiteDatabase db = getReadableDatabase();
		for (Speaker session : result) {
			ContentValues cv = getSpeakerContentValue(session);
			db.insert(TABLE_SPEAKER, null, cv);
		}

	}

	private ContentValues getSessionContentValue(Session session) {
		ContentValues cv = new ContentValues();

		cv.put(FIELD_NAME, session.getName());
		cv.put(FIELD_DESCRIPTION, session.getDescription());
		cv.put(FIELD_SPEAKER, session.getSpeaker().getfirstName() + " "
				+ session.getSpeaker().getlastName());
		cv.put(FIELD_COSPEAKER, session.getCoSpeaker() != null ? session
				.getCoSpeaker().getfirstName()
				+ session.getCoSpeaker().getlastName() : null);
		cv.put(FIELD_HALL, session.getHall());
		cv.put(FIELD_STARTTIME,
				String.valueOf(session.getStartTime().getTime()));
		cv.put(FIELD_ENDTIME, String.valueOf(session.getEndTime().getTime()));
		cv.put(FIELD_ISFAVORITE, session.getIsFavorite());
		return cv;
	}

	public List<Session> getSessions() {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = new String[] { FIELD_NAME, FIELD_DESCRIPTION,
				FIELD_SPEAKER, FIELD_COSPEAKER, FIELD_HALL, FIELD_STARTTIME,
				FIELD_ENDTIME, FIELD_ISFAVORITE };
		Cursor c = db.query(TABLE_SESSION, columns, null, null, null, null,
				null);

		List<Session> result = new ArrayList<Session>();
		while (c.moveToNext()) {
			Speaker speaker = new Speaker();
			speaker.setfirstName(c.getString(2));
			Speaker coSpeaker = new Speaker();
			coSpeaker.setfirstName(c.getString(3));

			result.add(new Session(new Date(Long.parseLong(c.getString(5))),
					new Date(Long.parseLong(c.getString(6))), c.getString(4), c
							.getString(0), c.getString(1), speaker, coSpeaker, c.getInt(7)));
		}

		return result;
	}

	private ContentValues getSpeakerContentValue(Speaker speaker) {
		ContentValues cv = new ContentValues();

		cv.put(FIELD_LASTNAME, speaker.getlastName());
		cv.put(FIELD_FIRSTNAME, speaker.getfirstName());
		cv.put(FIELD_EMAIL, speaker.getEmail());
		cv.put(FIELD_BIO, speaker.getBio());
		cv.put(FIELD_TWITTERURL, speaker.getTwitterURL());
		cv.put(FIELD_HEADLINE, speaker.getHeadline());
		cv.put(FIELD_PICTURE, speaker.getPicture());

		return cv;
	}

	public List<Speaker> getSpeakers() {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = new String[] { FIELD_LASTNAME, FIELD_FIRSTNAME,
				FIELD_EMAIL, FIELD_BIO, FIELD_TWITTERURL, FIELD_HEADLINE,
				FIELD_PICTURE };
		Cursor c = db.query(TABLE_SPEAKER, columns, null, null, null, null,
				null);

		List<Speaker> result = new ArrayList<Speaker>();
		while (c.moveToNext()) {
			result.add(new Speaker(c.getString(0), c.getString(1), c
					.getString(2), c.getString(3), c.getString(4), c
					.getString(5), c.getBlob(6)));
		}

		return result;
	}

	public void updateIsFavotire(Session session, int favorite) {
		ContentValues cv = new ContentValues();
		cv.put(FIELD_ISFAVORITE, Integer.valueOf(favorite));

		SQLiteDatabase db = getWritableDatabase();

		db.update(TABLE_SESSION, cv,
				String.format("%s = '%s'", FIELD_NAME, session.getName()), null);

	}
}
