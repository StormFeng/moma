package com.midian.login.bean;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 10.2获取城市列表
 * Created by Administrator on 2015/12/16 0016.
 */
public class CityBean extends NetResult {
    public static CityBean parse(String json) throws AppException {
        CityBean res = new CityBean();
        try {
            res = gson.fromJson(json, CityBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private CityContent content;

    public CityContent getContent() {
        return content;
    }

    public void setContent(CityContent content) {
        this.content = content;
    }

    public class CityContent {
        private String region_id;// id”,
        private String region_name;//名称”,
        private String region_level;//等级
        private ArrayList<AdvertisementCircle> advertisement_circle;

        public ArrayList<AdvertisementCircle> getAdvertisement_circle() {
            return advertisement_circle;
        }

        public void setAdvertisement_circle(ArrayList<AdvertisementCircle> advertisement_circle) {
            this.advertisement_circle = advertisement_circle;
        }

        public String getRegion_id() {
            return region_id;
        }

        public void setRegion_id(String region_id) {
            this.region_id = region_id;
        }

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getRegion_level() {
            return region_level;
        }

        public void setRegion_level(String region_level) {
            this.region_level = region_level;
        }
    }

    public class AdvertisementCircle extends NetResult {
        private String community_id;//广告圈id”,
        private String community_name;//广告圈名称

        public String getCommunity_id() {
            return community_id;
        }

        public void setCommunity_id(String community_id) {
            this.community_id = community_id;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }
    }
}
