package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.ui.advert.AdvertDetailActivity;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2016/3/31.
 */
public class AdvertFragmentTpl extends BaseTpl<AdvertListBean.AdvertListContent> implements View.OnClickListener {
    private TextView title_tv, start_time_tv, loc_tv, money_tv;
    private ImageView image_iv;
    private String ad_id;
    private View item;

    public AdvertFragmentTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdvertFragmentTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        image_iv = findView(R.id.image_iv);
        title_tv = findView(R.id.title_tv);
        start_time_tv = findView(R.id.start_time_tv);
        loc_tv = findView(R.id.loc_tv);
        money_tv = findView(R.id.money_tv);
        item = findView(R.id.item);
        item.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_advert_fragment;
    }

    @Override
    public void setBean(AdvertListBean.AdvertListContent bean, int position) {
        ad_id = bean.getAd_id();
        if (bean.getAd_pic_thumb_name() == null && bean.getAd_pic_thumb_name().equals("")) {
            ac.setImage(image_iv, R.drawable.icon_bg);
        } else {
            ac.setImage(image_iv, bean.getAd_pic_thumb_name());
        }
        title_tv.setText(bean.getAd_title());
        if (bean.getAd_min_startTime() != null && !bean.getAd_min_startTime().equals("")) {
            start_time_tv.setText(bean.getAd_min_startTime() + " 起");
        }
        if (bean.getAd_min_price() != null && !bean.getAd_min_price().equals("")) {
            money_tv.setText("¥" + bean.getAd_min_price());
        }
        loc_tv.setText("距离" + bean.getDistince());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item:
                Bundle bundle = new Bundle();
                if (ad_id == null) {
                    return;
                } else {
                    bundle.putString("id", ad_id);
                    UIHelper.jumpForResult(_activity, AdvertDetailActivity.class, bundle,10000);
                }
                break;
        }
    }
}
