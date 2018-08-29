package com.example.dannygonzalez.storyfinder;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */


import android.support.v4.app.Fragment;
public class StoryListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){
        return new StoryListFragment();
    }
}
