package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2016/1/26.
 */
public class CaseTypesBean extends NetResult {
    public static CaseTypesBean parse(String json) throws AppException {
        CaseTypesBean res = new CaseTypesBean();
        try {
            res = gson.fromJson(json, CaseTypesBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }


    private ArrayList<CaseTypesContent> content;

    public ArrayList<CaseTypesContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<CaseTypesContent> content) {
        this.content = content;
    }

    public class CaseTypesContent extends NetResult {
        private String type_id;//案例类型id
        private String name;//案例类型名称

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
