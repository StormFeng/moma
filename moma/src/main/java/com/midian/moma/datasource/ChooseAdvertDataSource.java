package com.midian.moma.datasource;

import android.app.Activity;
import android.content.Context;

import com.midian.moma.app.MAppContext;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;

/**
 * Created by chu on 2016/1/12.
 */
public class ChooseAdvertDataSource extends BaseListDataSource{

    private String keywords;
    private String cityId;
    public ChooseAdvertDataSource(Context context, String keywords,String city_id) {
        super(context);
        this.keywords = keywords;
        this.cityId=city_id;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
         ArrayList<AdvertListBean.AdvertListContent>morelist=new ArrayList<AdvertListBean.AdvertListContent>();
        this.page = page;
//        AdvertListBean bean = AppUtil.getMomaApiClient(ac).advertList(keywords, ac.lon, ac.lat, null, cityId, this.page + "", MAppContext.PAGE_SIZE);
        AdvertListBean bean = AppUtil.getMomaApiClient(ac).advertList(keywords,ac.lon,ac.lat,null,null,null,null ,null,this.page+"",MAppContext.PAGE_SIZE);
        if (bean.isOK()) {
            morelist.addAll(bean.getContent());
            if (bean.getContent().size()<1) {
                hasMore = false;
            } else {
                hasMore = true;
                this.page++;
            }
        }
        return morelist;
    }
}
