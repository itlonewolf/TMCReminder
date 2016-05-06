package com.example.yangyc.tmcreminder;

import android.content.Context;

/**
 * Created by yangyc on 2016/4/20. In project TMCReminder
 * 提醒管理类
 */
public class ReminderMgr {
    public static final int MONDAY    = 1;
    public static final int TUESDAY   = 1;
    public static final int Wednesday = 1;
    public static final int Thursday  = 1;
    public static final int Friday    = 1;
    public static final int Saturday  = 1;
    public static final int sunday    = 1;
    private static AlarmManagerCompat mAlarmManagerCompat;

//    public static void addReminder(Context context, int hourOfDay, int minOfHour, ){}

    /**
     *  添加提醒
     * @param reminderTime 提醒时间
     */
    public static void addReminder(Context context, long reminderTime, String key) {
        //NOTE: 设置闹铃时，将基本信息存储起来，以备查找更改和取消
        //基本信息包含时间 key（现在的想法是key的hashcode作为requestCode）
        final int requestCode = key.hashCode();
        checkAlarmMgr(context);
        mAlarmManagerCompat.addAlarm(context, reminderTime, requestCode, ReminderReceiver.ACTION_remender);
    }

    /**
     * 取消提醒
     * @param key 此提醒的（相关文字。。。）
     */
    public void cancleReminderByKey(Context context, String key) {
        final int requestCode = key.hashCode();
        checkAlarmMgr(context);
        mAlarmManagerCompat.cancleAlarmByRequestCode(context, requestCode, ReminderReceiver.ACTION_remender);
    }

    private static void checkAlarmMgr(Context context){
        if (mAlarmManagerCompat == null) {
            mAlarmManagerCompat = new AlarmManagerCompat(context);
        }
    }

}
