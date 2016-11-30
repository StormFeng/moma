package com.midian.moma.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.midian.moma.R;
import com.midian.moma.app.MAppContext;
import com.midian.moma.ui.MainActivity;
import com.midian.moma.ui.home.SignInActivity;

import midian.baselib.app.AppContext;

/**
 * Created by chu on 2016/5/24.
 */
public class PollingService extends Service {
    //    public static final String ACTION = "com.ryantang.service.PollingService";
    public static final String ACTION = "com.midian.moma.utils.PollingService";
    private Notification mNotification;
    private NotificationManager mManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        initNotifiManager();
    }


    @Override
    public void onStart(Intent intent, int startId) {
//        new PollingThread().start();
    }

    //初始化通知栏配置
    private void initNotifiManager() {
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        int icon = R.drawable.ic_launcher;
        mNotification = new Notification();
        mNotification.icon = icon;
        mNotification.tickerText = "签到提醒";
        mNotification.defaults |= Notification.DEFAULT_SOUND;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
            showNotification();
    }

    //弹出Notification
    private void showNotification() {
        mNotification.when = System.currentTimeMillis();
        //Navigator to the new activity when click the notification title
        Intent i = new Intent(this, SignInActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, Intent.FLAG_ACTIVITY_NEW_TASK);
        mNotification.setLatestEventInfo(this, getResources().getString(R.string.app_name), "你可以进行新的签到啦!", pendingIntent);
        mManager.notify(0, mNotification);
    }

    /**
     * Polling thread
     * 模拟向Server轮询的异步线程
     *
     * @Author Ryan   24*60*60
     * @Create 2013-7-13 上午10:18:34
     */
    long count = 0;
    long DAY = 100 * 60 * 60 * 24;//一天的时间

    class PollingThread extends Thread {
        @Override
        public void run() {
            System.out.println("Polling...count=" + count);
            count++;
            //当计数能被5整除时弹出通知
            if (count - DAY == 0) {
                count = 0;
                showNotification();
                System.out.println("签到提醒--New message!");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Service:onDestroy");
    }

}
