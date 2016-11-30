package com.midian.moma.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.midian.moma.R;

import midian.baselib.base.BaseActivity;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

/**
 * 选择广告购买开始时间
 * Created by chu on 2016/3/8.
 */
public class CalenderActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private GestureDetector gestureDetector = null;
    private CalendarAdapter adapter = null;
    private ViewFlipper flipper = null;
    private GridView gridView = null;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    private String dates = null;
    private Drawable drawable = null;
    private String start_time;
    /**
     * 每次添加gridview到viewflipper中时给的标记
     */
    private int gvFlag = 0;
    /**
     * 当前的年月，现在日历顶端
     */
    private TextView currentMonth;
    /**
     * 上个月
     */
    private ImageView prevMonth;
    /**
     * 下个月
     */
    private ImageView nextMonth;

    public CalenderActivity() {

        /*Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        day_c = Integer.parseInt(currentDate.split("-")[2]);*/

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
        start_time = mBundle.getString("start_time");
        topbar = findView(R.id.topbar);
        topbar.setTitle("购买开始日期").setLeftImageButton(R.drawable.icon_back, null).setLeftText("返回", UIHelper.finish(_activity));

//        topbar.setRightText("确定", RightOnClickListener);
        currentMonth = findView(R.id.currentMonth);
        prevMonth = findView(R.id.prevMonth);
        nextMonth = findView(R.id.nextMonth);
        setListener();

        year_c = Integer.parseInt(start_time.split("-")[0]);
        month_c = Integer.parseInt(start_time.split("-")[1]);
        day_c = Integer.parseInt(start_time.split("-")[2]);


        gestureDetector = new GestureDetector(this, new MyGestureListener());
        flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.removeAllViews();
        adapter = new CalendarAdapter(this, getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        addGridView();
        gridView.setAdapter(adapter);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);


    }


    private class MyGestureListener extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
                enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }

    /**
     * 移动到下一个月
     *
     * @param gvFlag
     */
    private void enterNextMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth++; // 下一个月

        adapter = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(adapter);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     *
     * @param gvFlag
     */
    private void enterPrevMonth(int gvFlag) {
        addGridView(); // 添加一个gridView
        jumpMonth--; // 上一个月

        adapter = new CalendarAdapter(this, this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c);
        gridView.setAdapter(adapter);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份 闰哪月等信息
     *
     * @param view
     */
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        // draw = getResources().getDrawable(R.drawable.top_day);
        // view.setBackgroundDrawable(draw);
        textDate.append(adapter.getShowYear()).append("年").append(adapter.getShowMonth()).append("月").append("\t");

//        String y = start_time.split("-")[0];
//        String m = start_time.split("-")[1];
//        textDate.append(y + "年" + m + "月");
//        view.setText(y + "年" + m + "月");
        view.setText(textDate);
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int Width = display.getWidth();
        int Height = display.getHeight();

        gridView = new GridView(this);
        gridView.setNumColumns(7);
        gridView.setColumnWidth(40);
        // gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        if (Width == 720 && Height == 1280) {
            gridView.setColumnWidth(40);
        }
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return CalenderActivity.this.gestureDetector.onTouchEvent(event);
            }
        });

        //日历view的点击事件
        gridView.setOnItemClickListener(new OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // TODO 日历view的点击事件
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = adapter.getStartPositon();
                int endPosition = adapter.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {

                    String scheduleYear = adapter.getShowYear();//获取选择的年份
                    String scheduleMonth = adapter.getShowMonth();//获取选择的月份
                    String scheduleDay = adapter.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历---获取选择的天
                    // String scheduleLunarDay =calV.getDateByClickItem(position).split("\\.")[1];//这一天的阴历

                    String d1 = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;//选择的年月日
                    String d2 = year_c + "-" + month_c + "-" + day_c;
                    System.out.println("广告位开始时间:::" + d1 + "：：：用户选择的时间：：" + d2);
                    System.out.println("d1+d2----------" + d1 + ":::::" + d2);
                    long chooseData = getStringToDate(d1);
                    long startData = getStringToDate(d2);
                    Long inv = 19 * 24 * 60 * 60 * 1000l;//20天的时间戳
                    if (chooseData - startData > inv) {
                        UIHelper.t(_activity, "请选择自开始日期20天内为开始时间");
                        return;
                    } else if (chooseData < startData) {
                        UIHelper.t(_activity, "请选择大于广告位开始时间");
                        return;
                    } else {
                        drawable = new ColorDrawable(Color.rgb(23, 126, 214));
                        arg1.setBackgroundDrawable(drawable);
                        dates = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;
                        //返回点选的日期
                        Bundle mBundle = new Bundle();
                        mBundle.putString("times", dates);
                        setResult(RESULT_OK, mBundle);
                        finish();
                    }



                   /* if (FDDataUtils.getInteger(scheduleYear) < year_c) {
                        UIHelper.t(_activity, "请选择大于当前日期");
                        return;
                    } else if (FDDataUtils.getInteger(scheduleMonth) < month_c) {
                        UIHelper.t(_activity, "请选择大于当前日期");
                        return;
                    } else if (FDDataUtils.getInteger(scheduleDay) < day_c) {
                        UIHelper.t(_activity, "请选择大于当前日期");
                        return;
                    } else if (t1 > t2) {
                        UIHelper.t(_activity, "请选择自开始日期20天内为开始时间");
                        return;
                    } else {
                        drawable = new ColorDrawable(Color.rgb(23, 126, 214));
                        arg1.setBackgroundDrawable(drawable);
                        dates = scheduleYear + "-" + scheduleMonth + "-" + scheduleDay;
                        //返回点选的日期
                        Bundle mBundle = new Bundle();
                        mBundle.putString("times", dates);
                        setResult(RESULT_OK, mBundle);
                        finish();
                    }*/
                }
            }
        });
        gridView.setLayoutParams(params);
    }

    private void setListener() {
        prevMonth.setOnClickListener(this);
        nextMonth.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.nextMonth: // 下一个月
                enterNextMonth(gvFlag);
                break;
            case R.id.prevMonth: // 上一个月
                enterPrevMonth(gvFlag);
                break;
            default:
                break;
        }
    }

    /**
     * 时间戳转换成字符窜
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        return sf.format(d);
    }

    /**
     * 将字符串转为时间戳
     *
     * @param time
     * @return
     */
    public static long getStringToDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

}
