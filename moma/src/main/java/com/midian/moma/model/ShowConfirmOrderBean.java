package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.6生成确认订单列表(立即购买接口)
 * Created by chu on 2015.12.1.001.
 */
public class ShowConfirmOrderBean extends NetResult {
    public static ShowConfirmOrderBean parse(String json) throws AppException {
        ShowConfirmOrderBean res = new ShowConfirmOrderBean();
        try {
            res = gson.fromJson(json, ShowConfirmOrderBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ShowConfirmOrder content;

    public ShowConfirmOrder getContent() {
        return content;
    }

    public void setContent(ShowConfirmOrder content) {
        this.content = content;
    }

    public class ShowConfirmOrder extends NetResult {
        private String user_name;//用户名”，
        private String user_phone;//用户联系电话”，
        private String company_name;//公司名称”,
        private String company_address;//公司地址
        private String total_price;//总价”,
        private String type;//订单类型。1：普通订单，2：预定订单”
        private ArrayList<Advertisements> advertisements;


        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

        public String getCompany_address() {
            return company_address;
        }

        public void setCompany_address(String company_address) {
            this.company_address = company_address;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_phone() {
            return user_phone;
        }

        public void setUser_phone(String user_phone) {
            this.user_phone = user_phone;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ArrayList<Advertisements> getAdvertisements() {
            return advertisements;
        }

        public void setAdvertisements(ArrayList<Advertisements> advertisements) {
            this.advertisements = advertisements;
        }
    }

    public class Advertisements extends NetResult {
        private String cart_id;//购物车id”，
        private String ad_child_id;//子广告id
        private String pic_thumb_name;//广告缩略图片名称”，
        private String pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//广告标题”，
        private String price;//价格”,
        private String discount_money;//优惠金额
        private String total_month_count;//购买总月数，包括赠送月份”,
        private String type_name;//类型名称”,
        private String position_name;//位置名称
        private String start_time;//预订开始时间”，
        private String end_time;//结束时间。包括：赠送月份”，

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

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTotal_month_count() {
            return total_month_count;
        }

        public void setTotal_month_count(String total_month_count) {
            this.total_month_count = total_month_count;
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

    }
}
