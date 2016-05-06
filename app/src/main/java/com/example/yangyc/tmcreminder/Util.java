package com.example.yangyc.tmcreminder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.view.View;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yangyc on 2016/4/20. In project TMCReminder
 */
public class Util {
    public static final  String SHORT_TIME = "MM_dd_HH_mm";
    public static final  String SHORT_TIME_WITH_SECOND = "MM_dd_HH_mm:ss";

    public static String currentTimeWithThisFormat(String timeFormat) {
        return formatTimeWithFormat(timeFormat, System.currentTimeMillis());
    }

    public static String formatTimeWithFormat(String timeFormater, long time){
        SimpleDateFormat formatter = new SimpleDateFormat(timeFormater);
        Date date = new Date(time);
        return formatter.format(date);
    }

    public static Bitmap bmpFromRes(@DrawableRes int resId) {
        return BitmapFactory.decodeResource(CusApplication.getContext().getResources(), resId);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px( float dpValue) {
        final float scale = CusApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static long timeToLong(String timeStr){
        long timeMill = 0;
        try {
            timeMill =  new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(timeStr).getTime();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return timeMill;
    }

    public static <V extends View> V findViewById(Activity activity, @IdRes int viewId) {
        return (V) activity.findViewById(viewId);
    }

//    public static String getCurrentTimeWithFormatted(){
//        Date currentTime = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dateString = formatter.format(currentTime);
//        return dateString;
//    }
}
