package com.example.yangyc.tmcreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by yangyc on 2016/4/20. In project TMCReminder
 */
public class AlarmManagerCompat {
    private final AlarmManager mAlarmMgr;

    public AlarmManagerCompat(Context context) {
        mAlarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);;
    }


    public void setExactAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {
        if (isAtLeastMarshmallow()) {
            mAlarmMgr.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
        } else {
            mAlarmMgr.setExact(type, triggerAtMillis, operation);
        }
    }

    public void setAndAllowWhileIdle(int type, long triggerAtMillis, PendingIntent operation) {
        if (isAtLeastMarshmallow()) {
            mAlarmMgr.setAndAllowWhileIdle(type, triggerAtMillis, operation);
        } else {
            mAlarmMgr.set(type, triggerAtMillis, operation);
        }
    }

    /**
     *  添加提醒
     * @param timeToReminder 提醒时间
     */
    public void addAlarm(Context context, long timeToReminder, int requestCode, String action) {
        //NOTE: 设置闹铃时，将基本信息存储起来，以备查找更改和取消
        //基本信息包含时间 key（现在的想法是key的hashcode作为requestCode）
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(action);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);//如果已存在，那么先将之前的取消，然后再重新注册一个
        setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, timeToReminder, pendingIntent);
    }

    /**
     * 取消提醒
     * @param action 对应的action
     */
    public void cancleAlarmByRequestCode(Context context, int requestCode, String action) {
        Intent alarmIntent = new Intent();
        alarmIntent.setAction(action);
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                requestCode,
                alarmIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);//如果已存在，那么先将之前的取消，然后再重新注册一个
        mAlarmMgr.cancel(pendingIntent);
    }


    private boolean isAtLeastMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
