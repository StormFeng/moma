package com.midian.moma.datasource;

import android.content.Context;

import com.midian.configlib.AppConstant;
import com.midian.moma.app.MAppContext;
import com.midian.moma.model.ShoppingCartBean;
import com.midian.moma.model.ShoppingCartBean.ShoppingContent;
import com.midian.moma.urlconstant.UrlConstant;
import com.midian.moma.utils.AppUtil;

import java.util.ArrayList;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.utils.UIHelper;

/**
 * 购物车DataSource
 *
 * @author chu
 */
public class ShoppingDataSource extends BaseListDataSource {

    public ShoppingDataSource(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<ShoppingContent> models = new ArrayList<ShoppingCartBean.ShoppingContent>();
        this.page = page;
        if (ac.isAccess()) {
            ShoppingCartBean bean = AppUtil.getMomaApiClient(ac).shoppingCart("1",   "1", AppContext.PAGE_SIZE);
            if (bean != null && bean.isOK()) {
                models.addAll(bean.getContent());
                //判断第二页是否有数据
                if (bean.getContent().size() < 10) {
                    hasMore = false;
                } else {
                    hasMore = true;
                    this.page++;
                }
            } else {
                ac.handleErrorCode(context, bean.error_code);
            }
        }
        return models;
    }

}
