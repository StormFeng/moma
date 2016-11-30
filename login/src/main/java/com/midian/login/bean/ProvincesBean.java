package com.midian.login.bean;

import java.util.ArrayList;

import com.google.gson.JsonSyntaxException;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 省份、直辖市列表.
 *
 * @author chu
 */
public class ProvincesBean extends NetResult {

    private ArrayList<ProvincesContent> content;

    public static ProvincesBean parse(String json) throws AppException {
        ProvincesBean res = new ProvincesBean();
        try {
            res = gson.fromJson(json, ProvincesBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    public ArrayList<ProvincesContent> getContent() {
        return content;
    }

    public void setContent(ArrayList<ProvincesContent> content) {
        this.content = content;
    }


    public static class ProvincesContent extends NetResult {

        private String province_id;// 省id
        private String province_name;// 省名称
        private ArrayList<City> city;

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }


        public ArrayList<City> getCity() {
            return city;
        }

        public void setCity(ArrayList<City> city) {
            this.city = city;
        }
    }

    public class City extends NetResult {
        private String city_id;//市id
        private String city_name;//市名称
        private ArrayList<Area> area;//

        public ArrayList<Area> getArea() {
            return area;
        }

        public void setArea(ArrayList<Area> area) {
            this.area = area;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }

    public class Area extends NetResult {
        private String area_id;//区id”,
        private String area_name;//区名称

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
