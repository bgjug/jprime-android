package com.bgjug.jprime.persistance;

public interface DBStatements {
	static final String TABLE_SESSION = "SESSIONS";
	static final String TABLE_SPEAKER = "SPEAKER";

	static final String FIELD_NAME = "NAME";
	static final String FIELD_DESCRIPTION = "DESCRIPTION";
	static final String FIELD_SPEAKER_FIRST_NAME = "SPEAKER";
	static final String FIELD_COSPEAKER = "COSPEAKER";
	static final String FIELD_HALL = "HALL";
	static final String FIELD_STARTTIME = "STARTTIME";
	static final String FIELD_ENDTIME = "ENDTIME";
	static final String FIELD_ISFAVORITE = "ISFAVORITE";
	static final String FIELD_SPEAKER_LAST_NAME ="SPEAKER_LASTNAME";
	static final String CREATE_SESSION_TABLE = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT,"
			+ " %s TEXT, %s TEXT, %s INTEGER, %s TEXT);", TABLE_SESSION, FIELD_NAME, FIELD_DESCRIPTION, FIELD_SPEAKER_FIRST_NAME, FIELD_COSPEAKER, FIELD_HALL, FIELD_STARTTIME, FIELD_ENDTIME, FIELD_ISFAVORITE, FIELD_SPEAKER_LAST_NAME);
	
	static final String FIELD_LASTNAME = "LASTNAME";
	static final String FIELD_FIRSTNAME = "FIRSTNAME";
	static final String FIELD_EMAIL = "EMAIL";
	static final String FIELD_BIO = "BIO";
	static final String FIELD_TWITTERURL = "TWITTERURL";
	static final String FIELD_HEADLINE = "HEADLINE";
	static final String FIELD_PICTURE = "PICTURE";
	static final String CREATE_SPEAKER_TABLE = String.format("CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT,"
			+ " %s TEXT, %s BLOB);", TABLE_SPEAKER, FIELD_LASTNAME, FIELD_FIRSTNAME, FIELD_EMAIL, FIELD_BIO, FIELD_TWITTERURL, FIELD_HEADLINE, FIELD_PICTURE);

	static final String DROP_TABLE = "DROP TABLE ";
}
