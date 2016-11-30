package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.11微信支付订单（重新支付接口）
 * Created by chu on 2016/1/9.
 */
public class PayWxOrderBean extends NetResult {
    public static PayWxOrderBean parse(String json) throws AppException {
        PayWxOrderBean res = new PayWxOrderBean();
        try {
            System.out.println("json::::::::::::" + json);
            res = gson.fromJson(json, PayWxOrderBean.class);
            if (res.getContent() != null && res != null && res.isOK() && json.contains("package")) {
                WechatParameter wechat = res.getContent().getWechatParameter();

                JSONObject jsonObject = new JSONObject(json);
//                String wechatParameter=jsonObject.getJSONObject("content").getString("wechatParameter");

                wechat.setPackageValue(jsonObject.getJSONObject("content").getJSONObject("wechatParameter").getString("package"));
            }
            System.out.println("json::::::::::::" + json);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        } catch (JSONException e) {
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ConfirmOrder content = new ConfirmOrder();

    public ConfirmOrder getContent() {
        return content;
    }

    public void setContent(ConfirmOrder content) {
        this.content = content;
    }

    public class ConfirmOrder extends NetResult {
        private String order_no;//订单号",
        private String pay_type;//1",

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        WechatParameter wechatParameter;

        public WechatParameter getWechatParameter() {
            return wechatParameter;
        }

        public void setWechatParameter(WechatParameter wechatParameter) {
            this.wechatParameter = wechatParameter;
        }


        //                "wechatParameter":{
//            "appid":"wx90c6a227bcba68332e783",
//                    "noncestr":"31445061336922e0ee72d0da227f6fee773c",
//                    "package":"Sign=WXPay",
//                    "partnerid":"12222291190331",
//                    "prepayid":"12010002200220141204628a83522524384de809",
//                    "sign":"0752740dc2649442c70075a90d22e45eee5650063cf85",
//                    "timestamp":"1417336591244596


    }

    public class WechatParameter {
        private String appid;
        private String noncestr;
        private String packageValue;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public String getPartnerid() {
            return partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
