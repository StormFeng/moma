/**
 * @Title: MyReceiver.java
 * @Package com.dwtedx.alarmmanagerdemo
 * @author qinyl
 * @date 2015年1月21日 上午10:22:34
 * @version V1.0
 */
package com.midian.moma.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName: MyReceiver
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author chu
 * @date 2015年1月21日 上午10:22:34
 */
public class MyReceiver extends BroadcastReceiver {

    /*
     * (non-Javadoc)
     *
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        String msg = intent.getStringExtra("msg") + transferLongToDate("yyyy-MM-dd HH:mm:ss", System.currentTimeMillis());
        Log.i("SendReceiver", msg);
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 把毫秒转化成日期
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    private String transferLongToDate(String dateFormat,Long millSec){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date= new Date(millSec);
        return sdf.format(date);
    }
}
