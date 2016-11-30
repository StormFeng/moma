package com.midian.moma.datasource;

import android.content.Context;

import com.midian.moma.app.MAppContext;
import com.midian.moma.model.AdvertListBean;
import com.midian.moma.model.HomeBean;
import com.midian.moma.model.HomeBean.Examp;
import com.midian.moma.model.HomeBean.Content;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

//import com.midian.moma.model.AdvertListBean.Advertisements;

/**
 * 首页广告位搜索
 * Created by chu on 2016/1/12.
 */
public class SearchCaseDataSource extends BaseListDataSource {

    private String keywords;
    private String cityId;

    public SearchCaseDataSource(Context context, String keywords, String city_id) {
        super(context);
        this.keywords = keywords;
        this.cityId = city_id;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<IndexMultiItem> morelist = new ArrayList<IndexMultiItem>();
        this.page = page;
        HomeBean bean = AppUtil.getMomaApiClient(ac).momaIndex(null,keywords,this.page + "", MAppContext.PAGE_SIZE);
        if (bean.isOK()) {

            for (HomeBean.Examp items : bean.getContent().getExamp()) {
                IndexMultiItem indexMultiItem = new IndexMultiItem();
                indexMultiItem.setItemViewType(1);
                indexMultiItem.examps = items;
                morelist.add(indexMultiItem);
            }

            if (bean.getContent().getExamp().size() < 1) {
                hasMore = false;
            } else {
                hasMore = true;
                this.page++;
            }

            /*if (bean.getContent().getExamp() != null) {
                morelist.add(bean.getContent());
            }*/
            /*if (bean.getContent().getExamp().size() < 1) {
                hasMore = false;
            } else {
                hasMore = true;
                this.page++;
            }*/
        } else {
            ac.handleErrorCode(context, bean.error_code);
        }

        return morelist;
    }
}
