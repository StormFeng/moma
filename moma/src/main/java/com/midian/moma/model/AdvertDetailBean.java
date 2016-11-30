package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 5.2广告位详情
 * Created by chu on 2015.12.1.001.
 */
public class AdvertDetailBean extends NetResult {
    public static AdvertDetailBean parse(String json) throws AppException {
        AdvertDetailBean res = new AdvertDetailBean();
        try {
            res = gson.fromJson(json, AdvertDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private AdvertContent content;

    public AdvertContent getContent() {
        return content;
    }

    public void setContent(AdvertContent content) {
        this.content = content;
    }

    public class AdvertContent extends NetResult {
        private String ad_id;//广告位id”,
        private List<Ad_pic> ad_pic;
        private String ad_title;//广告名称”,
        //        private String status;//状态，1：在售，2：已售”,
        private String ad_min_startTime;//广告位最近开售时间
        private String create_time;//发布时间”,
        private String ad_min_price;//广告位最低价格”,
        private String phone;//联系电话”，
        private String address;//联系地址”
        private String lon;//经度
        private String lat;//纬度
        private List<Types> types;//广告类型和位置
        //        private String start_time;//广告位开始月份”，
//        private String end_time;//广告位结束月份”，
        private String ad_intro;//广告位介绍”，
        private String is_collected;//是否关注，1：关注，2：未关注
        private String collect_count;//关注数

        public String getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(String collect_count) {
            this.collect_count = collect_count;
        }

        public String getAd_min_startTime() {
            return ad_min_startTime;
        }

        public void setAd_min_startTime(String ad_min_startTime) {
            this.ad_min_startTime = ad_min_startTime;
        }

        public String getAd_min_price() {
            return ad_min_price;
        }

        public void setAd_min_price(String ad_min_price) {
            this.ad_min_price = ad_min_price;
        }


        public List<Ad_pic> getAd_pic() {
            return ad_pic;
        }

        public void setAd_pic(List<Ad_pic> ad_pic) {
            this.ad_pic = ad_pic;
        }

        public List<Types> getTypes() {
            return types;
        }

        public void setTypes(List<Types> types) {
            this.types = types;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }


        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }


        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }


        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }


        public String getAd_intro() {
            return ad_intro;
        }

        public void setAd_intro(String ad_intro) {
            this.ad_intro = ad_intro;
        }

        public String getIs_collected() {
            return is_collected;
        }

        public void setIs_collected(String s_collected) {
            this.is_collected = is_collected;
        }

       /* @Override
        public String toString() {
            return "AdvertContent{" +
                    "ad_id='" + ad_id + '\'' +
                    ", ad_pic=" + ad_pic +
                    ", ad_title='" + ad_title + '\'' +
                    ", status='" + status + '\'' +
                    ", create_time='" + create_time + '\'' +
                    ", price='" + price + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", ad_intro='" + ad_intro + '\'' +
                    ", is_collected='" + is_collected + '\'' +
                    '}';
        }*/
    }

    public class Ad_pic extends NetResult {
        private String ad_pic_name;//广告图片名称”，
        private String ad_pic_suffix;//广告图片后缀

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

        @Override
        public String toString() {
            return "Ad_pic{" +
                    "ad_pic_name='" + ad_pic_name + '\'' +
                    ", ad_pic_suffix='" + ad_pic_suffix + '\'' +
                    '}';
        }
    }

    public class Types extends NetResult {
        private String type_id;//类型id”,
        private String type_name;//类型名称
        private List<Positions> positions;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public List<Positions> getPositions() {
            return positions;
        }

        public void setPositions(List<Positions> positions) {
            this.positions = positions;
        }
    }

    public class Positions extends NetResult {
        private String ad_child_id;//子广告id”,
        private String position_id;//位置id”,
        private String position_name;//位置名称”,
        private String price;//价格/月”,
        private String start_time;//开售时间”,
        private String end_time;//结束时间”,
        private String status;//状态。1：可售；2下架”,
        private String freeze_status;//冻结状态。1：正常，2：冻结

        public String getAd_child_id() {
            return ad_child_id;
        }

        public void setAd_child_id(String ad_child_id) {
            this.ad_child_id = ad_child_id;
        }

        public String getPosition_id() {
            return position_id;
        }

        public void setPosition_id(String position_id) {
            this.position_id = position_id;
        }

        public String getPosition_name() {
            return position_name;
        }

        public void setPosition_name(String position_name) {
            this.position_name = position_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getFreeze_status() {
            return freeze_status;
        }

        public void setFreeze_status(String freeze_status) {
            this.freeze_status = freeze_status;
        }
    }

    public class Month_buy_status extends NetResult {
        private String month;//月份”，
        private String status;//买卖状态。1：可售，2：已售

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "Month_buy_status{" +
                    "month='" + month + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AdvertDetailBean{" +
                "content=" + content +
                '}';
    }
}
