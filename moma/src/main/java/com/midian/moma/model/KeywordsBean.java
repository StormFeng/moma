package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2015.12.2.002.
 */
public class KeywordsBean extends NetResult {
    public static KeywordsBean parse(String json) throws AppException {
        KeywordsBean res = new KeywordsBean();
        try {
            res = gson.fromJson(json, KeywordsBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<KeywordsContent> content;

    public ArrayList<KeywordsContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<KeywordsContent> content) {
        this.content = content;
    }

    public class KeywordsContent extends NetResult {

        private String keywords;//热词

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }
    }

}
