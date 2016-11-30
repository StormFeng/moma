package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2016/3/4.
 */
public class SignScoreBean extends NetResult {
    private SignContent content;

    public static SignScoreBean parse(String json) throws AppException {
        SignScoreBean res = new SignScoreBean();
        try {
            res = gson.fromJson(json, SignScoreBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    public SignContent getContent() {
        return content;
    }

    public void setContent(SignContent content) {
        this.content = content;
    }

    public class SignContent extends NetResult {
        private String total_score;//总积分”,
        private ArrayList<SignDate> content;//

        public String getTotal_score() {
            return total_score;
        }

        public void setTotal_score(String total_score) {
            this.total_score = total_score;
        }

        public ArrayList<SignDate> getContent() {
            return content;
        }

        public void setContent(ArrayList<SignDate> content) {
            this.content = content;
        }
    }

    public class SignDate extends NetResult {
        private String date;//当月签到日期

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
