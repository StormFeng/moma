package com.midian.moma.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.midian.moma.R;

/**
 * Created by chu on 2016/5/12.
 */
public class MenuDialog extends Dialog implements View.OnClickListener {
    private ImageView ok,hint_sign,hint_loc,hint_channel;
    private String position;
    public MenuDialog(Context context) {
        super(context);
    }

    public MenuDialog(Context context,String position, int theme) {
        super(context, theme);
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_dialog);
        hint_sign = (ImageView) findViewById(R.id.hint_sign);
        hint_channel = (ImageView) findViewById(R.id.hint_channel);
        hint_loc = (ImageView) findViewById(R.id.hint_loc);
        ok = (ImageView) findViewById(R.id.ok);
        ok.setOnClickListener(this);

        if (position.equals("0")) {//根据position的不同切换不同的提醒页面
            hint_sign.setVisibility(View.VISIBLE);
            hint_channel.setVisibility(View.VISIBLE);
            hint_loc.setVisibility(View.GONE);
        } else {
            hint_sign.setVisibility(View.GONE);
            hint_channel.setVisibility(View.GONE);
            hint_loc.setVisibility(View.VISIBLE);
        }

        Display display = getWindow().getWindowManager().getDefaultDisplay(); // 为获取屏幕宽、高
        Window window = getWindow();
        WindowManager.LayoutParams windowLayoutParams = window.getAttributes(); // 获取对话框当前的参数值
        windowLayoutParams.width = (int) (display.getWidth()); // 宽度设置为屏幕宽
        windowLayoutParams.height = (int) (display.getHeight()); // 高度设置为屏幕高

    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}