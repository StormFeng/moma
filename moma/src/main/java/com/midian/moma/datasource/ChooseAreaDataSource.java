package com.midian.moma.datasource;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.ProvincesBean.City;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

public class ChooseAreaDataSource extends BaseListDataSource<MultiItem> {
    private List<City> city = new ArrayList<City>();

    public ChooseAreaDataSource(Context context, List<City> city) {
        super(context);
        this.city = city;
    }

    @Override
    protected ArrayList<MultiItem> load(int page) throws Exception {
        ArrayList<MultiItem> moreList = new ArrayList<MultiItem>();

        for (City bean : city) {
            MultiItem item = new MultiItem();
            item.city = bean;
            moreList.add(item);
        }
        hasMore = false;
        return moreList;
    }

}
