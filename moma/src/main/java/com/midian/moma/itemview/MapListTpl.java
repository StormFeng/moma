package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.MapOverlapBean.Advertisements;
import com.midian.moma.ui.advert.AdvertDetailActivity;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2016/1/8.
 */
public class MapListTpl extends BaseTpl<Advertisements> implements View.OnClickListener {
    private TextView title, sell_status, loc, money;
    private ImageView image;
    private String ad_id;

    public MapListTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MapListTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        title = findView(R.id.title);
        sell_status = findView(R.id.sell_status);
        loc = findView(R.id.loc);
        money = findView(R.id.money);
        image = findView(R.id.image);
        root.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_map_list_tpl;
    }

    @Override
    public void setBean(Advertisements bean, int position) {
        ad_id = bean.getAd_id();
        if (TextUtils.isEmpty(bean.getAd_pic_thumb_name())) {
            ac.setImage(image, R.drawable.default_button);
        } else {
            ac.setImage(image, ServerConstant.BASEFILEURL + bean.getAd_pic_thumb_name());
        }
        title.setText(bean.getAd_title());
        /*if ("1".equals(bean.getAd_status())) {
            sell_status.setText("在售");
        } else {
            sell_status.setText("已售");
        }*/

        sell_status.setText(bean.getAd_min_startTime()+" 起");
        loc.setText(bean.getDistince());
        money.setText("¥"+bean.getAd_min_price());
    }

    @Override
    public void onClick(View view) {
        Bundle mBundle = new Bundle();
        mBundle.putString("id", ad_id);
        UIHelper.jump(_activity, AdvertDetailActivity.class,mBundle);

    }
}
