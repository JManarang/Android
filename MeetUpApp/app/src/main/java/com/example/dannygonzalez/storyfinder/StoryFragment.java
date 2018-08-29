package com.example.dannygonzalez.storyfinder;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.UUID;

public class StoryFragment extends Fragment{
    private static final String ARG_STORY_ID = "story_id";
    private MeetUp mStory;
    private TextView mTitle;
    private TextView mDesc;
    private TextView mCityState;
    private TextView mMembers;
    private NetworkImageView mPicture;

    public static StoryFragment newInstance(int storyId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_STORY_ID, storyId);
        Log.d("myTag", "StoryFragment got the id: "+storyId);
        StoryFragment fragment = new StoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        int storyId = (int) getArguments().getSerializable(ARG_STORY_ID);
        EventLab eventLab = EventLab.get(getActivity());
        Log.d("myTag", "on create bundle got the id: "+storyId);
        mStory = eventLab.getGroup(storyId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_story,container,false);
        mTitle = (TextView) v.findViewById(R.id.title_text_view);
        mTitle.setText(mStory.getName());
        mDesc = (TextView) v.findViewById(R.id.desc_text_view);
        mDesc.setText(mStory.getDescription());
        mPicture = (NetworkImageView) v.findViewById(R.id.picture_image_view);
        this.mCityState = (TextView) v.findViewById(R.id.cityandstate_text_view);
        mCityState.setText(mStory.getCity() +", " + mStory.getState());
        this.mMembers = (TextView) v.findViewById(R.id.members_text_view);
        mMembers.setText("Members: " + mStory.getMembers());
        Log.d("myTag", "Successfully loaded the group:"+mStory.getName());
        ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
        this.mPicture.setImageUrl(mStory.getThumbImage(), imageLoader);

        return v;
    }
}
