package com.midian.moma.datasource;

import android.app.Activity;
import android.content.Context;

import com.midian.moma.app.MAppContext;
import com.midian.moma.model.MaterialReplyBean;
import com.midian.moma.model.MaterialReplyBean.MaterialReply;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.base.BaseListDataSource;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * Created by chu on 2016/1/20.
 */
public class ReplayListDataSource extends BaseListDataSource {
    private String order_id;

    public ReplayListDataSource(Context context, String order_id) {
        super(context);
        this.order_id = order_id;
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<MaterialReply> morelist = new ArrayList<MaterialReply>();
        this.page = page;
        MaterialReplyBean bean = AppUtil.getMomaApiClient(ac).materialReply(order_id, page+"", MAppContext.PAGE_SIZE);
        if (bean.isOK()) {
            morelist.addAll(bean.getContent());
            if (bean.getContent().size() == 0) {
                hasMore = false;
            } else {
                hasMore = true;
            }
        } else {
            ac.handleErrorCode(context, bean.error_code);
        }
        return morelist;
    }
}
