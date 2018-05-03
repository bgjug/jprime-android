package com.bgjug.jprime.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

import com.bgjug.jprime.tabs.fragments.utils.ModelUtil;

public class Session implements Parcelable {
	private Date startTime;
	private Date endTime;
	private String hall;
	private String name;
	private String description;
	private Speaker speaker;
	private Speaker coSpeaker;
	private String startEndTime;
	private Integer isFavorite = 0;
	private String speakerFirstName;
	private String speakerLastName;
	private String type;

	public Session() {
		super();
	}

	public Session(Date startTime, Date endTime, String hall, String name,
                   String description, Speaker speaker, Speaker coSpeaker,
                   Integer isFavorite, String type) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.hall = hall;
		this.name = name;
		this.description = description;
		this.speaker = speaker;
		this.coSpeaker = coSpeaker;
		this.isFavorite = isFavorite;
		this.type = type;
	}

	public Session(Date startTime, Date endTime, String hall, String name,
			String description, Speaker speaker, Speaker coSpeaker) {
		this(startTime, endTime, hall, name, description, speaker, coSpeaker, 0, "");
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getHall() {
		return hall;
	}

	public void setHall(String hall) {
		this.hall = hall;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Speaker getSpeaker() {
		return speaker;
	}

	public void setSpeaker(Speaker speaker) {
		this.speaker = speaker;
	}

	public Speaker getCoSpeaker() {
		return coSpeaker;
	}

	public void setCoSpeaker(Speaker coSpeaker) {
		this.coSpeaker = coSpeaker;
	}

	public String getStartEndTime() {
		return startEndTime;
	}

	public void setStartEndTime(String startEndTime) {
		this.startEndTime = startEndTime;
	}

	public Integer getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(Integer isFavorite) {
		this.isFavorite = isFavorite;
	}

	public String getSpeakerFirstName() {
		return speakerFirstName;
	}

	public void setSpeakerFirstName(String speakerFirstName) {
		this.speakerFirstName = speakerFirstName;
	}

	public String getSpeakerLastName() {
		return speakerLastName;
	}

	public void setSpeakerLastName(String speakerLastName) {
		this.speakerLastName = speakerLastName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(ModelUtil.getSessionTimeAsString(this.startTime)
				+ " - " + ModelUtil.getSessionTimeAsString(this.endTime));
		dest.writeString(this.hall);
		dest.writeString(this.description);
		dest.writeString(this.getSpeaker().getfirstName());
		dest.writeString(this.getSpeaker().getlastName());
		dest.writeString(this.type);
	}

	public Session(Parcel in) {
		this.name = in.readString();
		this.startEndTime = in.readString();
		this.hall = in.readString();
		this.description = in.readString();
		this.speakerFirstName = in.readString();
		this.speakerLastName = in.readString();
		this.type = in.readString();
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Session createFromParcel(Parcel in) {
			return new Session(in);
		}

		public Session[] newArray(int size) {
			return new Session[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}
}
