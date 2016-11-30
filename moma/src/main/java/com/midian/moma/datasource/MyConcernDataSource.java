package com.midian.moma.datasource;

import android.content.Context;

import com.midian.moma.model.MyCollectsBean;
import com.midian.moma.model.MyCollectsBean.CollectContent;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

/**
 * Created by chu on 2015.11.23.023.
 */
public class MyConcernDataSource extends BaseListDataSource {


    public MyConcernDataSource(Context context) {
        super(context);
    }


    @Override
    protected ArrayList load(int page) throws Exception {
      /*ArrayList<NetResult> morelist = new ArrayList<NetResult>();
        for (int i = 0; i < 4; i++) {
            morelist.add(new NetResult());
        }*/

        ArrayList<CollectContent> morelist = new ArrayList<CollectContent>();
        this.page = page;
        MyCollectsBean bean = AppUtil.getMomaApiClient(ac).myCollects("1", ac.lon, ac.lat, page + "", AppContext.PAGE_SIZE);
        if (bean != null) {
            if (bean.isOK()) {
                morelist.addAll(bean.getContent());
                //判断第二页+是否有数据
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
}
