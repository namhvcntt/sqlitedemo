package com.rikkeisoft.mobile.databasedemo;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by HoangVuNam on 4/12/17.
 */

public class NoteApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
