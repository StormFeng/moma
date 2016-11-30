package com.midian.moma.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.midian.moma.R;
import com.midian.moma.ui.home.SignInActivity;

/**
 * 
 * @ClassName: AlarmReceiver  
 * @Description: 闹铃时间到了会进入这个广播，这个时候可以做一些该做的业务。
 * @author HuHood
 * @date 2013-11-25 下午4:44:30  
 *
 */
public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager manager;
    private Notification mNotification;
    private NotificationManager mManager;
	@Override
    public void onReceive(Context context, Intent intent) {
        //初始化NotificationManager对象
        mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        //定义通知栏展现的内容信息
        int icon = R.drawable.ic_launcher;
        CharSequence tickerText = "签到提醒";
        long when = System.currentTimeMillis();
        Notification notification = new Notification(icon, tickerText, when);

        //定义下拉通知栏时要展现的内容信息
//        Context context = context.getApplicationContext();
        CharSequence contentTitle = "签到提醒";
        CharSequence contentText = "记得每天签到噢";
        Intent notificationIntent = new Intent(context, SignInActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);



       /* manager = (NotificationManager)context.getSystemService(android.content.Context.NOTIFICATION_SERVICE);
        //例如这个id就是你传过来的
        String id = intent.getStringExtra("id");
        //SignInActivity点击通知时想要跳转的Activity
        Intent playIntent = new Intent(context, SignInActivity.class);
        playIntent.putExtra("id", id);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle("title").setContentText("签到提醒").setSmallIcon(R.drawable.ic_launcher).setDefaults(Notification.DEFAULT_ALL).setContentIntent(pendingIntent).setAutoCancel(true).setSubText("二级text");
        //用mNotificationManager的notify方法通知用户生成标题栏消息通知
        manager.notify(1, builder.build());*/


    }

}
