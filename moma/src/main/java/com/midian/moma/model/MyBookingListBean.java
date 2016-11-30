package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 9.2我的预订列表
 * Created by chu on 2015.12.1.001.
 */
public class MyBookingListBean extends NetResult {
    public static MyBookingListBean parse(String json) throws AppException {
        MyBookingListBean res = new MyBookingListBean();
        try {
            res = gson.fromJson(json, MyBookingListBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<BookingContent> content;

    public ArrayList<BookingContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<BookingContent> content) {
        this.content = content;
    }

    public class BookingContent extends NetResult {
        private String reserve_id;//预订订单id”，
        private String ad_child_id;//广告位id
        private String pic_thumb_name;//广告缩略图片名称”,
        private String pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//订单广告标题”，
        private String type_name;//类型
        private String position_name;//位置
        private String price;//实际价格(优惠后)”，
        private String discount_money;//优惠金额

        private String startTime;//订单开始时间”，
        private String endTime;//订单结束时间，包括赠送时间”，
        private String status;//订单状态。0：全部，1：待审核，2：已通过，3已拒绝


        public String getAd_child_id() {
            return ad_child_id;
        }

        public void setAd_child_id(String ad_child_id) {
            this.ad_child_id = ad_child_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getPosition_name() {
            return position_name;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        public String getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(String discount_money) {
            this.discount_money = discount_money;
        }

        public String getReserve_id() {
            return reserve_id;
        }

        public void setReserve_id(String reserve_id) {
            this.reserve_id = reserve_id;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }


        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
