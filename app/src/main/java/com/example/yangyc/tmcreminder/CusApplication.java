package com.example.yangyc.tmcreminder;

import android.app.Application;
import android.content.Context;

/**
 * Created by yangyc on 2016/5/4. In project TMCReminder
 */
public class CusApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }
}
