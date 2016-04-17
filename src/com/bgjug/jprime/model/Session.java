package com.bgjug.jprime.model;

import java.util.Date;

public class Session
{  
    private Date startTime;
    private Date endTime;
    private String hall;
    private String name;
    private String description;
    private Speaker speaker;
    private Speaker coSpeaker;
    
    public Session()
    {
        super();
    }

    public Session(Date startTime, Date endTime, String hall, String name, String description,
        Speaker speaker, Speaker coSpeaker)
    {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.hall = hall;
        this.name = name;
        this.description = description;
        this.speaker = speaker;
        this.coSpeaker = coSpeaker;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getHall()
    {
        return hall;
    }

    public void setHall(String hall)
    {
        this.hall = hall;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Speaker getSpeaker()
    {
        return speaker;
    }

    public void setSpeaker(Speaker speaker)
    {
        this.speaker = speaker;
    }

    public Speaker getCoSpeaker()
    {
        return coSpeaker;
    }

    public void setCoSpeaker(Speaker coSpeaker)
    {
        this.coSpeaker = coSpeaker;
    }
    
}
