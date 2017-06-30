package com.eleganceinfoways.pigeeback;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by KHUSHI on 17-May-17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }
}
