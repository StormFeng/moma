package com.midian.moma.itemview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.configlib.ServerConstant;
import com.midian.moma.R;
import com.midian.moma.model.PayDetailBean.Advertisements;

import midian.baselib.view.BaseTpl;

/**
 * Created by chu on 2015/12/28.
 */
public class ItemPayDetailOrderTpl extends BaseTpl<Advertisements> {

    private TextView title, money, ad_type,ad_loc,days,buy_time;
    private ImageView image;

    public ItemPayDetailOrderTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemPayDetailOrderTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        image = findView(R.id.image);
        title = findView(R.id.title);
        money = findView(R.id.money);
        days = findView(R.id.days);
        ad_type = findView(R.id.ad_type);
        ad_loc = findView(R.id.ad_loc);
        buy_time = findView(R.id.buy_time);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_pay_detail_order_list_tpl;
    }

    @Override
    public void setBean(Advertisements bean, int position) {
        if (TextUtils.isEmpty(bean.getPic_thumb_name())) {
            image.setBackgroundResource(R.drawable.icon_home_bg);
        } else {
            ac.setImage(image, ServerConstant.BASEFILEURL + bean.getPic_thumb_name());
        }
        title.setText(bean.getAd_title());
        money.setText(bean.getPrice());
        days.setText(" (共" + bean.getTotal_month_count() + "个月)");
        ad_type.setText(bean.getType_name());
        ad_loc.setText(bean.getPosition_name());
        buy_time.setText(bean.getStart_time() + " - " + bean.getEnd_time());
    }
}
