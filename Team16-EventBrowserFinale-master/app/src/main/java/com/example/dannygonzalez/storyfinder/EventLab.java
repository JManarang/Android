package com.example.dannygonzalez.storyfinder;

/**
 * Created by Danny Gonzalez on 12/18/2016.
 */
import java.util.List;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;

public class EventLab {
    public List<MeetUp> mMeetups;
    private static EventLab seventLab;
    public static EventLab get(Context context){
         if(seventLab ==null){
             seventLab = new EventLab(context);
         }
         return seventLab;
    }
    private EventLab(Context context){
        mMeetups = new ArrayList<>();
    }

    public List<MeetUp> getmMeetups(){
        return mMeetups;
    }

    public MeetUp getGroup(int id){
        for(MeetUp meetUp : mMeetups){
            if(meetUp.getGroupId()==(id)){
                Log.d("myTag", "Successfully retrieved group! : "+id);
                return meetUp;
            }
        }
        return null;
    }
}
