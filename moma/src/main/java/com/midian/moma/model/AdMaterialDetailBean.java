package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.2广告资料详情
 * Created by chu on 2015.12.1.001.
 */
public class AdMaterialDetailBean extends NetResult {

    public static AdMaterialDetailBean parse(String json) throws AppException {
        AdMaterialDetailBean res = new AdMaterialDetailBean();
        try {
            res = gson.fromJson(json, AdMaterialDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private AdMaterialDetail content;

    public AdMaterialDetail getContent() {
        return content;
    }

    public void setContent(AdMaterialDetail content) {
        this.content = content;
    }

    public class AdMaterialDetail extends NetResult {
        private String order_id;//订单id”，
        private String ad_demand;//广告要求”,
        private ArrayList<Pics> pics;

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAd_demand() {
            return ad_demand;
        }

        public void setAd_demand(String ad_demand) {
            this.ad_demand = ad_demand;
        }

        public ArrayList<Pics> getPics() {
            return pics;
        }

        public void setPics(ArrayList<Pics> pics) {
            this.pics = pics;
        }
    }

    public class Pics extends NetResult {
        private String pic_id;//图片id”,
        private String pic_thumb_name;//广告缩略图片名称”，
        private String pic_thumb_suffix;//广告缩略图片后缀

        public String getPic_id() {
            return pic_id;
        }

        public void setPic_id(String pic_id) {
            this.pic_id = pic_id;
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
    }
}
