package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by Administrator on 2016/3/11.
 */
public class StartAdvertiseBean extends NetResult {
    public static StartAdvertiseBean parse(String json) throws AppException {
        StartAdvertiseBean res = new StartAdvertiseBean();
        try {
            res = gson.fromJson(json, StartAdvertiseBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private StartAdvertiseContent content;

    public StartAdvertiseContent getContent() {
        return content;
    }

    public void setContent(StartAdvertiseContent content) {
        this.content = content;
    }

    public class StartAdvertiseContent extends NetResult {
        private String ad_id;//广告id
        private String ad_pic_name;//广告名称
        private String ad_pic_suffix;//广告后缀
        private String url;//跳转地址

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
