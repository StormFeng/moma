package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 4.1首页
 * Created by chu on 2015.12.1.001.
 */
public class HomeBean extends NetResult {

    private Content content;

    public static HomeBean parse(String json) throws AppException {
        HomeBean res = new HomeBean();
        try {
            res = gson.fromJson(json, HomeBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content extends NetResult {

        private ArrayList<Banner> banner;//banner图片
        private ArrayList<Examp> examp;//案例

        public ArrayList<Banner> getBanner() {
            return banner;
        }

        public void setBanner(ArrayList<Banner> banner) {
            this.banner = banner;
        }

        public ArrayList<Examp> getExamp() {
            return examp;
        }

        public void setExamp(ArrayList<Examp> examp) {
            this.examp = examp;
        }
    }

    public class Banner extends NetResult {
        private String banner_id;//bannerId”，
        private String ad_id;//广告位id”
        private String pic_name;//banner"图片名称",
        private String pic_suffix;//图片后缀",
        private String url;//banner的图片

        public String getBanner_id() {
            return banner_id;
        }

        public void setBanner_id(String banner_id) {
            this.banner_id = banner_id;
        }

        public String getAd_id() {
            return ad_id;
        }

        public void setAd_id(String ad_id) {
            this.ad_id = ad_id;
        }

        public String getPic_name() {
            return pic_name;
        }

        public void setPic_name(String pic_name) {
            this.pic_name = pic_name;
        }

        public String getPic_suffix() {
            return pic_suffix;
        }

        public void setPic_suffix(String pic_suffix) {
            this.pic_suffix = pic_suffix;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class Examp extends NetResult {
        private String case_id;//案例id“，
        private String cover_pic_thumb_name;//案例封面缩略图片名称“，
        private String cover_pic_thumb_suffix;//案例封面缩略图片后缀“，
        private String serve_pic_thumb_name;//案例客户头像缩略图片名称”，
        private String serve_pic_thumb_suffix;//案例客户头像缩略图片后缀”，
        private String case_title;//案例标题”，
        private String case_serve;//案例客户
        private String like_count;//点赞数”,
        private String is_like;//是否点赞过


        public String getLike_count() {
            return like_count;
        }

        public void setLike_count(String like_count) {
            this.like_count = like_count;
        }

        public String getIs_like() {
            return is_like;
        }

        public void setIs_like(String is_like) {
            this.is_like = is_like;
        }

        public String getCase_id() {
            return case_id;
        }

        public void setCase_id(String case_id) {
            this.case_id = case_id;
        }

        public String getCover_pic_thumb_name() {
            return cover_pic_thumb_name;
        }

        public void setCover_pic_thumb_name(String cover_pic_thumb_name) {
            this.cover_pic_thumb_name = cover_pic_thumb_name;
        }

        public String getCover_pic_thumb_suffix() {
            return cover_pic_thumb_suffix;
        }

        public void setCover_pic_thumb_suffix(String cover_pic_thumb_suffix) {
            this.cover_pic_thumb_suffix = cover_pic_thumb_suffix;
        }

        public String getServe_pic_thumb_name() {
            return serve_pic_thumb_name;
        }

        public void setServe_pic_thumb_name(String serve_pic_thumb_name) {
            this.serve_pic_thumb_name = serve_pic_thumb_name;
        }

        public String getServe_pic_thumb_suffix() {
            return serve_pic_thumb_suffix;
        }

        public void setServe_pic_thumb_suffix(String serve_pic_thumb_suffix) {
            this.serve_pic_thumb_suffix = serve_pic_thumb_suffix;
        }

        public String getCase_title() {
            return case_title;
        }

        public void setCase_title(String case_title) {
            this.case_title = case_title;
        }

        public String getCase_serve() {
            return case_serve;
        }

        public void setCase_serve(String case_serve) {
            this.case_serve = case_serve;
        }
    }
}
