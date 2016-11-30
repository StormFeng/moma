package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2015.12.1.001.
 */
public class BetaStatusBean extends NetResult {
    public static BetaStatusBean parse(String json) throws AppException {
        BetaStatusBean res = new BetaStatusBean();
        try {
            res = gson.fromJson(json, BetaStatusBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }


    private BetaContent content = new BetaContent();

    public BetaContent getContent() {
        return content;
    }

    public void setContent(BetaContent content) {
        this.content = content;
    }

    public class BetaContent {
        private  String status;//测试包状态

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

}
