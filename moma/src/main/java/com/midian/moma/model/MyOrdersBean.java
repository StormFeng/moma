package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.4我的订单列表
 * Created by chu on 2015.12.1.001.
 */
public class MyOrdersBean extends NetResult {
    public static MyOrdersBean parse(String json) throws AppException {
        MyOrdersBean res = new MyOrdersBean();
        try {
            res = gson.fromJson(json, MyOrdersBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<MyorderContent> content;

    public ArrayList<MyorderContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<MyorderContent> content) {
        this.content = content;
    }

    public class MyorderContent extends NetResult {
        private String order_id;//订单id”,
        private String ad_child_id;//子广告id”,
        private String pic_thumb_name;//广告缩略图片名称”,
        private String pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//订单广告标题”，
        private String ad_type_name;//广告位类型名称
        private String ad_position_name;//广告位位置名称
        private String order_price;//订单实际金额
        private String start_time;//订单开始时间”，
        private String end_time;//订单结束时间，包括赠送时间”，
        private String order_status;//订单状态。0：全部，1：待付款，2：待提交，3待审核，4已完成”

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAd_child_id() {
            return ad_child_id;
        }

        public void setAd_child_id(String ad_child_id) {
            this.ad_child_id = ad_child_id;
        }

        public String getAd_type_name() {
            return ad_type_name;
        }

        public void setAd_type_name(String ad_type_name) {
            this.ad_type_name = ad_type_name;
        }

        public String getAd_position_name() {
            return ad_position_name;
        }

        public void setAd_position_name(String ad_position_name) {
            this.ad_position_name = ad_position_name;
        }

        public String getPic_thumb_name() {
            return pic_thumb_name;
        }

        public void setPic_thumb_name(String pic_thumb_name) {
            this.pic_thumb_name = pic_thumb_name;
        }

        public String getPic_thumb_suffix() {
            return pic_thumb_suffix;
        }

        public void setPic_thumb_suffix(String pic_thumb_suffix) {
            this.pic_thumb_suffix = pic_thumb_suffix;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }


        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }
    }

}
