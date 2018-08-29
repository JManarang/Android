package com.example.dannygonzalez.storyfinder;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */

public class StoryActivity extends SingleFragmentActivity{

    private static final String EXTRA_STORY_ID = "com.example.dannygonzalez.storyfinder.story_id";

    public static Intent newIntent(Context packageContext, int storyid) {
        Log.d("myTag", "newIntent got the id: "+storyid);
        Intent intent = new Intent(packageContext,StoryActivity.class);
        intent.putExtra("EXTRA_STORY_ID",storyid);
        return intent;
    }
    @Override
    protected Fragment createFragment(){
        int storyId = getIntent().getIntExtra("EXTRA_STORY_ID", 0);
        Log.d("myTag", "Fragment; Create Fragment got the id: "+storyId);
        return StoryFragment.newInstance(storyId);
    }
}
