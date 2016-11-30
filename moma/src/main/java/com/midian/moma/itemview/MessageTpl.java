package com.midian.moma.itemview;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.midian.baidupush.DeviceMessage;
import com.midian.moma.R;
import com.midian.moma.ui.advert.UploadActivity;
import com.midian.moma.ui.shopping.ConfirmOrderActivity;
import com.midian.moma.ui.shopping.OrderDetailActivity;

import midian.baselib.utils.UIHelper;
import midian.baselib.view.BaseTpl;

/**
 * 通知消息TPL
 * Created by Administrator on 2015/11/22 0022.
 */
public class MessageTpl extends BaseTpl<DeviceMessage> implements View.OnClickListener {

    private TextView title_tv, content_tv, time_tv, status_tv;
    private ImageView red_iv;
    private View item_ll, name_ll;
    private String type, order_no, order_id, orderType;
    private DeviceMessage bean;

    public MessageTpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MessageTpl(Context context) {
        super(context);
    }

    @Override
    protected void initView() {
        title_tv = (TextView) findView(R.id.title);
        content_tv = (TextView) findView(R.id.content);
        time_tv = (TextView) findView(R.id.time);
        status_tv = (TextView) findView(R.id.status);
        red_iv = findView(R.id.red);

        item_ll = findView(R.id.item_ll);
        name_ll = findView(R.id.name_ll);
        status_tv.setOnClickListener(this);

        item_ll.setOnClickListener(this);

        //2：会员，3：销售员
        if ("2".equals(ac.user_type)) {
            orderType = "1"; //1：普通订单，2：预定订单
        } else if ("3".equals(ac.user_type)) {
            orderType = "2";//1：普通订单，2：预定订单
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_message_tpl;
    }

    @Override
    public void setBean(DeviceMessage bean, int position) {
        this.bean = bean;
        //1：待支付；2：待提交；3：审核通过；4：审核不通过；5：到期
        if (bean == null) {
            item_ll.setVisibility(View.GONE);
            name_ll.setVisibility(View.VISIBLE);
        } else {
            item_ll.setVisibility(View.VISIBLE);
            name_ll.setVisibility(View.GONE);
            String msg_title = bean.getMsg_title();//推送标题
            String msg_content = bean.getMsg_content();//推送内容
            order_no = bean.getOrder_no();//订单编号
            order_id = bean.getOrder_id();//订单id
            String ad_title = bean.getAd_title();//广告标题
            type = bean.getType();//消息类型
            String time = bean.getTime();

            if ("0".equals(bean.getIsRead())) {
                red_iv.setVisibility(View.VISIBLE);
            } else {
                red_iv.setVisibility(View.GONE);
            }

            if ("1".equals(type)) {
                title_tv.setText("您的订单下单成功啦！");
                content_tv.setText("订单号: " + order_no + " 下单成功，您现在可以付款了");
                time_tv.setText(time);
                status_tv.setText("立即支付");
            } else if ("2".equals(type)) {
                title_tv.setText("您的订单支付成功啦！");
                content_tv.setText("订单号: " + order_no + " 已支付成功，您现在可以上传广告资料了");
                time_tv.setText(time);
                status_tv.setText("上传广告资料");
            } else if ("3".equals(type)) {
                title_tv.setText("您的订单审核通过啦！");
                content_tv.setText("订单号: " + order_no + " 已审核通过");
                time_tv.setText(time);
                status_tv.setVisibility(View.GONE);
            } else if ("4".equals(type)) {
                title_tv.setText("您的订单审核未通过！");
                content_tv.setText("订单号: " + order_no + " 审核未通过,您可以尝试联系客服或者修改后再次提交。");
                time_tv.setText(time);
                status_tv.setVisibility(View.GONE);
                item_ll.setEnabled(false);
                UIHelper.t(_activity, "审核未通过，不能跳转详情");
            } else if ("5".equals(type)) {
                title_tv.setText("您的广告位已到期！");
                content_tv.setText("订单号: " + order_no + " 的广告位已到期！");
                time_tv.setText(time);
                status_tv.setVisibility(View.GONE);
                item_ll.setEnabled(false);
                UIHelper.t(_activity, "审核未通过，不能跳转详情");
            }


        }


    }

    @Override
    public void onClick(View v) {
        Bundle mBundle = new Bundle();
        switch (v.getId()) {
            case R.id.item_ll:
                mBundle.putString("type", type);
                mBundle.putString("order_id", order_id);
                UIHelper.jump(_activity, OrderDetailActivity.class, mBundle);
                break;
            case R.id.status:
                //1：待支付；2：待提交；3：审核通过；4：审核不通过；5：到期
                if ("1".equals(type)) {
                    mBundle.putString("order_id", order_id);
                    mBundle.putString("buyType", "3");//1为购物车入口/2为广告位详情传参(即立即购买)入口，3为我的订单及推送入口
                    //mBundle.putString("ad_id", ad_id);
                    mBundle.putString("orderType", orderType);
                    UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle);// 生成确认订单接口
                } else if ("2".equals(type)) {
                    mBundle.putString("order_id", order_id);
                    UIHelper.jump(_activity, UploadActivity.class, mBundle);
                }

                break;
        }
    }
}
