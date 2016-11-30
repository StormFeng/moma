package com.midian.moma.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.opengl.ETC1Util;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.midian.moma.R;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.dialog.BaseDialog;

/**
 * Created by chu on 2016/3/8.
 */
public class EndTimeDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TextView monthAdd_tv, monthDel_tv, monthCount_tv;
    private TextView startTime_tv, endTime_tv, confirm_bt;
    private String start_time, end_time;
    private long timeStamp;
    private long days = 30 * 24 * 60 * 60 * 1000l;
    private ConfirmClickListener clickListener;

    public interface ConfirmClickListener {
        void getEndSeleteTime(String endSeletedTime);// 选择结束的时间

        void getCountDay(String count);// 选择的月数
    }

    /**
     * 第一个月的时间戳
     */
    private long firstM;
    private long endStamp, chooseStamp;

    public EndTimeDialog(Context context) {
        super(context);
        initUi(context);
    }

    public EndTimeDialog(Context context, int theme, String start_time, String end_time, ConfirmClickListener clickListener) {
        super(context, R.style.bottom_dialog);
        this.start_time = start_time;
        this.end_time = end_time;
        this.clickListener = clickListener;
        initUi(context);
    }

    protected EndTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initUi(context);
    }

    private void initUi(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        //设置dialog的对齐方式
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;//设置dialog的排列方式：底部
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);//在对话框外点击可以取消对话框
        View v = View.inflate(context, R.layout.endtime_dialog, null);
        this.setContentView(v);

        monthAdd_tv = (TextView) findViewById(R.id.month_add_renew);//+
        monthDel_tv = (TextView) findViewById(R.id.month_detele_renew);//-
        monthCount_tv = (TextView) findViewById(R.id.month_count_renew);//数
        startTime_tv = (TextView) findViewById(R.id.start_time);
        endTime_tv = (TextView) findViewById(R.id.end_time);
        confirm_bt = (TextView) findViewById(R.id.confirm_bt);
        confirm_bt.setOnClickListener(this);
        monthAdd_tv.setOnClickListener(this);
        monthDel_tv.setOnClickListener(this);

        startTime_tv.setText(start_time);
        timeStamp = getStringToDate(start_time) - 24 * 3600 * 1000l;//
        firstM = timeStamp + days;//设置默认选择第一个月的时间戳
        endTime_tv.setText(getDateToString(firstM));
        endStamp = getStringToDate(end_time);
    }

    @Override
    public void onClick(View v) {
        int number = Integer.valueOf(monthCount_tv.getText().toString());
        switch (v.getId()) {
            case R.id.month_add_renew:
                addCount(number, monthCount_tv, monthAdd_tv, monthDel_tv, endTime_tv);
                break;
            case R.id.month_detele_renew:
                deleteCount(number, monthCount_tv, monthAdd_tv, monthDel_tv, endTime_tv);
                break;
            case R.id.confirm_bt:
                String end_time = endTime_tv.getText().toString();
                String count = monthCount_tv.getText().toString();
                clickListener.getEndSeleteTime(end_time);
                clickListener.getCountDay(count);
                System.out.println("相差时间：：开始日期：" + start_time + "：：结束日期" + end_time + ":::相差的时间戳日期为：：:" + (getStringToDate(endTime_tv.getText().toString()) - getStringToDate(start_time)));
                dismiss();
                break;
        }
    }

    public void addCount(int num, TextView mounthCount, TextView add_tv, TextView delete_tv, TextView end_tv) {
        num++;
        long chooseStamp = getStringToDate(end_tv.getText().toString());
        long t = timeStamp + (days * num);
        String sDate = getDateToString(t);//把选择后的时间戳转换为日期
        mounthCount.setText(Integer.toString(num));
        end_tv.setText(sDate);

        if (num > 1 && chooseStamp < endStamp) {
            delete_tv.setEnabled(true);
            delete_tv.setBackgroundResource(R.drawable.month_delete_p);
        } else if (chooseStamp <= firstM) {
            delete_tv.setBackgroundResource(R.drawable.month_detele_n);
            delete_tv.setEnabled(false);
        }

        if (num > 1 && chooseStamp < endStamp) {
            add_tv.setEnabled(true);
            add_tv.setBackgroundResource(R.drawable.month_add_gree);
        } else if (chooseStamp >=endStamp) {
            add_tv.setEnabled(false);
            add_tv.setBackgroundResource(R.drawable.month_add_n);
        }
    }

    public void deleteCount(int num, TextView mounthCount, TextView add_tv, TextView delete_tv, TextView end_tv) {
        num--;
        if (num <= 1) {
            num = 1;
        }
        mounthCount.setText(Integer.toString(num));
        long t = getStringToDate(endTime_tv.getText().toString()) - days;
        end_tv.setText(getDateToString(t));

        long chooseStamp = getStringToDate(end_tv.getText().toString());//选择显示的日期，默认为第一个月

        if (num <= 1 && chooseStamp <= firstM) {
            delete_tv.setEnabled(false);
            delete_tv.setBackgroundResource(R.drawable.month_detele_n);
        } else  {
            delete_tv.setEnabled(true);
            delete_tv.setBackgroundResource(R.drawable.month_delete_p);
        }
        if (num >= 1 && chooseStamp >= firstM) {
            add_tv.setEnabled(true);
            add_tv.setBackgroundResource(R.drawable.month_add_gree);
        } else {
            add_tv.setEnabled(false);
            add_tv.setBackgroundResource(R.drawable.month_add_n);
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
