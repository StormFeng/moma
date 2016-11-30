package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 6.1购物车列表
 * Created by chu on 2015.12.1.001.
 */
public class ShoppingCartBean extends NetResult {
    public static ShoppingCartBean parse(String json) throws AppException {
        ShoppingCartBean res = new ShoppingCartBean();
        try {
            res = gson.fromJson(json, ShoppingCartBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<ShoppingContent> content;

    public ArrayList<ShoppingContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<ShoppingContent> content) {
        this.content = content;
    }

    public class ShoppingContent extends NetResult {
        private String cart_id;//购物车id”,
        private String ad_id;//广告id
        private String ad_child_id;//广告位ID
        private String pic_thumb_name;//广告缩略图片名称”，
        private String pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//广告标题”，
//        private String status;//广告状态。1：在售，2：已售”，
        private String ad_type_name;//广告类型
        private String ad_position_name;//广告位置
        private String months;//显示月数”,
        private String start_time;//开始时间”，
        private String end_time;//结束时间”，
        private String price;//价格，总价，
        private String discount_money;//优惠金额

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
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

        private boolean isChecked;

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public String getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(String discount_money) {
            this.discount_money = discount_money;
        }

        public String getAd_child_id() {
            return ad_child_id;
        }

        public void setAd_child_id(String ad_child_id) {
            this.ad_child_id = ad_child_id;
        }

        public String getCart_id() {
            return cart_id;
        }

        public void setCart_id(String cart_id) {
            this.cart_id = cart_id;
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

        public String getMonths() {
            return months;
        }

        public void setMonths(String months) {
            this.months = months;
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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }


    }
}
