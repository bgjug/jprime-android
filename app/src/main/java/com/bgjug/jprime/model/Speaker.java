package com.bgjug.jprime.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Speaker implements Parcelable {
	private String lastName;
	private String firstName;
	private String email;
	private String bio;
	private String twitterURL;
	private String headline;
	private byte[] picture;

	public Speaker() {
		super();
	}

	public Speaker(String lastName, String firstName, String email, String bio,
			String twitterURL, String headline, byte[] picture) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.bio = bio;
		this.twitterURL = twitterURL;
		this.headline = headline;
		this.picture = picture;
	}

	public String getlastName() {
		return lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getTwitterURL() {
		return twitterURL;
	}

	public void setTwitterURL(String twitterURL) {
		this.twitterURL = twitterURL;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public byte[] getPicture() {
		return picture;
	}

	public void setPicture(byte[] picture) {
		this.picture = picture;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.bio);
		dest.writeString(this.twitterURL);
		dest.writeInt(this.picture.length); 
		dest.writeByteArray(this.picture);

	}

	public Speaker(Parcel in) {
		this.firstName = in.readString();
		this.lastName = in.readString();
		this.bio = in.readString();
		this.twitterURL = in.readString();
		this.picture= new byte[in.readInt()]; 
		in.readByteArray(this.picture);
	}

	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Speaker createFromParcel(Parcel in) {
			return new Speaker(in);
		}

		public Speaker[] newArray(int size) {
			return new Speaker[size];
		}
	};

}
