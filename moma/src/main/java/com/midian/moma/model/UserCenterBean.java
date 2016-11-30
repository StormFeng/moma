package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 1.14个人中心--订单数量
 * Created by chu on 2016/5/12.
 */
public class UserCenterBean extends NetResult {
    public static UserCenterBean parse(String json) throws AppException {
        UserCenterBean res = new UserCenterBean();
        try {
            res = gson.fromJson(json, UserCenterBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private UserContent content;

    public UserContent getContent() {
        return content;
    }

    public void setContent(UserContent content) {
        this.content = content;
    }

    public class UserContent extends NetResult {
        private String wait_pay_count;//":"待支付数量",
        private String wait_commit_count;//”:”待提交数量”,
        private String wait_audit_count;//	”:”待审核数量”,
        private String complete_count;//	”:”已完成数量

        public String getWait_pay_count() {
            return wait_pay_count;
        }

        public void setWait_pay_count(String wait_pay_count) {
            this.wait_pay_count = wait_pay_count;
        }

        public String getWait_commit_count() {
            return wait_commit_count;
        }

        public void setWait_commit_count(String wait_commit_count) {
            this.wait_commit_count = wait_commit_count;
        }

        public String getWait_audit_count() {
            return wait_audit_count;
        }

        public void setWait_audit_count(String wait_audit_count) {
            this.wait_audit_count = wait_audit_count;
        }

        public String getComplete_count() {
            return complete_count;
        }

        public void setComplete_count(String complete_count) {
            this.complete_count = complete_count;
        }
    }
}
