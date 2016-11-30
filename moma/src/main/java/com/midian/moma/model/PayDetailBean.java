package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.8支付详情接口
 * Created by Administrator on 2015/12/16 0016.
 */
public class PayDetailBean extends NetResult {
    public static PayDetailBean parse(String json) throws AppException {
        PayDetailBean res = new PayDetailBean();
        try {
            res = gson.fromJson(json, PayDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private PayDetail content;

    public PayDetail getContent() {
        return content;
    }

    public void setContent(PayDetail content) {
        this.content = content;
    }

    /**
     * 备注：如是我的订单列表入口，则order_sn响应为空，则先调用7.9更改订单编号接口
     */
    public class PayDetail extends NetResult {
        private String user_name;//用户名”，
        private String user_phone;//用户联系电话”，
        private String company;//公司名称”，
        private String company_address;//公司地址”，
        private String total_price;//总价”,
        private String order_no;//订单编号”，
        private ArrayList<Advertisements> advertisements;//

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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getCompany_address() {
            return company_address;
        }

        public void setCompany_address(String company_address) {
            this.company_address = company_address;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public ArrayList<Advertisements> getAdvertisements() {
            return advertisements;
        }

        public void setAdvertisements(ArrayList<Advertisements> advertisements) {
            this.advertisements = advertisements;
        }
    }

    public class Advertisements extends NetResult {
        private String order_id;//订单id”，
        private String pic_thumb_name;//广告缩略图片名称”，
        private String pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//广告标题”，
        private String price;//商品价格”,
        private String discount_money;//优惠金额”，
        private String total_month_count;//购买总月数”,
        private String type_name;//类型名称
        private String position_name;//位置名称
        private String start_time;//开始时间”，
        private String end_time;//结束时间
        private String invoice_type;//发票类型”，
        private String invoice_title;//发票抬头

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

        public String getInvoice_type() {
            return invoice_type;
        }

        public void setInvoice_type(String invoice_type) {
            this.invoice_type = invoice_type;
        }

        public String getInvoice_title() {
            return invoice_title;
        }

        public void setInvoice_title(String invoice_title) {
            this.invoice_title = invoice_title;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
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

        public String getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(String discount_money) {
            this.discount_money = discount_money;
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
