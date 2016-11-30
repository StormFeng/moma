package com.midian.moma.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.midian.moma.R;

import midian.baselib.utils.AnimationHelper;

/**
 * Created by chu on 2016/1/10.
 */
public class GuideView extends LinearLayout implements View.OnClickListener {

    private OpenCloseListener openCloseListener;
    private String  position;

    public GuideView(Context context) {
        super(context);
        init(context);
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GuideView(Context context, String position) {
        super(context);
        this.position = position;

    }

    private void init(Context context) {
        setOnClickListener(this);
        View v = View.inflate(context, R.layout.menu_dialog, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(v, params);
       /* TextView footer = new TextView(context);
//		footer.setText(getResources().getString(R.string.please_select_your_gateway));
        footer.setTextColor(getResources().getColor(R.color.dp_gray));
        footer.setPadding(0, Func.Dp2Px(context, 30), 0, Func.Dp2Px(context, 10));
        footer.setGravity(Gravity.CENTER);
        footer.setBackgroundResource(android.R.color.white);
        listView.addFooterView(footer);*/
    }

    @Override
    public void onClick(View v) {
        close();
    }


    public void close() {
        if (openCloseListener != null) {
            openCloseListener.close();
        }
        AnimationHelper.getFadeOutAnimator(this).setDuration(200).start();
    }

    public void show() {
        try {
        if (openCloseListener != null) {
            openCloseListener.open();
        }
        AnimationHelper.getFadeInAnimator(this).setDuration(200).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }


    public OpenCloseListener getOpenCloseListener() {
        return openCloseListener;
    }

    public void setOpenCloseListener(OpenCloseListener openCloseListener) {
        this.openCloseListener = openCloseListener;
    }

    public interface OpenCloseListener {
        void open();

        void close();
    }
}
