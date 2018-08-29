package com.example.dannygonzalez.storyfinder;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jonah on 12/8/2016.
 */

public class App extends Application {

    private static App instance;

    /**
     * Return application context anywhere in the application
     * @return  Application context
     */
    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}