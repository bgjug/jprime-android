package com.bgjug.jprime.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bgjug.jprime.model.Session;
import com.bgjug.jprime.model.Speaker;

public class RestClient
{
    private JSONArray sessions = null;
    private short statusCode = 0;

    public RestClient()
    {
        initialize();
    }

    private void initialize()
    {
        JSONObject object;
        try
        {
            object = new JSONObject(getWebContent("http://demo5590976.mockable.io/sessions")
                .toString());
            sessions = new JSONArray(object.getJSONObject("_embedded").getJSONArray("sessions")
                .toString());
        }
        catch (Exception e)
        {
            statusCode = 1;
        }
    }

    public List<Session> getSessions()
    {
        Session session = null;
        Speaker speaker = null;
        SessionHandler sessionHandler = null;
        SpeakerHandler speakerHandler = null;
        List<Session> result = new ArrayList<Session>();

        try
        {
            for (int i = 0; i < sessions.length(); i++)
            {
                String submissionURL = sessions.getJSONObject(i).getJSONObject("_links")
                    .getJSONObject("submission").getString("href");
                JSONObject submissionObject = new JSONObject(getWebContent(submissionURL)
                    .toString());

                sessionHandler = new SessionHandler(sessions.getJSONObject(i), submissionObject);
                session = sessionHandler.parseSession();

                JSONObject speakerObject = getSpeakerContent(submissionObject);
                speakerHandler = new SpeakerHandler(speakerObject);
                speaker = speakerHandler.parseSpeaker();
                session.setSpeaker(speaker);

                String hallURL = sessions.getJSONObject(i).getJSONObject("_links")
                    .getJSONObject("hall").getString("href");
                String hallName = getHallName(hallURL);

                session.setHall(hallName);

                result.add(session);
            }
        }
        catch (Exception e)
        {
            statusCode = 1;
        }

        return result;
    }

    private StringBuilder getWebContent(final String url) throws InterruptedException
    {
        final StringBuilder stringBuilder = new StringBuilder();

        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                InputStream inputStream = null;

                try
                {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(url);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    StatusLine status = httpResponse.getStatusLine();
                    int statusCode = status.getStatusCode();

                    if (statusCode == 200)
                    {
                        HttpEntity entity = httpResponse.getEntity();
                        inputStream = entity.getContent();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(
                            inputStream));
                        String line = null;

                        while ((line = reader.readLine()) != null)
                        {
                            stringBuilder.append(line);
                        }
                    }
                }
                catch (Exception e)
                {
                    statusCode = 1;
                }
                finally
                {
                    if (inputStream != null)
                    {
                        try
                        {
                            inputStream.close();
                        }
                        catch (IOException e)
                        {
                            // TODO
                        }
                    }
                }
            }
        });

        thread.start();
        thread.join();

        return stringBuilder;
    }

    private JSONObject getSpeakerContent(JSONObject submissionObject) throws InterruptedException,
        JSONException
    {
        String speakerURL = "";
        JSONObject speaker = null;

        speakerURL = submissionObject.getJSONObject("_links").getJSONObject("speaker")
            .getString("href");
        StringBuilder speakerContent = getWebContent(speakerURL);
        speaker = new JSONObject(speakerContent.toString());

        return speaker;
    }

    private String getHallName(String hallURL) throws InterruptedException, JSONException
    {
        JSONObject hall;
        String hallName = null;

        StringBuilder hallContent = getWebContent(hallURL);
        hall = new JSONObject(hallContent.toString());
        hallName = hall.getString("name");

        return hallName;
    }

    public short getStatusCode()
    {
        return statusCode;
    }

    public void setStatusCode(short statusCode)
    {
        this.statusCode = statusCode;
    }

}
