package com.midian.moma.itemview;

import com.midian.moma.R;
import com.midian.moma.model.MyOrdersBean.MyorderContent;
import com.midian.moma.ui.advert.UploadActivity;
import com.midian.moma.ui.shopping.OrderDetailActivity;
import com.midian.moma.urlconstant.UrlConstant;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 待审核
 *
 * @author chu
 *
 */
public class PendingAuditTpl extends BaseTpl<MyorderContent> implements OnClickListener {
    private ImageView image;
    private TextView title, money, time, status_tv, button;
    private View item;
    private String order_id,ad_id,status;
    private TextView type_loc;

    public PendingAuditTpl(Context context) {
        super(context);
    }

    public PendingAuditTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        image = findView(R.id.image);
        title = findView(R.id.title);
        money = findView(R.id.money);
        time = findView(R.id.time);
        status_tv = findView(R.id.status);
        button = findView(R.id.button);
        item = findView(R.id.item);
        type_loc = findView(R.id.type_loc);
        item.setOnClickListener(this);
        button.setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_pending_audit_tpl;
    }

    @Override
    public void setBean(MyorderContent bean, int position) {
        //0：全部，1：待付款，2：待提交，3待审核，4已完成
        if (bean == null) {
            return;
        } else {
            if (TextUtils.isEmpty(bean.getPic_thumb_name())) {
                ac.setImage(image, R.drawable.default_button);
            } else {
                ac.setImage(image, UrlConstant.BASEFILEURL +bean.getPic_thumb_name());
            }
            ad_id = bean.getAd_child_id();
            type_loc.setText("类型:"+bean.getAd_type_name()+"；位置："+bean.getAd_position_name()+";");
            order_id = bean.getOrder_id();
            title.setText(bean.getAd_title());
            money.setText(bean.getOrder_price());
            time.setText(bean.getStart_time() + " - " + bean.getEnd_time());
            status = bean.getOrder_status();
            if ("3".equals(status)) {
                status_tv.setText("待审核");
                button.setText("编辑资料");
            }
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.item:
                String type = "3";
                bundle.putString("type", type);
                bundle.putString("order_id", order_id);
                UIHelper.jump(_activity, OrderDetailActivity.class, bundle);
                break;
            case R.id.button:
                bundle.putString("order_id", order_id);
                bundle.putString("type", "3");
                UIHelper.jump(_activity, UploadActivity.class, bundle);
                break;
        }
    }

}
