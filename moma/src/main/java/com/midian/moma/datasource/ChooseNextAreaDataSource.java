package com.midian.moma.datasource;

import android.content.Context;

import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.ProvincesBean.City;

import java.util.ArrayList;
import java.util.List;

import midian.baselib.base.BaseListDataSource;

public class ChooseNextAreaDataSource extends BaseListDataSource<MultiItem> {
    private List<ProvincesBean.Area> areas = new ArrayList<ProvincesBean.Area>();

    public ChooseNextAreaDataSource(Context context, List<ProvincesBean.Area> areas) {
        super(context);
        this.areas = areas;
    }

    @Override
    protected ArrayList<MultiItem> load(int page) throws Exception {
        ArrayList<MultiItem> moreList = new ArrayList<MultiItem>();

        for (ProvincesBean.Area bean : areas) {
            MultiItem item = new MultiItem();
            item.area = bean;
            moreList.add(item);
        }
        hasMore = false;
        return moreList;
    }

}
