package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2016/4/6.
 */
public class DiscountBean extends NetResult {
    public static DiscountBean parse(String json) throws AppException {
        DiscountBean res = new DiscountBean();
        try {
            res = gson.fromJson(json, DiscountBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private List<DiscountContent> content;

    public List<DiscountContent> getContent() {
        return content;
    }

    public void setContent(List<DiscountContent> content) {
        this.content = content;
    }

    public class DiscountContent extends NetResult {
        private String discount_id;//折扣id”，
        private String discount_name;//折扣名称

        public String getDiscount_id() {
            return discount_id;
        }

        public void setDiscount_id(String discount_id) {
            this.discount_id = discount_id;
        }

        public String getDiscount_name() {
            return discount_name;
        }

        public void setDiscount_name(String discount_name) {
            this.discount_name = discount_name;
        }
    }
}
