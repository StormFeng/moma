package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.3获取已支付待提交资料订单
 * Created by chu on 2015.12.1.001.
 */
public class WaitCommitOrderBean extends NetResult {
    public static WaitCommitOrderBean parse(String json) throws AppException {
        WaitCommitOrderBean res = new WaitCommitOrderBean();
        try {
            res = gson.fromJson(json, WaitCommitOrderBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<WaitCommitOrder> content;

    public ArrayList<WaitCommitOrder> getContent() {
        return content;
    }

    public void setContent(ArrayList<WaitCommitOrder> content) {
        this.content = content;
    }

    public class WaitCommitOrder extends NetResult {
        private String order_id;//订单id”,
        private String ad_pic_thumb_name;//订单广告缩略图片”,
        private String ad_pic_thumb_suffix;//订单广告缩略图片后缀”，
        private String order_ad_title;//订单广告标题”，
        private String order_startTime;//订单开始时间”，
        private String order_endTime;//订单结束时间，即包括赠送到期时间”，
        private String order_price;//订单价格

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAd_pic_thumb_name() {
            return ad_pic_thumb_name;
        }

        public void setAd_pic_thumb_name(String ad_pic_thumb_name) {
            this.ad_pic_thumb_name = ad_pic_thumb_name;
        }

        public String getAd_pic_thumb_suffix() {
            return ad_pic_thumb_suffix;
        }

        public void setAd_pic_thumb_suffix(String ad_pic_thumb_suffix) {
            this.ad_pic_thumb_suffix = ad_pic_thumb_suffix;
        }

        public String getOrder_ad_title() {
            return order_ad_title;
        }

        public void setOrder_ad_title(String order_ad_title) {
            this.order_ad_title = order_ad_title;
        }

        public String getOrder_startTime() {
            return order_startTime;
        }

        public void setOrder_startTime(String order_startTime) {
            this.order_startTime = order_startTime;
        }

        public String getOrder_endTime() {
            return order_endTime;
        }

        public void setOrder_endTime(String order_endTime) {
            this.order_endTime = order_endTime;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }
    }
}
