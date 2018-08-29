package com.example.dannygonzalez.storyfinder;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;




public class StoryLab {
    private static StoryLab sStoryLab;
    private List<Story> mStories;

    public static StoryLab get(Context context){
        if (sStoryLab == null){
            sStoryLab = new StoryLab(context);
        }
        return sStoryLab;
    }

    private StoryLab(Context context){
        mStories = new ArrayList<>();
        for(int i = 0; i < 100 ; i++){
            Story story = new Story();
            story.setmName("Story # " + i);
            story.setmDescription("the uuid of this story is " + story.getmUuid());
            if(i % 2 == 0){//checks if the story# is even
                story.setmImageId(R.drawable.serena);
            }else{
                story.setmImageId(R.drawable.george);
            }
            mStories.add(story);
        }
    }

    public List<Story> getStories() {
        return mStories;
    }

    public Story getStory(UUID id){
        for(Story story : mStories){
            if(story.getmUuid().equals(id)){
                return story;
            }
        }
        return null;
    }
}
