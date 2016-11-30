package com.midian.moma.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * 闹钟定时提醒轮询工具类
 * Created by chu on 2016/5/24.
 */
public class PollingUtils {
    //开启轮询服务
    public static void startPollingService(Context context, long seconds, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
//        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);

        //这是60分钟的毫秒数60*60*1000
        long Minutes = 24 * 60 * 60 * 1000;//// //24小时
        //SystemClock.elapsedRealtime()表示1970年1月1日0点至今所经历的时间
        long totalTime = 0;//SystemClock.elapsedRealtime();//+ Minutes

        ContentResolver cv = context.getContentResolver();
        String strTimeFormat = android.provider.Settings.System.getString(cv, android.provider.Settings.System.TIME_12_24);
        long firstTime = SystemClock.elapsedRealtime();	// 开机之后到现在的运行时间(包括睡眠时间)表示1970年1月1日0点至今所经历的时间
        long systemTime = System.currentTimeMillis();
        //设置的时间  定时 执行这个闹铃
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8")); // 这里时区需要设置一下，不然会有8个小时的时间差
        calendar.set(Calendar.MINUTE, 10);//分钟
        if (strTimeFormat.equals("24")) {//24小时制
            Log.i("activity", "24");
            calendar.set(Calendar.HOUR_OF_DAY, 21);//小时
        } else {
            //为12小时制
            calendar.set(Calendar.HOUR_OF_DAY, 03);//小时
            Log.i("activity", "12");
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 选择的每天定时时间
        long setTime = calendar.getTimeInMillis();
        System.out.println("定时提醒 的时间 为="+setTime);
        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        if(systemTime > setTime) {
            Toast.makeText(context, "提醒的时间小于当前时间", Toast.LENGTH_SHORT).show();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            setTime = calendar.getTimeInMillis();
        }
        // 计算现在时间到设定时间的时间差
        long time = setTime - systemTime;
        firstTime += time;

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Service
        manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime, Minutes, pendingIntent);
    }

    //停止轮询服务
    public static void stopPollingService(Context context, Class<?> cls, String action) {
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
//        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        //取消正在执行的服务
        manager.cancel(pendingIntent);
    }
}