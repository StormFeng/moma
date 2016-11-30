package com.midian.moma.ui.home;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.midian.moma.R;
import com.midian.moma.model.SignScoreBean;
import com.midian.moma.model.SignScoreBean.SignDate;
import com.midian.moma.utils.AlarmReceiver;
import com.midian.moma.utils.AppUtil;
import com.midian.moma.utils.MyReceiver;
import com.midian.moma.utils.PollingService;
import com.midian.moma.utils.PollingUtils;
import com.midian.moma.utils.ShareDialogUtil;
import com.midian.moma.utils.SignCustomDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.tooglebutton.ToggleButton;
import midian.baselib.utils.FDDataUtils;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.CircleImageView;

/**
 * 签到
 * Created by chu on 2016/3/2.
 */
public class SignInActivity extends BaseActivity implements SignCustomDialog.VerificationDialogInterface, ToggleButton.OnToggleChanged {
    private BaseLibTopbarView topbar;
    private CircleImageView head_cv;
    private TextView name_tv;//积分数
    private TextView current_time;//日历上行显示的当前日期
    private TextView continuity_sign;//连续签到
    private TextView count_score;//总积分
    private Button sign_btn;//签到
    private View share_ll;//分享
    private ToggleButton toggle_button;
    private SignCalendar calendar;//自定义日历控件
    private List<String> list = new ArrayList<String>();//设置标记列表
    private boolean isSign = false;//标记日历中已签到的日期
    private boolean isTodaySign=false;//标记今日是否已签到
    private String date = null;//设置默认选中的日期格式为2016-03-03标准DATE格式
    private String date1 = null;//单天日期
    private String total_score;
    public static final long DAY =  60 * 60 * 24;//一天的时间

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        date1 = formatter.format(curDate);

        topbar = findView(R.id.topbar);
        topbar.getLine_iv().setVisibility(View.GONE);
        topbar.setBackgroundResource(R.color.qd_bg);
        topbar.setTitle("每日签到").setLeftText("返回", null).setLeftImageButton(R.drawable.icon_back, UIHelper.finish(_activity));

        head_cv = findView(R.id.head_cv);
        name_tv = findView(R.id.name_tv);
        current_time = findView(R.id.current_time);
        continuity_sign = findView(R.id.continuity_sign);//签到总天数
        count_score = findView(R.id.count_score);//总积分
        sign_btn = findView(R.id.sign_btn);
        share_ll = findView(R.id.share_ll);
        toggle_button = findView(R.id.toggle_button);
        calendar = findView(R.id.calendar);
        sign_btn.setOnClickListener(this);
        share_ll.setOnClickListener(this);

        toggle_button.setOnToggleChanged(this);
        current_time.setText(date1);
        if (null != date) {
            int years = Integer.parseInt(date.substring(0, date.indexOf("-")));
            int month = Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-")));
            current_time.setText(years + "年" + month + "月");
            calendar.showCalendar(years, month);
        }

