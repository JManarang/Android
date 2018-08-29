package com.example.dannygonzalez.storyfinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.util.UUID;

import static android.R.attr.bitmap;

/**
 * Created by Danny Gonzalez on 11/28/2016.
 */

public class Story {
    private UUID mUuid;
    private String mName;
    private String mDescription;
    private int mImageId;
    /*
    Each story has a int 'mImageId' which is the resource of the picture
    when we want to get the drawble from this id we do the following:
    ResourcesCompat.getDrawable(getResources(), mStory.getmImageId(), null)
     */

    public Story(){
        mUuid = UUID.randomUUID();
    }

    public UUID getmUuid() {
        return mUuid;
    }

    public String getmName() {
        return mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public int getmImageId(){
        return  mImageId;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public void setmImageId(int draw){
        mImageId = draw;
    }


}
