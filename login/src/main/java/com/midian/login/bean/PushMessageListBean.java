package com.midian.login.bean;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 11.1推送消息列表
 * Created by Administrator on 2015/12/16 0016.
 */
public class PushMessageListBean extends NetResult {
    public static PushMessageListBean parse(String json) throws AppException {
        PushMessageListBean res = new PushMessageListBean();
        try {
            res = gson.fromJson(json, PushMessageListBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<PushMessageContent> content;

    public ArrayList<PushMessageContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<PushMessageContent> content) {
        this.content = content;
    }

    public class PushMessageContent extends NetResult {
        private String msg_title;//推送标题”,
        private String msg_content;//推送内容”
        private String order_no;//订单编号”,
        private String order_id;//订单id”，
        private String ad_title;//广告标题”,
        private String type;//消息类型”,
        private String time;//时间

        public String getMsg_title() {
            return msg_title;
        }

        public void setMsg_title(String msg_title) {
            this.msg_title = msg_title;
        }

        public String getMsg_content() {
            return msg_content;
        }

        public void setMsg_content(String msg_content) {
            this.msg_content = msg_content;
        }

        public String getOrder_no() {
            return order_no;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        public String getAd_title() {
            return ad_title;
        }

        public void setAd_title(String ad_title) {
            this.ad_title = ad_title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

}
