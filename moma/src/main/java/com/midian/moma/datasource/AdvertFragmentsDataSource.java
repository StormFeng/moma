package com.midian.moma.datasource;

import android.content.Context;

import com.midian.moma.model.AdvertListBean;
import com.midian.moma.model.AdvertListBean.AdvertListContent;
import com.midian.moma.ui.main.AdvertFragments;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;

/**
 * Created by chu on 2016/3/31.
 */
public class AdvertFragmentsDataSource extends BaseListDataSource {

    private String keywords = null;
    private String lon;
    private String lat;
    private String type = null;
    private String regionId = null;
    private String level = null;
    private String community_id = null;
    private String date_time = null;


    public AdvertFragmentsDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<AdvertListContent> morelist = new ArrayList<AdvertListContent>();
        this.page = page;
        if (ac.city_name.equals("全国") || ac.city_name.equals("")) {
            regionId = null;
            level = null;
        } else {
            regionId = ac.city_id;
            level = ac.loc_city_level;
        }
//        System.out.println("选择的区id::::" + regionId + "---" + ac.loc_city_level);

        AdvertListBean bean = AppUtil.getMomaApiClient(ac).advertList(keywords, ac.lon, ac.lat, type, regionId, level, community_id, date_time, this.page + "", AppContext.PAGE_SIZE);
        if (bean != null) {
            if (bean.isOK()) {
                morelist.addAll(bean.getContent());
                if (bean.getContent().size() == 0) {
                    hasMore = false;
                } else {
                    hasMore = true;
                    this.page++;
                }

            } else {
                ac.handleErrorCode(context, bean.error_code);
            }
        }
        return morelist;
    }


    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }
}
