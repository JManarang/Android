package com.example.dannygonzalez.storyfinder;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dannygonzalez.storyfinder.MeetUp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Jonah on 12/8/2016.
 */

public class JsonRequest extends Request<List<MeetUp>> {

    // Success listener implemented in controller
    private Response.Listener<List<MeetUp>> successListener;

    /**
     * Class constructor
     * @param method            Request method
     * @param url               url to API
     * @param successListener   success listener
     * @param errorListener     failure listener
     */
    public JsonRequest( int method,
                        String url,
                        Response.Listener<List<MeetUp>> successListener,
                        Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.successListener = successListener;
    }

    @Override
    protected Response<List<MeetUp>> parseNetworkResponse(NetworkResponse response) {
        // Convert byte[] data received in the response to String
        String jsonString = new String(response.data);
        List<MeetUp> meetUps ;
        JSONArray jsonObject;
        JSONArray jsonArray;
        Log.i(this.getClass().getName(), jsonString);
        // Try to convert JsonString to list of movies
        try {
            // Convert JsonString to JSONObject
            jsonObject = new JSONArray(jsonString);
            // Get list of movies from received JSON
            meetUps = MeetUp.parseJson(jsonObject);

        }
        // in case of exception, return volley error
        catch (JSONException e) {
            e.printStackTrace();
            // return new volley error with message
            return Response.error(new VolleyError("Failed to process the request"));
        }
        // return list of movies
        return Response.success(meetUps, getCacheEntry());
    }

    @Override
    protected void deliverResponse(List<MeetUp> meetUps) {
        successListener.onResponse(meetUps);
    }
}
