package com.midian.moma.itemview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.AdvertListBean;
//import com.midian.moma.model.AdvertListBean.Advertisements;
import com.midian.moma.urlconstant.UrlConstant;
import midian.baselib.view.BaseTpl;

/**
 * 选择广告位
 * Created by chu on 2016/1/12.
 */
public class ChooseadvertTpl extends BaseTpl<AdvertListBean.AdvertListContent> {

    private TextView title, sell_status, loc, money;
    private ImageView image;
    private String ad_id;
    public ChooseadvertTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChooseadvertTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        image =  findView(R.id.image);
        title =findView(R.id.title);
        sell_status = findView(R.id.sell_status);
        loc = findView(R.id.loc);
        money = findView(R.id.money);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_choose_advert_tpl;
    }

    @Override
    public void setBean(AdvertListBean.AdvertListContent bean, int position ) {
        if (bean == null) {

        } else {
            ad_id = bean.getAd_id();
            if (!TextUtils.isEmpty(bean.getAd_pic_thumb_name())) {
                ac.setImage(image, UrlConstant.BASEFILEURL + bean.getAd_pic_thumb_name());
            } else {
                ac.setImage(image, R.drawable.default_button);
            }

            /*title.setText(bean.getAd_title());
            if ("1".equals(bean.getAd_status())) {
                sell_status.setText("在售");

            } else if ("2".equals(bean.getAd_status())) {
                sell_status.setText("已售");
            }
            loc.setText("距离" + bean.getDistince());
            money.setText(bean.getPrice());*/
        }


    }
}
