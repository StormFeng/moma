package com.midian.moma.datasource;

import android.content.Context;

import com.midian.moma.app.MAppContext;
import com.midian.moma.model.HomeBean;
import com.midian.moma.ui.main.IndexMultiItem;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;

/**
 * 首页HomeDataSource
 *
 * @author chu
 */
public class HomeDataSource extends BaseListDataSource<IndexMultiItem> {

    public HomeDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<IndexMultiItem> load(int page) throws Exception {


        ArrayList<IndexMultiItem> models = new ArrayList<IndexMultiItem>();
        HomeBean homeBean = AppUtil.getMomaApiClient(ac).momaIndex(null,null,"1", AppContext.PAGE_SIZE);

        if (homeBean != null) {
            if (homeBean.isOK()) {
                IndexMultiItem multiItem1 = new IndexMultiItem();
                multiItem1.setItemViewType(0);
                multiItem1.banners = homeBean.getContent().getBanner();
                models.add(multiItem1);

                for (HomeBean.Examp items : homeBean.getContent().getExamp()) {
                    IndexMultiItem indexMultiItem2 = new IndexMultiItem();
                    indexMultiItem2.setItemViewType(1);
                    indexMultiItem2.examps = items;
                    models.add(indexMultiItem2);
                }

                //if (homeBean.getContent().getExamp().size() == 0) {
                //hasMore = false;
                //} else {
                //hasMore = true;
                //this.page++;
                //}

            } else {
                ac.handleErrorCode(context, homeBean.error_code);
            }
        }

        hasMore = false;
        return models;
    }


}
