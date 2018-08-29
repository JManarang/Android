package com.example.dannygonzalez.storyfinder;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.dannygonzalez.storyfinder.JsonRequest;
import com.example.dannygonzalez.storyfinder.VolleySingleton;

import java.util.List;

/**
 * Created by Jonah on 12/7/2016.
 */

public class JsonController {
    private final int TAG = 100;
    private OnResponseListener responseListener;

    public JsonController(OnResponseListener responseListener){
        this.responseListener = responseListener;
    }

    public void sendRequest(String query) {//, List<MeetUp> groups
        //Create Location manager and get the context using the App class
         LocationManager lm = (LocationManager)App.getContext().getSystemService(Context.LOCATION_SERVICE);
         Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude;
        double latitude;

        if(location != null){
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } else{
            Toast.makeText(App.getContext(), "User location not found, setting location to San Francisco!", Toast.LENGTH_LONG).show();
            longitude = -122.4194;
            latitude = 37.7749;
        }

        int method = Request.Method.GET;
        //Get lat and long from LocationManager and get query from search NOTE: I removed '-' because it was giving me error
        String url = "https://api.meetup.com/find/groups?key=49561d38b566b677d17617831725857&sign=true&photo-host=public&lon="+longitude+"&text=" + Uri.encode(query) + "&radius=10&lat="+latitude+"&page=100";

        JsonRequest request = new JsonRequest(method, url, new Response.Listener<List<MeetUp>>() {
            @Override
            public void onResponse(List<MeetUp> meetUps) {
                responseListener.onSuccess(meetUps);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFailure(error.getMessage());
                    }
                }
        );

        request.setTag(TAG);

        VolleySingleton.getInstance(App.getContext()).addToRequestQueue(request);
    }

    public void cancelAllRequests(){
        VolleySingleton.getInstance(App.getContext()).cancelAllRequests(TAG);
    }

    public interface OnResponseListener{
        void onSuccess(List<MeetUp> meetUps);
        void onFailure(String errorMessage);
    }
}