        System.out.println("ac中的isSign::=" + ac.isSign);
        if (ac.isSign) {
            toggle_button.setToggleOn();
        } else {
            toggle_button.setToggleOff();
        }
        if (ac.isAccess()) {
            loadDate();
        } else {
            UIHelper.t(_activity,"请登陆后再进行签到");
            finish();
        }
        //判断当前是否已经签到
        if (isSign) {
            sign_btn.setBackgroundResource(R.drawable.icon_sign_in_ok);
            sign_btn.setEnabled(false);
        }
        System.out.println("是否签到 过：：：" + isSign);
    }


    private void loadDate() {
        AppUtil.getMomaApiClient(ac).signScore(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sign_btn://签到事件
                AppUtil.getMomaApiClient(ac).addSignScore(this);
                break;
            case R.id.share_ll:
//                ShareDialogUtil shareDialogUtil = new ShareDialogUtil(_activity, R.style.bottom_dialog);
//                shareDialogUtil.show();
                String share_title="摩范Mofun";
                String share_content="万千优惠在手中，精彩生活我做主！";
                String share_url="http://url.cn/40AirWe";
                new ShareDialogUtil(_activity).show(share_url, share_title, share_content, "","1");
                break;
            case R.id.toggle_button:
                toggle_button.toggle();
                break;
        }
    }

    @Override
    public void onApiStart(String tag) {
        super.onApiStart(tag);
        showLoadingDlg();
        sign_btn.setEnabled(false);
    }

    ArrayList<SignDate> signDates = new ArrayList<SignDate>();

    @Override

    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        sign_btn.setEnabled(true);
        if (res.isOK()) {
            if ("signScore".equals(tag)) {
                SignScoreBean scoreBean = (SignScoreBean) res;
                count_score.setText(scoreBean.getContent().getTotal_score() + " 积分");
                total_score = scoreBean.getContent().getTotal_score();

                if (TextUtils.isEmpty(ac.getProperty("head_pic"))) {
                    ac.setImage(head_cv, R.drawable.head1);
                } else {
                    ac.setImage(head_cv, ac.getProperty("head_pic"));
                }
                if (TextUtils.isEmpty(ac.name)) {
                    name_tv.setText("");
                } else {
                    name_tv.setText(ac.getProperty("name"));
                }
                signDates = scoreBean.getContent().getContent();
                continuity_sign.setText("已签到" + signDates.size() + "天");
                for (SignDate dateBean : scoreBean.getContent().getContent()) {
                    list.add(dateBean.getDate());
                }
                calendar.addMarks(list, 0);
                ac.isSendFirst(true);
                query();
            }
            if ("addSignScore".equals(tag)) {
                count_score.setText((FDDataUtils.getInteger(total_score) + 2) + " 积分");
                continuity_sign.setText("已签到" + (signDates.size() + 1) + "天");
                isSign = true;
                isTodaySign = true;
                SignCustomDialog dialog = new SignCustomDialog(_activity, "签到成功", "获得2积分", "确定", null);
                dialog.setClicklistener(this);
                dialog.show();
            }
        } else {
            ac.handleErrorCode(_activity, res.error_code);
            if (res.ret.equals("error") || res.error_code.equals("token_error")) {
                finish();
            }
        }

    }


    /**
     * 查询是否已签到
     */
    private void query() {
        for (SignDate dateBean : signDates) {
            if (date1.equals(dateBean.getDate())) {
                isSign = true;
                sign_btn.setBackgroundResource(R.drawable.icon_sign_in_ok);
                sign_btn.setEnabled(false);
            } else {
                sign_btn.setBackgroundResource(R.drawable.icon_sign_in_btn);
                sign_btn.setEnabled(true);
            }

        }
    }


    @Override
    public void doConfirm() {
        isSign = true;
        Date today = calendar.getThisday();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        //将当前日期标示出来
        list.add(df.format(today));
        calendar.addMark(today, 0);
        sign_btn.setBackgroundResource(R.drawable.icon_sign_in_ok);
        sign_btn.setEnabled(false);
    }

    @Override
    public void doCancel() {

    }

    @Override
    public void onToggle(boolean on) {
        if (on) {
            ac.isSign(true);
            if (!isTodaySign) {
                PollingUtils.startPollingService(_activity, DAY, PollingService.class, PollingService.ACTION);
            }
//            startSign();
        } else {
            ac.isSign(false);
            PollingUtils.stopPollingService(_activity, PollingService.class, PollingService.ACTION);
//            stopSign();
        }
        System.out.println("签到设置状态：：：" + ac.isSign);
    }








    private void startSign() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        long firstTime = SystemClock.elapsedRealtime();	// 开机之后到现在的运行时间(包括睡眠时间)
        long systemTime = System.currentTimeMillis();
        //设置的时间  定时 执行这个闹铃
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.MINUTE, 5);//分钟
        calendar.set(Calendar.HOUR_OF_DAY, 21);//小时
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 选择的每天定时时间
        long setTime = calendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > setTime) {
            Toast.makeText(this, "设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            setTime = calendar.getTimeInMillis();
        }
        // 计算现在时间到设定时间的时间差
        long time = setTime - systemTime;
        firstTime += time;
        // 进行闹铃注册
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, 10*1000, sender);
    }

    private void stopSign() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, 0, intent, 0);
        // 取消闹铃
        AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        am.cancel(sender);
    }

}
