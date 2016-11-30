package com.midian.moma.wxapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.midian.UMengUtils.Constant;
import com.midian.moma.model.ConfirmOrderBean;
import com.midian.moma.ui.advert.PaySucessActivity;
import com.midian.moma.utils.AppUtil;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    static public String TYPE = "Type";
    private IWXAPI api;
    String user_name = "";
    String user_phone = "";
    String user_company = "";
    String user_address = "";

    String invoice_type = "";
    String invoice_title = "";
    String cart_ids = "";
    String ad_child_id = "";
    String start_time = "";
    String end_time = "";
    static String order_id = "";

    String buyType = "";//判断购买入口：1为购物车入口、2为订单详情立即购买入口、3为我的订单及推送的重新支付入口
    static String order_sn = "";//订单编号
    //    static String order_no = "";//订单编号
//    static String order_no_id = "";//订单编号
    String type = "";//1是立即支付
    static ConfirmOrderBean mConfirmOrderBean;

    public static void gotoActivity(Context mContext, String buyType, String order_sn, String type, String user_name, String user_phone,
                                    String user_company, String user_address, String invoice_type,
                                    String invoice_title, String cart_ids, String ad_id,
                                    String start_time, String end_time, String order_id) {
        Intent intent = new Intent(mContext, WXPayEntryActivity.class);
        intent.putExtra("flag", true);
        intent.putExtra("buyType", buyType);
        intent.putExtra("order_sn", order_sn);
        intent.putExtra(TYPE, type);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_phone", user_phone);
        intent.putExtra("user_company", user_company);
        intent.putExtra("user_address", user_address);
        intent.putExtra("invoice_type", invoice_type);
        intent.putExtra("invoice_title", invoice_title);
        intent.putExtra("cart_ids", cart_ids);
        intent.putExtra("ad_id", ad_id);

        intent.putExtra("start_time", start_time);
        intent.putExtra("end_time", end_time);
        intent.putExtra("order_id", order_id);


        mContext.startActivity(intent);
    }

    public static void gotoActivity(Context mContext, String buyType, String order_sn, String order_id) {
        Intent intent = new Intent(mContext, WXPayEntryActivity.class);
        intent.putExtra("flag", true);
        intent.putExtra("buyType", buyType);
        intent.putExtra("order_sn", order_sn);
        intent.putExtra("order_id", order_id);
        mContext.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = new View(this);
        view.setBackgroundColor(getResources().getColor(
                android.R.color.transparent));
        setContentView(view);
        api = WXAPIFactory.createWXAPI(this, Constant.weixinAppId, false);
        api.handleIntent(getIntent(), this);
        boolean flag = getIntent().getBooleanExtra("flag", false);
        type = getIntent().getStringExtra(TYPE);


        if (flag) {
            getdata();
            weixinPay();
        }
    }

    private void getdata() {
        buyType = getIntent().getStringExtra("buyType");
        order_sn = getIntent().getStringExtra("order_sn");
        user_name = getIntent().getStringExtra("user_name");
        user_phone = getIntent().getStringExtra("user_phone");
        user_company = getIntent().getStringExtra("user_company");
        user_address = getIntent().getStringExtra("user_address");
        invoice_type = getIntent().getStringExtra("invoice_type");
        invoice_title = getIntent().getStringExtra("invoice_title");
        cart_ids = getIntent().getStringExtra("cart_ids");
        ad_child_id = getIntent().getStringExtra("ad_id");
        start_time = getIntent().getStringExtra("start_time");
        end_time = getIntent().getStringExtra("end_time");
        order_id = getIntent().getStringExtra("order_id");
        System.out.println("744order_sn::::::" + order_sn);
    }

    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        if ("confirmAdvertisement".equals(tag) || "updateOrderSn".equals(tag) || "payWxOrder".equals(tag)) {
            if (res.isOK()) {
                mConfirmOrderBean = (ConfirmOrderBean) res;
                order_sn = mConfirmOrderBean.getContent().getOrder_no();
                System.out.println("mConfirmOrderBeanDSn::::" + order_sn);
                System.out.println("mConfirmOrderBean:::::" + mConfirmOrderBean.toString());
                weixinapi(mConfirmOrderBean);
            } else {

                ac.handleErrorCode(_activity, res.getError_code());
                finish();
            }
        }

    }

    private void weixinapi(ConfirmOrderBean mConfirmOrderBean) {
        if (mConfirmOrderBean == null) return;
        PayReq req = new PayReq();
        ConfirmOrderBean.WechatParameter wechatParameter = mConfirmOrderBean.getContent().getWechatParameter();
        req.appId = wechatParameter.getAppid();
        req.partnerId = wechatParameter.getPartnerid();
        req.prepayId = wechatParameter.getPrepayid();
        req.nonceStr = wechatParameter.getNoncestr();
        req.timeStamp = wechatParameter.getTimestamp();
        req.packageValue = wechatParameter.getPackageValue();
        req.sign = wechatParameter.getSign();
        System.out.println("weixinapimConfirmOrderBean:::::" + mConfirmOrderBean.toString());
        api.sendReq(req);
        finish();
    }

    private void weixinPay() {
        //从我的订单使用微信支付或重新支付调7.11微信支付订单接口
        //1为购物车入口、2为订单详情立即购买入口、3为我的订单及推送的重新支付入口
        if ("3".equals(buyType)) {
            RePay();
        } else {
            PayRightNow();
        }

    }

    private void PayRightNow() {
        //如果1为购物车入口、2为订单详情立即购买入口,则调用确认订单接口进行直接支付
        System.out.println("user_name::" + user_name + ":::user_phone:::" + user_phone + "::::user_company::" + user_company +
                "::::user_address:::::::" + user_address + "::::invoice_type" + invoice_type+":::invoice_title:"+invoice_title);
        AppUtil.getMomaApiClient(ac).confirmAdvertisement("2", user_name, user_phone, user_company, user_address, invoice_type, invoice_title, cart_ids, ad_child_id, start_time, end_time, this);
    }

    private void RePay() {

//        weixinapi(mConfirmOrderBean);
        if(TextUtils.isEmpty(order_sn)){
            AppUtil.getMomaApiClient(ac).updateOrderSn("2",order_id,this);
        }else{
            AppUtil.getMomaApiClient(ac).payWxOrder(order_sn, this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent == null)
            intent = new Intent();
        intent.putExtra("order_sn", order_sn);
        intent.putExtra("order_id", order_id);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    private void fail() {
//        UIHelper.t(_activity,"支付失败");
        System.out.println("支付失败回传的order_id:::::" + order_id);
        PaySucessActivity.gotoActivity(_activity, PaySucessActivity.FAILED, "2", order_sn, order_id);
        finish();
    }

    @Override
    public void onReq(BaseReq req) {

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if (resp.errCode == BaseResp.ErrCode.ERR_OK) {// 支付成功
                PaySucessActivity.gotoActivity(_activity, PaySucessActivity.SUCCESS, "2", order_sn, order_id);
            } else {
                fail();
            }
        }
        finish();
    }

}