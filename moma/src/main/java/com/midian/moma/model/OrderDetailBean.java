package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 7.5订单详情
 * Created by chu on 2015.12.1.001.
 */
public class OrderDetailBean extends NetResult {
    public static OrderDetailBean parse(String json) throws AppException {
        OrderDetailBean res = new OrderDetailBean();
        try {
            res = gson.fromJson(json, OrderDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private OrderDetail content = new OrderDetail();

    public OrderDetail getContent() {
        return content;
    }

    public void setContent(OrderDetail content) {
        this.content = content;
    }

    public class OrderDetail extends NetResult {
        private String order_id;//订单id”，
        private String order_sn;//订单编号”，
        private String order_status;//订单状态。0：全部，1：待付款，2：待提交，3待审核，4已完成”，
        private String order_time;//订单时间”,
        private String user_name;//联系人”，
        private String user_phone;//电话”，
        private String company_name;//公司名称”，
        private String company_address;//公司地址”，
        private String ad_child_id;//广告id”,
        private String unit_price;//广告单价
        private String ad_pic_name;//广告图片名称”，
        private String ad_pic_suffix;//广告图片后缀”，
        private String ad_title;//广告标题”，
        private String total_price;//商品价格”，
        private String discount_money;//优惠金额”，
        private String pay_money;//应付金额
        private String type_name;//类型
        private String position_name;//位置
        private String start_time;//开始时间”，
        private String end_time;//结束时间，包括赠送时间”，
        private String material_demand;//广告要求”，
        private ArrayList<Material_pic> material_pics;


        public String getAd_child_id() {
            return ad_child_id;
        }

        public void setAd_child_id(String ad_child_id) {
            this.ad_child_id = ad_child_id;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
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

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(String discount_money) {
            this.discount_money = discount_money;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getOrder_time() {
            return order_time;
        }

        public void setOrder_time(String order_time) {
            this.order_time = order_time;
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


        public String getAd_pic_name() {
            return ad_pic_name;
        }

        public void setAd_pic_name(String ad_pic_name) {
            this.ad_pic_name = ad_pic_name;
        }

        public String getAd_pic_suffix() {
            return ad_pic_suffix;
        }

        public void setAd_pic_suffix(String ad_pic_suffix) {
            this.ad_pic_suffix = ad_pic_suffix;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
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


        public String getMaterial_demand() {
            return material_demand;
        }

        public void setMaterial_demand(String material_demand) {
            this.material_demand = material_demand;
        }

        public ArrayList<Material_pic> getMaterial_pics() {
            return material_pics;
        }

        public void setMaterial_pics(ArrayList<Material_pic> material_pics) {
            this.material_pics = material_pics;
        }
    }

    public class Material_pic extends NetResult {
        private String pic_thumb_name;//缩略图片名称”，
        private String pic_thumb_suffix;//缩略图片后缀”

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
    }
}
