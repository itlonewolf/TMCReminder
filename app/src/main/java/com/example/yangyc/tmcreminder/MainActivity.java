package com.example.yangyc.tmcreminder;

import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TimePicker mTimePicker;
    View mView;
    Drawable cusDrawable;
    int dp6 = Util.dp2px(6);
    int dp20 = Util.dp2px(20);
    int dp200 = Util.dp2px(200);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mTimePicker = Util.findViewById(this, R.id.timePicker);
        mView = Util.findViewById(this, R.id.viewDraw);

        cusDrawable = new CusDrawable();
//        Rect drawBounds = new Rect();
//        drawBounds.left = 0;
//        drawBounds.top = 0;
//        drawBounds.right = dp200;
//        drawBounds.bottom = dp200;
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            mView.setClipBounds(drawBounds);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mView.setBackground(cusDrawable);
        } else {
            mView.setBackgroundDrawable(cusDrawable);
        }

//        ReminderMgr.addReminder(MainActivity.this, 1461320220, "demo");
//        Log.d("timer", "设置了闹钟>>>"+ System.currentTimeMillis());
    }

    public void settingAlarm(View view) {
//        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                long alarmTime  = Util.timeToLong("25/4/2016 16:01:00");
//                ReminderMgr.addReminder(MainActivity.this, alarmTime, "demo");
//                Log.d("timer", "设置了闹钟>>>"+ Util.formatTimeWithFormat(Util.SHORT_TIME_WITH_SECOND, alarmTime));
//            }
//        });
    }

    private class CusDrawable extends Drawable {
        private static final String TAG = "CusDrawable";
        Paint mPaint;
        final String infoToShow = "Almost Lover";
        float canvasWidth;
        float canvasHeight;
        Rect textRect ;

        Drawable tmcDrawable;
        Rect tmcBounds;
        int tmcIconWidth;
        int tmcIconHeight;
        String[] infosToShow;
        ArrayList<LineInfo> mLineInfos;
        float textWidth;
        float textHeight;
        float textSingleHeight;
        float lineSpaceSize = 0;
        float contentWidth;
        float contentHeight;
        float textAscent;

        public CusDrawable() {
            mLineInfos = new ArrayList<>();
            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setDither(true);
            mPaint.setColor(Color.GRAY);
            mPaint.setStyle(Paint.Style.STROKE);
            loadRes();
        }

        private void loadRes(){
            tmcDrawable = getDrawable(R.mipmap.tmc);
            tmcIconWidth = tmcDrawable.getMinimumWidth();
            tmcIconHeight = tmcDrawable.getMinimumHeight();

            infosToShow = getString(R.string.tmc_info).split(",");
            for (String s : infosToShow) {
                Log.d("infosToShow", s);
            }

            tmcBounds = new Rect();
            textRect = new Rect();
        }

        private LineInfo measureSingleLine(String info, Paint paint) {
            if (TextUtils.isEmpty(info)) {
                return new LineInfo();
            }
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//            float height = Math.abs(fontMetrics.leading + fontMetrics.bottom + fontMetrics.top) + lineSpaceSize;
            float height = Math.abs(fontMetrics.leading) + Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom) + lineSpaceSize;
            textAscent = Math.abs(fontMetrics.ascent);
            Log.d(TAG, "height>>>" + height);
            float width = paint.measureText(info);
            return new LineInfo(width, height);
        }

        private void measureText(String[] infos, Paint paint) {
            for (String info : infos) {
                LineInfo lineInfo = measureSingleLine(info, paint);

                mLineInfos.add(lineInfo);

                float infoWidth = lineInfo.getWidth();
                textWidth = infoWidth > textWidth ? infoWidth : textWidth;
                textHeight += lineInfo.getHeight();
                textSingleHeight = lineInfo.getHeight();
            }
        }

        private void measure(String[] infos, Paint paint){
            measureText(infos, paint);
            contentWidth = textWidth > tmcIconWidth ? textWidth : tmcIconWidth;
            contentHeight = tmcIconHeight + textHeight;
            Log.d(TAG, "tmcIconHeight>>>" + tmcIconHeight + ";  textHeight>>>" + textHeight + "; contentHeight>>>" + contentHeight);
        }

        @Override
        public void draw(Canvas canvas) {
//            Rect clipBounds = canvas.getClipBounds();
            mPaint.setTextSize(dp20);
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();

            measure(infosToShow, mPaint);
            Log.d(TAG, "canvas height>>>" + canvasHeight);

            //绘制图标  左右未变，上下变了
            tmcBounds.left = (int) (canvasWidth / 2  - tmcIconWidth / 2);
            tmcBounds.right = (int) (canvasWidth / 2 + tmcIconWidth / 2);

            tmcBounds.top = (int) (canvasHeight / 2 - contentHeight / 2);
            tmcBounds.bottom = (int) (canvasHeight / 2 - (contentHeight / 2 - tmcIconHeight));
            tmcDrawable.setBounds(tmcBounds);
            tmcDrawable.draw(canvas);
            Log.d(TAG, "tmcBounds.top>>>" + tmcBounds.top + "; tmcBounds.bottom>>>" + tmcBounds.bottom);
            canvas.drawLine(0,tmcBounds.bottom, 500, tmcBounds.bottom + 1, mPaint);

            //绘制文字
            float textTop = canvasHeight / 2 - (contentHeight / 2 - tmcIconHeight) + textAscent;
            for (int i = 0; i < infosToShow.length; i++) {
                String info = infosToShow[i];
                float textLeft = canvasWidth / 2 - mLineInfos.get(i).getWidth() / 2;
                textTop += textSingleHeight * i;
                Log.d(TAG, "textTop>>>" + textTop + "; index>>>" + i + "; textSingleHeight>>>" + textSingleHeight);
                canvas.drawText(info, textLeft, textTop, mPaint);
            }
        }

//        @Override
//        public int getMinimumHeight() {
//            int originHeight = super.getMinimumHeight();
//            return originHeight <= 0 ? dp200 : originHeight;
//        }
//
//        @Override
//        public int getMinimumWidth() {
//            int originWidth = super.getMinimumWidth();
//            return originWidth <= 0 ? dp200: originWidth;
//        }

        @Override
        public void setAlpha(int alpha) {

        }

        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return 0;
        }
    }
}
