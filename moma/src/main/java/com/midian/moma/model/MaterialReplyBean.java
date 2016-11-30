package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.3查看回复列表
 * Created by chu on 2015.12.1.001.
 */
public class MaterialReplyBean extends NetResult {
    public static MaterialReplyBean parse(String json) throws AppException {
        MaterialReplyBean res = new MaterialReplyBean();
        try {
            res = gson.fromJson(json, MaterialReplyBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;

    }

    private ArrayList<MaterialReply> content;

    public ArrayList<MaterialReply> getContent() {
        return content;
    }

    public void setContent(ArrayList<MaterialReply> content) {
        this.content = content;
    }

    public class MaterialReply extends NetResult {
        private String reply_id;//回复id”,
        private String title;//回复标题”,
        private String url;//跳转链接”，
        private String reply_time;//回复时间

        public String getReply_id() {
            return reply_id;
        }

        public void setReply_id(String reply_id) {
            this.reply_id = reply_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }
    }


}
