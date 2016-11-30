package com.midian.moma.ui.advert;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.model.SysConfListBean;
import com.midian.moma.ui.shopping.ConfirmOrderActivity;
import com.midian.moma.utils.AppUtil;

import midian.baselib.app.AppManager;
import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;

public class PaySucessActivity extends BaseActivity {
    private BaseLibTopbarView topbar;
    private Button pay, upload;
    private String type;
    private String pay_type;
    private View failed_ll, success_ll;
    private TextView wait, phone;//稍后上传
    public static final String SUCCESS = "success";
    public static final String FAILED = "failed";
    private String order_id;
    private String price;
    private String order_No;

    public static void gotoActivity(Activity mContext, String type, String pay_type, String order_No, String order_id, String price) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("pay_type", pay_type);
        bundle.putString("order_No", order_No);
        bundle.putString("order_id", order_id);
        bundle.putString("price", price);
        UIHelper.jump(mContext, PaySucessActivity.class, bundle);
    }

    public static void gotoActivity(Activity mContext, String type, String pay_type, String order_No, String order_id) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("pay_type", pay_type);
        bundle.putString("order_No", order_No);
        bundle.putString("order_id", order_id);
        UIHelper.jump(mContext, PaySucessActivity.class, bundle);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_success);

        topbar = findView(R.id.topbar);
        topbar.setTitle("支付结果").setLeftImageButton(R.drawable.icon_back, null).setLeftText("取消",
                UIHelper.finish(_activity));
        order_id = getIntent().getStringExtra("order_id");//订单id
        failed_ll = findView(R.id.failed_ll);//支付失败View
        success_ll = findView(R.id.success_ll);//支付成功View
        pay = findView(R.id.pay);//重新支付
        upload = findView(R.id.upload);//上传资料
        wait = findView(R.id.wait);//稍后上传
        phone = findView(R.id.phone);//联系电话

        type = getIntent().getStringExtra("type");
        pay_type = getIntent().getStringExtra("pay_type");
        price = getIntent().getStringExtra("price");
        order_No = getIntent().getStringExtra("order_No");
        System.out.println("order_No:::::" + order_No);
        if (SUCCESS.equals(type)) {
            failed_ll.setVisibility(View.GONE);
            success_ll.setVisibility(View.VISIBLE);
        } else if (FAILED.equals(type)) {
            success_ll.setVisibility(View.GONE);
            failed_ll.setVisibility(View.VISIBLE);
            //支付失败显示的企业联系电话
            AppUtil.getMomaApiClient(ac).getSysConfList(null, this);
        }

        wait.setOnClickListener(this);
        pay.setOnClickListener(this);
        upload.setOnClickListener(this);
        AppManager.getAppManager().finishActivity(ConfirmOrderActivity.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.pay://重新支付
                Bundle mBundle1 = new Bundle();
                mBundle1.putString("order_id", order_id);
                mBundle1.putString("buyType", "3");//1为购物车入口/2为广告位详情传参(即立即购买)入口，3为我的订单及推送入口
                mBundle1.putString("order_sn", order_No);
                System.out.println("重新支付订单id；：：：" + order_id + ":::重新支付时的订单编号：：：" + order_No);
                UIHelper.jump(_activity, ConfirmOrderActivity.class, mBundle1);// 生成确认订单接口
//                if ("1".equals(pay_type)) {
//                    new PayUtil(_activity, new PayUtil.CallBack() {
//                        @Override
//                        public void complete(boolean stat) {
//                            PaySucessActivity.gotoActivity(_activity, stat ? PaySucessActivity.SUCCESS : PaySucessActivity.FAILED,"1",order_No, order_id, "0.01");
////                            if (SUCCESS.equals(type)) {
////                                failed_ll.setVisibility(View.GONE);
////                                success_ll.setVisibility(View.VISIBLE);
////                            } else if (FAILED.equals(type)) {
////                                success_ll.setVisibility(View.GONE);
////                                failed_ll.setVisibility(View.VISIBLE);
////                                //支付失败显示的企业联系电话
////                                AppUtil.getMomaApiClient(ac).getSysConfList(null, PaySucessActivity.this);
////                            }
//                        }
//                    }).pay(order_No, price, "moma", "moma");//总价totalPrice替换0.01
//                } else {
//
//                    WXPayEntryActivity.gotoActivity(_activity,"3",order_No,order_id);
//
//                }
                finish();
                break;
            case R.id.upload://上传资料
                    Bundle mBundle = new Bundle();
                    mBundle.putString("order_id", order_id);
                    mBundle.putString("type", "1");
                    UIHelper.jump(_activity, UploadActivity.class,mBundle);
                break;
            case R.id.wait:
                finish();
                break;
        }
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        hideLoadingDlg();
        if (res.isOK()) {
            SysConfListBean bean = (SysConfListBean) res;
            phone.setText(bean.getContent().getContact_phone());
        }
    }
}
