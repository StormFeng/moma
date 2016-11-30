package com.midian.login.bean;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 1.8个人资料
 *
 * @author Administrator
 */
public class UserDetailBean extends NetResult {

    public static UserDetailBean parse(String json) throws AppException {
        UserDetailBean res = new UserDetailBean();
        try {
            res = gson.fromJson(json, UserDetailBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
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
        private String head_pic;// 头像名称
        private String head_suffix;// 头像后缀
        private String name;// 姓名
        private String phone;// 手机号码
        private String sex;// 1
        private String address;//家庭住址
        private String mobile_phone;//登陆者账号

        public String getMobile_phone() {
            return mobile_phone;
        }

        public void setMobile_phone(String mobile_phone) {
            this.mobile_phone = mobile_phone;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getHead_suffix() {
            return head_suffix;
        }

        public void setHead_suffix(String head_suffix) {
            this.head_suffix = head_suffix;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
