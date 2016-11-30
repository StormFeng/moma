package com.midian.moma.itemview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.MyOrdersBean.MyorderContent;
import com.midian.moma.ui.shopping.ConfirmOrderActivity;
import com.midian.moma.ui.shopping.MyOrderActivity;
import com.midian.moma.ui.shopping.OrderDetailActivity;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import midian.baselib.api.ApiCallback;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 待付款
 *
 * @author chu
 */
public class PendingPayTpl extends BaseTpl<MyorderContent> implements OnClickListener, ApiCallback {
    private ImageView image;
    private TextView title, money, time, status_tv;
    //	private TextView combo, welf;// 套餐、套餐内容//界面已隐藏
    private View item;
    private String order_id;
    private Button cancel, button;
    private int position;
    private String ad_id,status,start_time,end_time,orderType;
    private TextView type_loc;

    public PendingPayTpl(Context context) {
        super(context);
    }

    public PendingPayTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initView() {
        image = findView(R.id.image);
        title = findView(R.id.title);
        money = findView(R.id.money);
        time = findView(R.id.time);
        status_tv = findView(R.id.status);
        cancel = findView(R.id.cancel);
        button = findView(R.id.button);
        item = findView(R.id.item);
        item.setOnClickListener(this);
        cancel.setOnClickListener(this);
        button.setOnClickListener(this);
        type_loc = findView(R.id.type_loc);

        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            orderType = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            orderType = "2";//1：普通订单，2：预定订单
        }



    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_pending_pay_tpl;
    }

    @Override
    public void setBean(MyorderContent bean, int position) {
        //0：全部，1：待付款，2：待提交，3待审核，4已完成
        this.position = position;
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
            start_time = bean.getStart_time();
            this.end_time = bean.getEnd_time();
            money.setText(bean.getOrder_price());
            time.setText(bean.getStart_time() + " - " + bean.getEnd_time());
            status = bean.getOrder_status();
            if ("1".equals(status)) {
                status_tv.setText("待付款");
                cancel.setVisibility(View.VISIBLE);
                button.setText("立即付款");
            }

        }
    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        switch (v.getId()) {
            case R.id.item:
                String type = "1";// 1表示待付款

                mBundle.putString("type", type);
                mBundle.putString("order_id", order_id);
                UIHelper.jump(_activity, OrderDetailActivity.class, mBundle);
                break;
            case R.id.button:

                mBundle.putString("order_id", order_id);
                mBundle.putString("buyType", "3");//1为购物车入口/2为广告位详情传参(即立即购买)入口，3为我的订单及推送入口
                mBundle.putString("ad_id", ad_id);
                mBundle.putString("start_time", start_time);
                mBundle.putString("end_time", end_time);
                mBundle.putString("orderType", orderType);
                UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle);// 生成确认订单接口
                break;
            case R.id.cancel:
                AppUtil.getMomaApiClient(ac).cancleOrder(order_id, this);
                break;
        }

    }

    @Override
    public void onApiStart(String tag) {
        ((MyOrderActivity) _activity).showLoadingDlg();
    }

    @Override
    public void onApiLoading(long count, long current, String tag) {
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        ((MyOrderActivity) _activity).hideLoadingDlg();
        if (res.isOK()) {
            if ("cancleOrder".equals(tag)) {
                UIHelper.t(_activity, "取消订单成功");
                data.remove(position);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
    }

    @Override
    public void onParseError(String tag) {
    }
}
