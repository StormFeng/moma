package com.midian.moma.datasource;

import android.content.Context;

import com.midian.login.api.LoginApiClient;
import com.midian.login.bean.MultiItem;
import com.midian.login.bean.ProvincesBean;
import com.midian.login.bean.ProvincesBean.ProvincesContent;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

public class ChooseCitysDataSource extends BaseListDataSource<MultiItem> {

    int index = 0;

    public ChooseCitysDataSource(Context context) {
        super(context);
    }

    public ChooseCitysDataSource(Context context, int i) {
        super(context);
        index = i;
    }

    @Override
    protected ArrayList<MultiItem> load(int page) throws Exception {
        ArrayList<MultiItem> moreList = new ArrayList<MultiItem>();
        MultiItem item0 = new MultiItem();
        item0.setItemViewType(0);
        moreList.add(item0);

        MultiItem item1 = new MultiItem();
        item1.setItemViewType(1);
        moreList.add(item1);

        MultiItem item2 = new MultiItem();
        item2.setItemViewType(2);
        moreList.add(item2);

        ProvincesBean bean = ac.api.getApiClient(LoginApiClient.class).getProvinces();
        if (bean != null) {
            if (bean.isOK()) {

                for (ProvincesBean.ProvincesContent content : bean.getContent()) {
                    MultiItem item3 = new MultiItem();
                    item3.setItemViewType(3);
                    item3.provincesContent=content;
                    moreList.add(item3);
                }

            } else {
                ac.handleErrorCode(context, bean.error_code);
            }
        }

        hasMore = false;
        return moreList;
    }

}
