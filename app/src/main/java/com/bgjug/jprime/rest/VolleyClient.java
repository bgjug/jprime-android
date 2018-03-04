package com.bgjug.jprime.rest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.RequestFuture;

public class VolleyClient {

	private RequestQueue mRequestQueue = null;

	public VolleyClient() {

		// Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024);
		// // 1MB

		Network network = new BasicNetwork(new HurlStack());

		mRequestQueue = new RequestQueue(new NoCache(), network);

		mRequestQueue.start();
	}

	public JSONObject requestJSON(String url) throws InterruptedException,
			ExecutionException, TimeoutException {

		RequestFuture<JSONObject> future = RequestFuture.newFuture();

		JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
				future, future);

		stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
		mRequestQueue.add(stringRequest);

		return future.get(7, TimeUnit.SECONDS);
	}
	
	public void stopReuestQueue()
	{
		mRequestQueue.stop();
	}
	
	public void startReuestQueue()
	{
		mRequestQueue.stop(); 
	}
}
