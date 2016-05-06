package com.example.yangyc.tmcreminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String TAG = "RemenderReceiver";

    public static final String ACTION_remender = "com.example.yangyc.tmcreminder.remender.";
    public ReminderReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notiMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            Notification noti = new Notification.Builder(context)
                    .setContentTitle(Util.currentTimeWithThisFormat(Util.SHORT_TIME_WITH_SECOND))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            noti.defaults = Notification.DEFAULT_ALL;
            notiMgr.notify(12, noti);
        }
        Log.d(TAG, "收到了广播>action：" + intent.getAction() + "；当前时间："+ Util.currentTimeWithThisFormat(Util.SHORT_TIME));
    }
}
