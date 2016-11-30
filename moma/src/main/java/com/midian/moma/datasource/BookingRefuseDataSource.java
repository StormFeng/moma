package com.midian.moma.datasource;

import java.util.ArrayList;

import android.content.Context;

import com.midian.moma.model.MyBookingListBean;
import com.midian.moma.model.MyBookingListBean.BookingContent;
import com.midian.moma.utils.AppUtil;

import midian.baselib.app.AppContext;
import midian.baselib.base.BaseListDataSource;
import midian.baselib.bean.NetResult;

/**
 * 预订-已拒绝
 * 
 * @author chu
 *
 */
public class BookingRefuseDataSource extends BaseListDataSource<BookingContent> {

	public BookingRefuseDataSource(Context context) {
		super(context);
	}

    @Override
    protected ArrayList<BookingContent> load(int page) throws Exception {
        /*ArrayList<NetResult> morelist = new ArrayList<NetResult>();
        for (int i = 0; i < 1; i++) {
			morelist.add(new NetResult());
		}*/

        ArrayList<BookingContent> morelist = new ArrayList<BookingContent>();
        this.page = page;
        String type = "3";//0：全部，1：待审核，2：已通过，3已拒绝
        MyBookingListBean bean = AppUtil.getMomaApiClient(ac).myReserves("3", this.page+ "", AppContext.PAGE_SIZE);
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
