package com.bgjug.jprime.model;

import java.net.URL;

public class Speaker
{
    private String lastName;
    private String firstName;
    private String email;
    private String bio;
    private URL twitterURL;
    private String headline;
    private URL picture;

    public Speaker()
    {
        super();
    }

    public Speaker(String lastName, String firstName, String email, String bio, URL twitterURL,
        String headline, URL picture)
    {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.bio = bio;
        this.twitterURL = twitterURL;
        this.headline = headline;
        this.picture = picture;
    }

    public String getlastName()
    {
        return lastName;
    }

    public void setlastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getfirstName()
    {
        return firstName;
    }

    public void setfirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getBio()
    {
        return bio;
    }

    public void setBio(String bio)
    {
        this.bio = bio;
    }

    public URL getTwitterURL()
    {
        return twitterURL;
    }

    public void setTwitterURL(URL twitterURL)
    {
        this.twitterURL = twitterURL;
    }

    public String getHeadline()
    {
        return headline;
    }

    public void setHeadline(String headline)
    {
        this.headline = headline;
    }

    public URL getPicture()
    {
        return picture;
    }

    public void setPicture(URL picture)
    {
        this.picture = picture;
    }

}
