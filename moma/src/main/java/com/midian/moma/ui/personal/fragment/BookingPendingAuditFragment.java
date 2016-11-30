package com.midian.moma.ui.personal.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;

import com.midian.moma.datasource.BookingPendingAuditDataSource;
import com.midian.moma.itemview.BookingPendingAuditTpl;
import com.midian.moma.itemview.PendingCommitTpl;
import com.midian.moma.model.MyBookingListBean.BookingContent;

import midian.baselib.base.BaseListFragment;
import midian.baselib.bean.NetResult;
import midian.baselib.shizhefei.view.listviewhelper.IDataSource;

/**
 * 我的预订-待审核
 * 
 * @author chu
 *
 */
public class BookingPendingAuditFragment extends BaseListFragment<BookingContent> {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //自定义列表无数据时的提示文字和事件 DeFaultLoadViewFactory.java
        listViewHelper.loadViewFactory.config(true, "目前没有待审核的预定");
        listViewHelper.loadViewFactory.BtOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                _activity.setResult(_activity.RESULT_OK, intent);
                _activity.finish();
            }
        }, "马上去预订");
    }

    @Override
	protected IDataSource<ArrayList<BookingContent>> getDataSource() {
		return new BookingPendingAuditDataSource(_activity);
	}

	@Override
	protected Class getTemplateClass() {
		return BookingPendingAuditTpl.class;
	}

}
