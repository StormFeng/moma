package com.midian.login.bean;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 注册
 */
public class RegisterBean extends NetResult {

    private MomaContent content = new MomaContent();

    public void setContent(MomaContent content) {
        this.content = content;
    }

    public MomaContent getContent() {

        return content;
    }

    public static RegisterBean parse(String json) throws AppException {
        RegisterBean res = new RegisterBean();
        try {
            res = gson.fromJson(json, RegisterBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    public class MomaContent {
        private String access_token;// 授权令牌
        private String userid;// 用户ID
        private String name;// 姓名
        private String user_type;// 用户类型"，2会员
        private String head_pic_name;//用户头像
        private String head_pic_suffix;//头像后缀

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getHead_pic_name() {
            return head_pic_name;
        }

        public void setHead_pic_name(String head_pic_name) {
            this.head_pic_name = head_pic_name;
        }

        public String getHead_pic_suffix() {
            return head_pic_suffix;
        }

        public void setHead_pic_suffix(String head_pic_suffix) {
            this.head_pic_suffix = head_pic_suffix;
        }
    }

}
