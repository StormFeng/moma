package com.midian.moma.datasource;

import java.util.ArrayList;

import android.content.Context;

import com.midian.moma.model.MyOrdersBean;
import com.midian.moma.model.MyOrdersBean.MyorderContent;
import com.midian.moma.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

/**
 * 待完成
 *
 * @author chu
 */
public class AllOrderDataSource extends BaseListDataSource<MyorderContent> {

    public AllOrderDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList<MyorderContent> load(int page) throws Exception {
        /*ArrayList<NetResult> morelist = new ArrayList<NetResult>();
        for (int i = 0; i < 1; i++) {
			morelist.add(new NetResult());
		}*/

        ArrayList<MyorderContent> morelist = new ArrayList<MyorderContent>();
        this.page = page;
        String type = "0";//0：全部，1：待付款，2：待提交，3待审核，4已完成
        MyOrdersBean bean = AppUtil.getMomaApiClient(ac).myOrders("0", page + "", AppContext.PAGE_SIZE);
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
