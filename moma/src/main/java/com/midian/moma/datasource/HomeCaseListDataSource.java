package com.midian.moma.datasource;

import android.content.Context;

import com.midian.moma.model.HomeBean;
import com.midian.moma.model.HomeBean.Banner;
import com.midian.moma.model.HomeBean.Examp;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

/**
 * 首页HomeDataSource
 *
 * @author chu
 */
public class HomeCaseListDataSource extends BaseListDataSource<NetResult> {
    String type_id;

    public HomeCaseListDataSource(Context context, String type_id) {
        super(context);
        this.type_id = type_id;
    }

    @Override
    protected ArrayList<NetResult> load(int page) throws Exception {

        ArrayList<NetResult> models = new ArrayList<NetResult>();
        HomeBean homeBean = AppUtil.getMomaApiClient(ac).momaIndex(type_id, null, this.page + "", AppContext.PAGE_SIZE);
        if (homeBean.isOK()) {
            if (page == 1) {
                IndexMultiItem multiItem1 = new IndexMultiItem();
                multiItem1.setItemViewType(0);
                multiItem1.banners = homeBean.getContent().getBanner();
                models.add(multiItem1);
            }
            for (HomeBean.Examp items : homeBean.getContent().getExamp()) {
                IndexMultiItem indexMultiItem = new IndexMultiItem();
                indexMultiItem.setItemViewType(1);
                indexMultiItem.examps = items;
                models.add(indexMultiItem);
            }
            if (homeBean.getContent().getExamp().size() == 0) {
                hasMore = false;
            } else {
                hasMore = true;
                this.page++;
            }
        } else {
            ac.handleErrorCode(context, homeBean.error_code);
        }
        return models;
    }


}
