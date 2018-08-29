package com.example.dannygonzalez.storyfinder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonah on 12/8/2016. Lol, dang good job! I was really dreading doing this!
 */

public class MeetUp {
    private String name;
    private int members;
    private String description;
    private String city;
    private String state;
    private int groupId;
    private int lat;
    private int lon;
    private String thumbImage;

    public static List<MeetUp> parseJson(JSONArray jsonArray) throws JSONException {
        List<MeetUp> meetUps = new ArrayList<>();
        // Check if the JSONObject has object with key "results" since that's what Meetup JsonObjects start with
        //if(jsonObject.has("score")){
            // Get JSONArray from JSONObject
            //JSONArray jsonArray = jsonObject.getJSONArray("score");
            for(int i = 0; i < jsonArray.length(); i++){
                // Create new MeetUp object from each JSONObject in the JSONArray
                meetUps.add(new MeetUp(jsonArray.getJSONObject(i)));
            }
        //}

        return meetUps;
    }

    /** Meet up Card should have
     *  Group Name
     *  Location: City, State
     *  Members
     *  Visibility
     *  Description
     *  but it will have lat, lon, and id but not on card
     */
    private MeetUp(JSONObject jsonObject) throws JSONException{
        if(jsonObject.has("lon")) this.setLon(jsonObject.getInt("lon"));
        if(jsonObject.has("lat")) this.setLat(jsonObject.getInt("lat"));
        if(jsonObject.has("members")) this.setMembers(jsonObject.getInt("members"));
        if(jsonObject.has("name")) this.setName(jsonObject.getString("name"));
        if(jsonObject.has("description")) this.setDescription(jsonObject.getString("description"));
        if(jsonObject.has("city")) this.setCity(jsonObject.getString("city"));
        if(jsonObject.has("state")) this.setState(jsonObject.getString("state"));
        if(jsonObject.has("id")) this.setGroupId(jsonObject.getInt("id"));
        if(jsonObject.has("group_photo")){ JSONObject photo = jsonObject.getJSONObject("group_photo");
            this.setThumbImage(photo.getString("thumb_link"));
        }
    }

    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    public int getLon() {
        return lon;
    }

    public void setLon(int lon) {
        this.lon = lon;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getMembers() {
        return members;
    }

    public void setMembers(int members) {
        this.members = members;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

}
