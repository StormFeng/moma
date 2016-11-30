package com.midian.moma.ui;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.StartAdvertiseBean;
import com.midian.moma.ui.shopping.WelUrlActivity;
import com.midian.moma.utils.AppUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;

/**
 * Created by chu on 2016/1/12.
 */
public class WelActivity extends BaseActivity implements View.OnClickListener{
    StartAdvertiseBean content;
    ImageView iv;
    String url;
    private TextView jump_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wel);
        try {
            AppUtil.getMomaApiClient(ac).getStartAdvertise(this);
        }catch (Exception e){
            e.printStackTrace();
        }

        iv = (ImageView) findViewById(R.id.imageView1);
        jump_tv = findView(R.id.jump_tv);
        jump_tv.setOnClickListener(this);
        iv.setOnClickListener(this);
        // 3秒
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 2);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(aListener);
        iv.setAnimation(alphaAnimation);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.imageView1:
                if (url != null&&!url.equals("")) {
                    Bundle b = new Bundle();
                    b.putString("url",url);
                    UIHelper.jump(_activity, WelUrlActivity.class,b);
                }
                break;
            case R.id.jump_tv:
                UIHelper.jump(WelActivity.this, MainActivity.class);
                finish();
                break;
        }

    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            jump_tv.setVisibility(View.VISIBLE);
            content = (StartAdvertiseBean) res;
            if (content.getContent().getAd_pic_name() == null && content.getContent().getAd_pic_name().equals("")) {
                ac.setImage(iv, R.drawable.welcome);
            } else {
                ac.setImage(iv, content.getContent().getAd_pic_name());
            }
            url = content.getContent().getUrl();
        } else {
            ac.handleErrorCode(_activity, res.error_code);
        }

    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);
        hideLoadingDlg();
    }

    private AnimationListener aListener = new AnimationListener() {

        @Override
        public void onAnimationEnd(Animation arg0) {
            UIHelper.jump(WelActivity.this, MainActivity.class);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationStart(Animation arg0) {
        }
    };

}
