package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 2.2获取系统配置信息
 * Created by chu on 2015.12.1.001.
 */
public class SysConfListBean extends NetResult {
    public static SysConfListBean parse(String json) throws AppException {
        SysConfListBean res = new SysConfListBean();
        try {
            res = gson.fromJson(json, SysConfListBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private SysContent content;

    public SysContent getContent() {
        return content;
    }

    public void setContent(SysContent content) {
        this.content = content;
    }

    public class SysContent extends NetResult {
        private String about_pay;//
        private String about_us;//
        private String contact_phone;//
        private String link_brand;//
        private String link_geo_pro;//
        private String link_help;//
        private String link_standard;//
        private String service_term;//
        private String cooperation_process;//合作流程

        public String getCooperation_process() {
            return cooperation_process;
        }

        public void setCooperation_process(String cooperation_process) {
            this.cooperation_process = cooperation_process;
        }

        public String getContact_phone() {
            return contact_phone;
        }

        public void setContact_phone(String contact_phone) {
            this.contact_phone = contact_phone;
        }

        public String getLink_brand() {
            return link_brand;
        }

        public void setLink_brand(String link_brand) {
            this.link_brand = link_brand;
        }

        public String getLink_geo_pro() {
            return link_geo_pro;
        }

        public void setLink_geo_pro(String link_geo_pro) {
            this.link_geo_pro = link_geo_pro;
        }

        public String getLink_help() {
            return link_help;
        }

        public void setLink_help(String link_help) {
            this.link_help = link_help;
        }

        public String getLink_standard() {
            return link_standard;
        }

        public void setLink_standard(String link_standard) {
            this.link_standard = link_standard;
        }

        public String getService_term() {
            return service_term;
        }

        public void setService_term(String service_term) {
            this.service_term = service_term;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getAbout_pay() {
            return about_pay;
        }

        public void setAbout_pay(String about_pay) {
            this.about_pay = about_pay;
        }
    }
}
