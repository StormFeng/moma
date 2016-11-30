package com.midian.moma.datasource;

import android.app.Activity;
import android.content.Context;

import com.midian.moma.model.MapOverlapBean;
import com.midian.moma.model.MapOverlapBean.Advertisements;
import com.midian.moma.model.MapOverlapBean.MapOverContent;
import com.midian.moma.model.MyCollectsBean;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * Created by chu on 2016/1/8.
 */
public class MapListDataSource extends BaseListDataSource {

    private String ad_ids;
    private List<Advertisements> list=new ArrayList<Advertisements>();

    public MapListDataSource(Context context, String ad_ids) {
        super(context);
        this.ad_ids = ad_ids;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<Advertisements> morelist = new ArrayList<Advertisements>();
        this.page = page;
        System.out.println("MapListDataSource：拼接的id：" + ad_ids.toString());
        MapOverlapBean bean = AppUtil.getMomaApiClient(ac).mapOverlapAdvertisement(ad_ids, ac.lon, ac.lat);
        if (bean != null) {
            if (bean.isOK()) {
                list = bean.getContent().getAdvertisements();
                morelist.addAll(list);
                //判断第二页+是否有数据
                if (bean.getContent().getAdvertisements().size() == 0) {
                    hasMore = false;
                } else {
                    hasMore = true;
                    this.page++;
                }
            } else {
                ac.handleErrorCode(context, bean.error_code);
            }
        }
        hasMore = false;
        return morelist;
    }


}
