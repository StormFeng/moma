package com.midian.moma.model;

import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;

import midian.baselib.app.AppException;
import midian.baselib.bean.NetResult;

/**
 * 8.1上传广告资料
 * Created by chu on 2015.12.1.001.
 */
public class UploadMaterialBean extends NetResult {

    public static UploadMaterialBean parse(String json) throws AppException {
        UploadMaterialBean res = new UploadMaterialBean();
        try {
            res = gson.fromJson(json, UploadMaterialBean.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            throw AppException.json(e);
        }
        // System.out.println(res);
        return res;
    }

    private ArrayList<UploadMaterial> content;

    public ArrayList<UploadMaterial> getContent() {
        return content;
    }

    public void setContent(ArrayList<UploadMaterial> content) {
        this.content = content;
    }

    public class UploadMaterial extends NetResult {
        private String pic_thumb_name;//广告资料缩略图片文件名称”,
        private String pic_thumb_suffix;//广告资料缩略图片文件后缀

        public String getPic_thumb_name() {
            return pic_thumb_name;
        }

        public void setPic_thumb_name(String pic_thumb_name) {
            this.pic_thumb_name = pic_thumb_name;
        }

        public String getPic_thumb_suffix() {
            return pic_thumb_suffix;
        }

        public void setPic_thumb_suffix(String pic_thumb_suffix) {
            this.pic_thumb_suffix = pic_thumb_suffix;
        }
    }
}
