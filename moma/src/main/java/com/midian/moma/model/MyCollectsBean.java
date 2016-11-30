package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 3.3我的关注
 * Created by chu on 2015.12.1.001.
 */
public class MyCollectsBean extends NetResult {
    public static MyCollectsBean parse(String json) throws AppException {
        MyCollectsBean res = new MyCollectsBean();
        try {
            res = gson.fromJson(json, MyCollectsBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }


    private ArrayList<CollectContent> content;

    public ArrayList<CollectContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<CollectContent> content) {
        this.content = content;
    }

    public class CollectContent extends NetResult {
        private String collect_id;//收藏id”,
        private String type;//类型。1:广告位”
        private String record_id;//被收藏对象id”,
        private String pic_thumb_name;//图片名称”,
        private String pic_thumb_suffix;//图片后缀”,
        private String ad_name;//广告名称”
        private String min_Price;//最低价格”,
        private String start_time;//最近开始时间
        private String status;//状态”,
        private String distance;//距离
        public boolean isEdit;

        public boolean isEdit() {
            return isEdit;
        }

        public void setIsEdit(boolean isEdit) {
            this.isEdit = isEdit;
        }


        public String getMin_Price() {
            return min_Price;
        }

        public void setMin_Price(String min_Price) {
            this.min_Price = min_Price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(String collect_id) {
            this.collect_id = collect_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getRecord_id() {
            return record_id;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
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

        public String getAd_name() {
            return ad_name;
        }

        public void setAd_name(String ad_name) {
            this.ad_name = ad_name;
        }

       /* public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }*/

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }
    }
}
