package com.matejhacin.roomies;

import android.app.Application;

import io.paperdb.Paper;

/**
 * Created by Domen Lanišnik on 6. 12. 2016.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Paper.init(this);
    }
}
