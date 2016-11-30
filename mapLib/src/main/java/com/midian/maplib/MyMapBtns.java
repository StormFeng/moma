package com.midian.maplib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.midian.maplib.R;

/**
 * Created by MIDIAN on 2015/12/17.
 */
public class MyMapBtns extends LinearLayout{
    View foot;
    public MyMapBtns(Context context) {
        super(context);
        init(context);
    }

    public MyMapBtns(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context){
        foot= LayoutInflater.from(context).inflate(R.layout.map_btns,null);
        foot.findViewById(R.id.top_bar).setVisibility(View.GONE);
        foot.findViewById(R.id.refresh).setVisibility(View.INVISIBLE);
        addView(foot);
    }

}
