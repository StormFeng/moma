package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 5.3地图附近广告位列表
 * Created by chu on 2015.12.1.001.
 */
public class NearMapBean extends NetResult {
    public static NearMapBean parse(String json) throws AppException {
        NearMapBean res = new NearMapBean();
        try {
            res = gson.fromJson(json, NearMapBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }


    private ArrayList<NearContent> content;

    public ArrayList<NearContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<NearContent> content) {
        this.content = content;
    }

    public class NearContent extends NetResult {
        private String ad_id;//广告位id”,
        private String pic_thumb_name;//广告位缩略图片名称”,
        private String pic_thumb_suffix;//广告位缩略图片后缀”,
        private String lon;//经度”,
        private String lat;//纬度

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
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
    }
}
