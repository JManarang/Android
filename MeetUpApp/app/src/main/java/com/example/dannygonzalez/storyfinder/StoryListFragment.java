package com.example.dannygonzalez.storyfinder;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.example.dannygonzalez.storyfinder.JsonController;

public class StoryListFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView mStoryRecyclerView;
    private StoryAdapter mAdaptor;
    private List<MeetUp> groups;
    public static final String TAG = "MENU QUERY: ";
    JsonController controller;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


        controller = new JsonController(
                new JsonController.OnResponseListener() {
                    @Override
                    public void onSuccess(List<MeetUp> movies) {
                        if (movies.size() > 0) {
                            mAdaptor.updateDataSet(movies);
                            mStoryRecyclerView.setAdapter(mAdaptor);
                            mStoryRecyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(getActivity(), "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    //TODO: create onCreateOptionsMenu so when setHasOptionsMenu is true it will call this and menuInflater will inflate the menu xml fragment_story_list
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.fragment_story_list, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("Search");
        searchView.setSubmitButtonEnabled(true);
    }

    public boolean onQueryTextSubmit(String query) {
        if (query.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(query);
            return false;
        } else {
            Toast.makeText(getActivity(), "Must provide more than one character", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean onQueryTextChange(String newText) {
        if (newText.length() > 1) {
            controller.cancelAllRequests();
            controller.sendRequest(newText);
        } else if (newText.equals("")) {
            mStoryRecyclerView.setVisibility(View.GONE);//basically  clears out the view(just makes it not visible)
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story_list, container, false);
        mStoryRecyclerView = (RecyclerView) view.findViewById(R.id.story_recycler_view);
        mStoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        EventLab eventLab = EventLab.get(getActivity());
        groups = eventLab.getmMeetups();
        mAdaptor = new StoryAdapter(groups);
        mStoryRecyclerView.setAdapter(mAdaptor);
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        //TODO: i think this is important since we need to be able to come back on RESUME
        EventLab eventLab = EventLab.get(getActivity());
        List<MeetUp> groups = eventLab.getmMeetups();
        if(mAdaptor == null){
            mAdaptor = new StoryAdapter(groups);
            mStoryRecyclerView.setAdapter(mAdaptor);
        }else{
            mAdaptor.notifyDataSetChanged();
        }
    }


    private class StoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MeetUp mStory;
        private CardView cardView;
        private NetworkImageView mImage;
        private TextView mTitleTextView;
        private TextView mDescriptionTextView;
        private TextView mCityState;
        private TextView mMembers;


        public StoryHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.cardView = (CardView) itemView.findViewById(R.id.card_view);
            this.mTitleTextView = (TextView) itemView.findViewById(R.id.tvTitle);
            this.mImage = (NetworkImageView) itemView.findViewById(R.id.nivPoster);
            this.mDescriptionTextView = (TextView) itemView.findViewById(R.id.desc);
            this.mCityState = (TextView) itemView.findViewById(R.id.citystate);
            this.mMembers = (TextView) itemView.findViewById(R.id.members);

        }

        public void bindStory(MeetUp story) {
            /*
            Bellow I added the set lines method to make sure each fragment comes out the same size
            because if they are not the same size recycler view will not display them in order!
             */
            mStory = story;
            mTitleTextView.setText(mStory.getName());
            mTitleTextView.setLines(2);
            mDescriptionTextView.setText("Click Card for Description");
            mDescriptionTextView.setLines(5);
            //TODO: fix the picture thing lol
            //In onBindViewHolder I use the setPosterUrl method and that creates the image
            // mImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), mStory.getGroupId(), null));
            mCityState.setText(mStory.getCity() + ", " + mStory.getState());
            mMembers.setText("Members: " + mStory.getMembers());

        }

        @Override
        public void onClick(View v) {
            Log.d("myTag", "this is the group id of the group: "+mStory.getGroupId());
            //EventLab event = EventLab.get(getActivity());
            //event.mMeetups.add(mStory);
            Intent intent = StoryActivity.newIntent(getActivity(),mStory.getGroupId());
            startActivity(intent);
        }

        void setPosterUrl(String imageUrl) {
            ImageLoader imageLoader = VolleySingleton.getInstance(App.getContext()).getImageLoader();
            this.mImage.setImageUrl(imageUrl, imageLoader);
        }
    }

    private class StoryAdapter extends RecyclerView.Adapter<StoryHolder> {
        private List<MeetUp> mStories ;

        public StoryAdapter(List<MeetUp> stories) {
            this.mStories = stories;
        }

        @Override
        public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_story, parent, false);
            return new StoryHolder(view);
        }

        @Override
        public void onBindViewHolder(StoryHolder holder, int position) {
            MeetUp story = mStories.get(position);
            holder.setPosterUrl(story.getThumbImage());
            holder.bindStory(story);
        }

        public void updateDataSet(List<MeetUp> modelList) {
            this.mStories.clear();
            this.mStories.addAll(modelList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return mStories.size();
        }
    }

}


