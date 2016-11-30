package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 地图重叠的广告位列表
 * Created by chu on 2016/1/8.
 */
public class MapOverlapBean extends NetResult {

    public static MapOverlapBean parse(String json) throws AppException {
        MapOverlapBean res = new MapOverlapBean();
        try {
            res = gson.fromJson(json, MapOverlapBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private MapOverContent content;

    public MapOverContent getContent() {
        return content;
    }

    public void setContent(MapOverContent content) {
        this.content = content;
    }

    public class MapOverContent extends NetResult {

        private ArrayList<Advertisements> advertisements;

        public ArrayList<Advertisements> getAdvertisements() {
            return advertisements;
        }

        public void setAdvertisements(ArrayList<Advertisements> advertisements) {
            this.advertisements = advertisements;
        }
    }

    public class Advertisements extends NetResult {
        private String ad_id;//广告id”，
        private String ad_pic_thumb_name;//广告缩略图片名称”，
        private String ad_pic_thumb_suffix;//广告缩略图片后缀”，
        private String ad_title;//广告标题”，
//        private String ad_status;//广告位在售情况，1：在售，2：已售”，
        private String ad_min_startTime;//广告位最近开始时间
        private String distince;//距离”，
        private String ad_min_price;//广告位最低价格


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

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
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

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }


        public String getDistince() {
            return distince;
        }

        public void setDistince(String distince) {
            this.distince = distince;
        }

    }


}