package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 4.2案例详情
 * Created by chu on 2015.12.1.001.
 */
public class CaseDetailBean extends NetResult {
    public static CaseDetailBean parse(String json) throws AppException {
        CaseDetailBean res = new CaseDetailBean();
        try {
            res = gson.fromJson(json, CaseDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }


    private Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public class Content extends NetResult {
        private String case_id;//案例id”，
        private String serve_pic_thumb_name;//案例客户头像缩略图名称”，
        private String serve_pic_thumb_suffix;//案例客户头像缩略图后缀”，
        private String case_serve_name;//案例客户名称”，
        private String case_title;//案例标题”，
        private String  like_count;//点赞数”,
        private String is_like;//是否点赞过
        private String case_detail;
        private String share_url;//分享链接

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

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

        public String getCase_serve_name() {
            return case_serve_name;
        }

        public void setCase_serve_name(String case_serve_name) {
            this.case_serve_name = case_serve_name;
        }

        public String getCase_title() {
            return case_title;
        }

        public void setCase_title(String case_title) {
            this.case_title = case_title;
        }

        public String getCase_detail() {
            return case_detail;
        }

        public void setCase_detail(String case_detail) {
            this.case_detail = case_detail;
        }
    }

    /*public class CaseDetail extends NetResult {
        private String case_pic_name;//案例详情图片名称”，
        private String case_pic_suffix;//案例详情图片后缀”，
        private String case_content;//案例内容

        public String getCase_pic_name() {
            return case_pic_name;
        }

        public void setCase_pic_name(String case_pic_name) {
            this.case_pic_name = case_pic_name;
        }

        public String getCase_pic_suffix() {
            return case_pic_suffix;
        }

        public void setCase_pic_suffix(String case_pic_suffix) {
            this.case_pic_suffix = case_pic_suffix;
        }

        public String getCase_content() {
            return case_content;
        }

        public void setCase_content(String case_content) {
            this.case_content = case_content;
        }
    }*/
}
